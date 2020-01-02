package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.viaann.chatin.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.editTextEmail
import kotlinx.android.synthetic.main.activity_register.editTextPassword

class RegisterActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var email = findViewById<EditText>(R.id.editTextEmail)
        var password = findViewById<EditText>(R.id.editTextPassword)
        var confirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)

        buttonSignup.setOnClickListener {
            // for check if password value and confirm password is same
            if (password.text.toString()  == confirmPassword.text.toString()) {
                // register account using firebase auth
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                            // make all edit text is null after the register is success
                            email.text = null
                            password.text = null
                            confirmPassword.text = null
                        } else {
                            Log.d("RegisterActivity", it.exception.toString())
                            Toast.makeText(this, "Register Fail", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Password and Confirm Password not same", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
