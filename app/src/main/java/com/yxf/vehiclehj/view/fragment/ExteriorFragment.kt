package com.yxf.vehiclehj.view.fragment

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehiclehj.MyApp
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.bean.*
import com.yxf.vehiclehj.databinding.FragmentExteriorBinding
import com.yxf.vehiclehj.utils.*
import com.yxf.vehiclehj.viewModel.DatabaseViewModel
import com.yxf.vehiclehj.viewModel.DatabaseViewModelFactor
import com.yxf.vehiclehj.viewModel.ExteriorViewModel
import com.yxf.vehiclehj.viewModel.ExteriorViewModelFactory
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private val viewModel by viewModels<ExteriorViewModel> { ExteriorViewModelFactory((this.requireActivity().application as MyApp).exteriorRepo) }
    private val databaseViewModel by viewModels<DatabaseViewModel> { DatabaseViewModelFactor((this.requireActivity().application as MyApp).databaseRepo) }
    private lateinit var currentPhotoPath: String
    lateinit var startActivityLaunch: ActivityResultLauncher<Uri>
    private val itemAdapter = ExteriorItemRecyclerViewAdapter()
    private val photoAdapter = ExteroirPhotoRecyclerViewAdapter()

    private var holder: RecyclerView.ViewHolder? = null
    private var imageView : ImageView? = null
    private val args by navArgs<ExteriorFragmentArgs>()
    private lateinit var beginTime : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 注册相机启动callback registerForActivityResult用法见 它替代了传统的onActivityResult()
         * @since https://developer.android.com/training/basics/intents/result
         */
        startActivityLaunch = registerForActivityResult(ActivityResultContracts.TakePicture()){
            if (it){
                /**
                 * 拍照成功 处理图像
                 */
                val targetW: Int = imageView!!.width
                val targetH: Int = imageView!!.height

                val bmOptions = BitmapFactory.Options().apply {
                    // Get the dimensions of the bitmap
                    inJustDecodeBounds = true

                    val photoW: Int = outWidth
                    val photoH: Int = outHeight

                    // Determine how much to scale down the image
                    val scaleFactor: Int = Math.min(photoW / targetW, photoH / targetH)
                    inJustDecodeBounds = false
                    inSampleSize = 4
                    inPurgeable = true
                }
                /**
                 * 处理好的图像设置到imageView上
                 */
                BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                    imageView?.setImageBitmap(bitmap)
                    /**
                     * 设置tag表明此view已有照片 在上传数据收集中通过tag判断是否需要上传
                     */

                    imageView?.tag = "1"
                }
            }else{
                Log.e("TAG", "takePhoto: 拍照取消", )
            }
        }
    }

    override fun init() {
        beginTime = date2String(Date(),"HHmmss")
        binding.rvExteriorItem.adapter = itemAdapter
        binding.rvExteriorPhoto.adapter = photoAdapter
        //创建一个lifecycle的协程域以运行挂起函数
        lifecycleScope.launchWhenCreated {
            /**
             * 请求webapi获取外键照片和外键项目数据
             */
            viewModel.getExteriorPhoto(args.beanR101.Hjlsh).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "init: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                photoAdapter.data = it.Body
            }
            viewModel.exteriorItem(args.beanR101.Hjlsh).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "init: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                itemAdapter.data = it.Body
            }
        }
        //给外检项目item添加点击事件
        itemAdapter.onItemViewClickListener = object : BaseRvAdapter.OnItemViewClickListener<ExteriorItemR104Response>{
            override fun onItemClick(view: View, position: Int, bean: ExteriorItemR104Response) {
                when (view.tag){
                    "1" ->{
                        (view as ImageView).setImageResource(R.drawable.icon_no)
                        view.tag = "2"
                    }
                    "2" ->{
                        (view as ImageView).setImageResource(R.drawable.icon_yes)
                        view.tag = "1"
                    }

                }
            }

        }
        //给外检照片item添加点击事件
        photoAdapter.onItemViewClickListener = object :BaseRvAdapter.OnItemViewClickListener<ExteriorPhotoR102Response>{
            override fun onItemClick(view: View, position: Int, bean: ExteriorPhotoR102Response) {
                        //先创建文件 创建失败抛出异常
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            Log.e("TAG", "onItemClick: ${ex.message}", )

                            null
                        }
                //获取文件Uri并传入startActivityLaunch.launch()中启动相机
                        photoFile?.also {
                            val photoURI: Uri = FileProvider.getUriForFile(
                                requireContext(),
                                FILE_PROVIDER,
                                it
                            )
                            startActivityLaunch.launch(photoURI)
                        }
                        holder = binding.rvExteriorPhoto.findViewHolderForAdapterPosition(position)
                imageView = holder?.itemView?.findViewById(R.id.iv_takePhoto)
                    }

        }
        binding.btnSubmit.setOnClickListener {
            databaseViewModel.getJyjgbh().observe(this){
                submit(getExteriorItemData(),getExteriorPhotosData(it))
            }


        }


    }

    /**
     * 获取需上传的外检照片信息
     * @param Jyjgbh 检验机构编号
     */
    private fun getExteriorPhotosData(Jyjgbh : String): List<SavePhotoW102Request> {
        val list = ArrayList<SavePhotoW102Request>()
        for (index in 0 until photoAdapter.itemCount){
            val holder = binding.rvExteriorPhoto.findViewHolderForAdapterPosition(index)
            val zpZl = holder?.itemView?.findViewById<TextView>(R.id.tv_zpdm)
            val zpMc = holder?.itemView?.findViewById<TextView>(R.id.tv_zpmc)
            val ivImage = holder?.itemView?.findViewById<ImageView>(R.id.iv_takePhoto)
            val drawable = ivImage?.drawable
            val ivTag = ivImage?.tag.toString()
            if (ivTag == "1") {
                val bitmap = getBitmapFromDrawable(drawable!!)
                val base64 = bitmap2Base64(bitmap)
                list.add(SavePhotoW102Request(
                    args.beanR101.Hjlsh,
                    Jyjgbh,
                    "1",
                    args.beanR101.Jccs,
                    args.beanR101.Hphm,
                    args.beanR101.Hpzl,
                    args.beanR101.VIN,
                    base64,
                    date2String(Date(),"yyyyMMddHHmmss"),
                    "F1",
                    zpZl?.text.toString(),
                    zpMc?.text.toString(),
                    args.beanR101.Hjdlsj
                    ))
            }
        }
        return list

    }

    /**
     * 提交数据到服务器数据库
     * @param exteriorItem 外键项目信息
     * @param exteriorPhotos 外键照片信息
     */
    private fun submit(exteriorItem: ExteriorItemW101Request, exteriorPhotos : List<SavePhotoW102Request>) {
        /**
         * 使用方法
         * @since https://developer.android.com/topic/libraries/architecture/coroutines
         */
        lifecycleScope.launchWhenCreated {
            viewModel.saveExteriorItem(exteriorItem).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "submit: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                viewModel.saveExteriorPhoto(exteriorPhotos).collect{
                    if (it.Code != "1"){
                        Log.e("TAG", "submit: ${it.Message}", )
                        showShortSnackbar(binding.root,it.Message)
                        return@collect
                    }
                    showShortSnackbar(binding.root,"上传成功")
                    findNavController().popBackStack()
                }
            }
        }




    }

    /**
     * 创建文件到sdcard/Android/data/<packageName>/Pictures目录下
     * 并将路径(File.absolutePath)赋值给 currentPhotoPath
     * @return 返回这个文件的File对象
     */

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = date2String(Date(),"yyyyMMdd_HHmmss")
        val storageDir: File? =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }




    /**
     * 获取需上传的外检项目信息
     */
    private fun getExteriorItemData() : ExteriorItemW101Request{
        //workJcxm是数据库 JcDate_Work_JC 中的相同字段，保存不合格项目编号 小数点分隔
        val workJcxm = StringBuilder()
        //遍历recyclerView的每一个itemView获取信息
        for (index in 0 until itemAdapter.itemCount){
            val holder = binding.rvExteriorItem.findViewHolderForAdapterPosition(index)
            val tvStatus = holder?.itemView?.findViewById<ImageView>(R.id.iv_status)
            val tvDm = holder?.itemView?.findViewById<TextView>(R.id.tv_dm)
            if (tvStatus?.tag == "2"){
                workJcxm.append(tvDm?.text)
                workJcxm.append(".")
            }
        }


        return ExteriorItemW101Request(
            args.beanR101.Hjlsh,
            args.beanR101.Hpzl,
            args.beanR101.Hphm,
            args.beanR101.Jccs,
            date2String(Date(),"yyyyMMdd"),
            beginTime,
            date2String(Date(),"HHmmss"),
            "1".takeIf { workJcxm.isBlank()  }?:"2",
            "F1",
            workJcxm.toString(),
            args.beanR001.TrueName
        )


    }

}