package com.yxf.vehiclehj.view.fragment

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
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
import com.yxf.vehiclehj.bean.ExteriorItemW101Request
import com.yxf.vehiclehj.bean.ExteriorPhotoR102Request
import com.yxf.vehiclehj.bean.ExteriorPhotoR102Response
import com.yxf.vehiclehj.bean.SavePhotoW102Request
import com.yxf.vehiclehj.databinding.FragmentExteriorBinding
import com.yxf.vehiclehj.repo.ExteriorRepo
import com.yxf.vehiclehj.utils.FILE_PROVIDER
import com.yxf.vehiclehj.utils.showShortSnackbar
import com.yxf.vehiclehj.viewModel.DatabaseViewModel
import com.yxf.vehiclehj.viewModel.DatabaseViewModelFactor
import com.yxf.vehiclehj.viewModel.ExteriorViewModel
import com.yxf.vehiclehj.viewModel.ExteriorViewModelFactory
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
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
    val itemAdapter = ExteriorItemRecyclerViewAdapter()
    val photoAdapter = ExteroirPhotoRecyclerViewAdapter()

    private var holder: RecyclerView.ViewHolder? = null
    private var imageView : ImageView? = null
    private val args by navArgs<ExteriorFragmentArgs>()

    override fun init() {
        binding.rvExteriorItem.adapter = itemAdapter
        itemAdapter.data = repo.inspectionGasItemList.entries.toList()
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
        }
        photoAdapter.data = repo.photoList
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
                submit(
                    ExteriorItemW101Request(
                        args.beanR101.Hjlsh,
                        args.beanR101.Hpzl,
                        args.beanR101.Hphm,
                        args.beanR101.Jccs,
                        "20230222",
                        "120000",
                        "130000",
                        "1",
                        "F1",
                        "",
                        ""
                    ),
                    getPhotos(it)
                )
            }


        }


    }

    private fun getPhotos(Jyjgph : String): List<SavePhotoW102Request> {
        val list = listOf(SavePhotoW102Request(
            args.beanR101.Hjlsh,
            Jyjgph,
            "1",
            args.beanR101.Jccs,
            args.beanR101.Hphm,
            args.beanR101.Hpzl,
            args.beanR101.VIN,
            "base64",
            "20230202",
            "F1",
            "100901",
            "前45度",
            args.beanR101.Hjdlsj
        ))



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

    override fun onAttach(context: Context) {
        super.onAttach(context)


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

}