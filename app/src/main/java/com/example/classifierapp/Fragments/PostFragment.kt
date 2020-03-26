package com.example.classifierapp.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.classifierapp.R
import kotlinx.android.synthetic.main.fragment_post.*

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment() {

    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_post, container, false)
    }

}
