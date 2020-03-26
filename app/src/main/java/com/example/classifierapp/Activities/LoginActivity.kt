package com.example.classifierapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.classifierapp.Extensions.showToast
import com.example.classifierapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    var TAG = "mytag"
    lateinit var authStateListerner: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        authStateListerner = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                var intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        }

        signUp_button.setOnClickListener {
            var intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        signIn_button.setOnClickListener {
            var email = emailEditText.text.toString()
            var password = password_editText.text.toString()
            Log.d(TAG, email)
            if (email.equals("") or password.equals("")) {
                showToast("Empty fields are not allowed")
            } else {
                signIn(email, password)
            }
        }
    }
    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                run {
                    if (task.isSuccessful) showToast("SignIn") else showToast(task.exception?.message.toString())
                    Log.d(TAG, task.exception?.message.toString())

                }
            }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(authStateListerner)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener { authStateListerner }
    }
}
