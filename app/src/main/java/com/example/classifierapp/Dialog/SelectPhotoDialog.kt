package com.example.classifierapp.Dialog


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.classifierapp.R
import kotlinx.android.synthetic.main.select_photo_dialog.*
import java.util.jar.Manifest

class SelectPhotoDialog : DialogFragment() {
    var PICK_IMAGE_REQUEST_CODE = 1;
    var CAMERA_REQUET_CODE = 2;
    lateinit var onPhotoSelectedListerner: OnPhotoSelectedListerner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.select_photo_dialog, container, false)
        dialogChoosePhoto.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
        dialogOpenCamera.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUET_CODE)

        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            var selectedImageUri = data?.data
            if (selectedImageUri != null) {
                onPhotoSelectedListerner.getImagePath(selectedImageUri)
            }

        } else if (requestCode == CAMERA_REQUET_CODE && resultCode == Activity.RESULT_OK) {
            var bitmap: Bitmap = data?.extras?.get("data") as Bitmap
            onPhotoSelectedListerner.getImageBitmap(bitmap)

        }
    }

    interface OnPhotoSelectedListerner {
        fun getImagePath(uri: Uri)
        fun getImageBitmap(bitmap: Bitmap)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onPhotoSelectedListerner = activity as OnPhotoSelectedListerner
    }


}