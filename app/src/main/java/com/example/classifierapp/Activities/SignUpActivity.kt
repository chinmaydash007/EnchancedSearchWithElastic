package com.example.classifierapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.classifierapp.Extensions.showToast
import com.example.classifierapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    var TAG="mytag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()


        signUpbutton.setOnClickListener {
            var email = emailEditText.text.toString()
            var password = password_editText.text.toString()
            var confirm_password = confirm_password_editText.text.toString()

            if (email.equals("") or password.equals("") or confirm_password.equals("")) {
                showToast("Empty fields are not allowed")
            } else if (!password.equals(confirm_password)) {
                showToast("Passwords don't match")
            } else {
                signUpUser(email, password)
            }

        }
    }
    private fun signUpUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showToast("Successfully created the account")

                } else {
                    showToast(task.exception?.message.toString())
                    Log.d(TAG, task.exception?.message.toString())
                }
            }
    }
}
