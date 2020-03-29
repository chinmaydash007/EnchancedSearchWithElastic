package com.example.classifierapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.classifierapp.Dialog.SelectPhotoDialog
import com.example.classifierapp.Extensions.logMessage
import com.example.classifierapp.Extensions.showToast

import com.example.classifierapp.R
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.net.URI
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment(), SelectPhotoDialog.OnPhotoSelectedListerner, View.OnClickListener {
    lateinit var imageView: ImageView
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12
    var bitmap: Bitmap? = null
    var uri: Uri? = null
    lateinit var uploadByteArray: ByteArray

    override fun getImagePath(uri: Uri) {
        imageView.setImageURI(uri)
        this.uri = uri
        this.bitmap = null
    }

    override fun getImageBitmap(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
        this.bitmap = bitmap
        this.uri = null
    }

    internal lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_post, container, false)
        imageView = view.post_image
        // Inflate the layout for this fragment
        view.post_image.setOnClickListener {
            var selectPhotoDialog = SelectPhotoDialog()
            selectPhotoDialog.show(
                requireActivity().supportFragmentManager,
                getString(R.string.dialog_fragment)
            )
            selectPhotoDialog.setTargetFragment(this, 1)

        }
        view.btn_post.setOnClickListener(this)
        return view
    }


    override fun onClick(v: View?) {
        if (TextUtils.isEmpty(view.input_title.text) ||
            TextUtils.isEmpty(view.input_description.text) ||
            TextUtils.isEmpty(view.input_price.text) ||
            TextUtils.isEmpty(view.input_country.text) ||
            TextUtils.isEmpty(view.input_state_province.text) ||
            TextUtils.isEmpty(view.input_city.text) ||
            TextUtils.isEmpty(view.input_email.text)
        ) {
            activity?.showToast("Empty fields are not allowed")
        } else {
        if (uri != null && bitmap == null) {
            uri?.uploadNewPhoto()
        } else if (uri == null && bitmap != null) {
            bitmap?.uploadNewPhoto()
        }

        }


    }


    //For Bitmap
    private fun Bitmap?.uploadNewPhoto() {
        logMessage("For Bitmap")
        CoroutineScope(Dispatchers.IO).launch {
            var job = async {

                uploadByteArray = getBytesFromBitmap(bitmap!!, 100)

            }
            try {
                job.await()
            } catch (e: Exception) {
                logMessage(e.message.toString())
            }
        }
    }

    //For URI
    private fun Uri?.uploadNewPhoto() {
        logMessage("For URI")
        CoroutineScope(Dispatchers.IO).launch {
            //For android Version< N
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    activity?.contentResolver,
                    uri
                )
                uploadByteArray = getBytesFromBitmap(bitmap, 100)
                logMessage(uploadByteArray.contentToString())
            } else {
                //For android Version>N
                val source = ImageDecoder.createSource(activity?.contentResolver!!, uri!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                uploadByteArray = getBytesFromBitmap(bitmap, 100)
                logMessage(uploadByteArray.contentToString())
            }
        }
    }


    suspend fun getBytesFromBitmap(bitmap: Bitmap, quality: Int): ByteArray {
        logMessage("get")
        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        return stream.toByteArray()
    }


}


