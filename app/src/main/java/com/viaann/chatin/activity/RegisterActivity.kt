package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.viaann.chatin.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.editTextEmail
import kotlinx.android.synthetic.main.activity_register.editTextPassword

class RegisterActivity : AppCompatActivity() {


    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val myRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var email = findViewById<EditText>(R.id.editTextEmail)
        var password = findViewById<EditText>(R.id.editTextPassword)
        var confirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        var idAccount = findViewById<EditText>(R.id.editTextIdAccount)


        buttonSignup.setOnClickListener {
            // for check if password value and confirm password is same
            if (password.text.toString()  == confirmPassword.text.toString()) {
                // register account using firebase auth
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)
                            val user = HashMap<String, String>()

                            alert.setMessage("Register Success")
                            alert.setPositiveButton("OK"){alert, which ->
                            }
                            alert.show()

                            user.put("email", email.text.toString())
                            user.put("idAccount", idAccount.text.toString())
                            user.put("imageUrl", "")
                            user.put("status", "")
                            user.put("username", "")
                            myRef.child(idAccount.text.toString()).child("profile").setValue(user)


                            // make all edit text is null after the register is success
                            email.text.clear()
                            password.text.clear()
                            confirmPassword.text.clear()
                            idAccount.text.clear()
                        } else {
                            Log.d("RegisterActivity", it.exception.toString())
                            val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)

                            alert.setMessage("Register Fail")
                            alert.setPositiveButton("OK"){alert, which ->
                            }
                            alert.show()
                        }
                    }
            } else {
                val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)

                alert.setMessage("Password and Confirm Password not same")
                alert.setPositiveButton("OK"){alert, which ->
                }
                alert.show()
            }
        }

        tvSignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }
}
