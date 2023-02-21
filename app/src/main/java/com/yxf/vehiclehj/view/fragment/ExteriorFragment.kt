package com.yxf.vehiclehj.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehiclehj.R
import com.yxf.vehiclehj.bean.ImageItemR102Response
import com.yxf.vehiclehj.databinding.FragmentExteriorBinding
import com.yxf.vehiclehj.repo.ExteriorRepo
import com.yxf.vehiclehj.utils.FILE_PROVIDER
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    private lateinit var currentPhotoPath: String
    val repo = ExteriorRepo()
    lateinit var startActivityLaunch: ActivityResultLauncher<Uri>
    val itemAdapter = ExteriorItemRecyclerViewAdapter()
    val photoAdapter = ExteroirPhotoRecyclerViewAdapter()

    private var holder: RecyclerView.ViewHolder? = null
    private var imageView : ImageView? = null

    override fun init() {
        binding.rvExteriorItem.adapter = itemAdapter
        itemAdapter.data = repo.inspectionGasItemList.entries.toList()
        binding.rvExteriorPhoto.adapter = photoAdapter
        photoAdapter.data = repo.photoList
        photoAdapter.onItemViewClickListener = object :BaseRvAdapter.OnItemViewClickListener<ImageItemR102Response>{
            override fun onItemClick(view: View, position: Int, bean: ImageItemR102Response) {

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
            findNavController().popBackStack()
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