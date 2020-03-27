package com.example.classifierapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.classifierapp.Dialog.SelectPhotoDialog

import com.example.classifierapp.R
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment(), SelectPhotoDialog.OnPhotoSelectedListerner {
    lateinit var imageView: ImageView
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_post, container, false)
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


        return view
    }

    override fun getImagePath(uri: Uri) {
        imageView.setImageURI(uri)

    }

    override fun getImageBitmap(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

}
