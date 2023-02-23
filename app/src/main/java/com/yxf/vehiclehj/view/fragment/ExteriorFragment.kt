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
import com.yxf.vehiclehj.repo.ExteriorRepo
import com.yxf.vehiclehj.utils.*
import com.yxf.vehiclehj.viewModel.DatabaseViewModel
import com.yxf.vehiclehj.viewModel.DatabaseViewModelFactor
import com.yxf.vehiclehj.viewModel.ExteriorViewModel
import com.yxf.vehiclehj.viewModel.ExteriorViewModelFactory
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private val viewModel by viewModels<ExteriorViewModel> { ExteriorViewModelFactory((this.requireActivity().application as MyApp).exteriorRepo) }
    private val databaseViewModel by viewModels<DatabaseViewModel> { DatabaseViewModelFactor((this.requireActivity().application as MyApp).databaseRepo) }
    private lateinit var currentPhotoPath: String
    val repo = ExteriorRepo()
    lateinit var startActivityLaunch: ActivityResultLauncher<Uri>
    private val itemAdapter = ExteriorItemRecyclerViewAdapter()
    private val photoAdapter = ExteroirPhotoRecyclerViewAdapter()

    private var holder: RecyclerView.ViewHolder? = null
    private var imageView : ImageView? = null
    private val args by navArgs<ExteriorFragmentArgs>()
    private lateinit var beginTime : String

    override fun init() {
        beginTime = date2String(Date(),"HHmmss")
        binding.rvExteriorItem.adapter = itemAdapter
        binding.rvExteriorPhoto.adapter = photoAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.getExteriorPhoto(args.beanR101.Hjlsh).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "init: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                photoAdapter.data = it.Body
            }
            viewModel.exteriorList(args.beanR101.Hjlsh).collect{
                if (it.Code != "1"){
                    Log.e("TAG", "init: ${it.Message}", )
                    showShortSnackbar(binding.root,it.Message)
                    return@collect
                }
                itemAdapter.data = it.Body
            }
        }
        itemAdapter.onItemViewClickListener = object : BaseRvAdapter.OnItemViewClickListener<ExteriorItemR104Response>{
            override fun onItemClick(view: View, position: Int, bean: ExteriorItemR104Response) {
                Log.e("TAG", "onItemClick: ${view.tag}", )
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
        photoAdapter.onItemViewClickListener = object :BaseRvAdapter.OnItemViewClickListener<ExteriorPhotoR102Response>{
            override fun onItemClick(view: View, position: Int, bean: ExteriorPhotoR102Response) {

                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            Log.e("TAG", "onItemClick: ${ex.message}", )

                            null
                        }
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

    private fun getExteriorPhotosData(Jyjgph : String): List<SavePhotoW102Request> {
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
                    Jyjgph,
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

    private fun submit(exteriorItem: ExteriorItemW101Request, exteriorPhotos : List<SavePhotoW102Request>) {
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

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            this.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityLaunch = registerForActivityResult(ActivityResultContracts.TakePicture()){
            if (it){
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
                BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
                    imageView?.setImageBitmap(bitmap)
                    imageView?.tag = "1"
                }
            }else{
                Log.e("TAG", "takePhoto: 拍照取消", )
            }
        }
    }

    private fun getExteriorItemData() : ExteriorItemW101Request{
        val workJcxm = StringBuilder()
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