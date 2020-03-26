package com.example.classifierapp.Activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.classifierapp.Adapter.ViewPagerAdapter
import com.example.classifierapp.Fragments.PostFragment
import com.example.classifierapp.Fragments.SearchFragment
import com.example.classifierapp.Fragments.WatchListFragment
import com.example.classifierapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


import com.example.classifierapp.Extensions.logMessage
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAuthStateListener: FirebaseAuth.AuthStateListener
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    var PERMISSION_REQUEST_CODE=10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                var intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }

        }
        verifyPermission()
    }

    fun initViewpager() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0);

        viewPagerAdapter.addFragment(PostFragment(), "Post")
        viewPagerAdapter.addFragment(SearchFragment(), "Search")
        viewPagerAdapter.addFragment(WatchListFragment(), "WatchList")

        tabs.setupWithViewPager(viewpager, true)
        viewpager.adapter = viewPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.signOut -> {
                firebaseAuth.signOut()
                return true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser == null) {
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()

        }
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuthStateListener)

    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(firebaseAuthStateListener)
    }

    fun verifyPermission() {
        var permission = arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
        if (ContextCompat.checkSelfPermission(
                this,
                permission[0]
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                permission[1]
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                permission[2]
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            initViewpager()

        } else {
            ActivityCompat.requestPermissions(this, permission, PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        verifyPermission()
    }
}
