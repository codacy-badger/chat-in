package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.viaann.chatin.R
import kotlinx.android.synthetic.main.activity_old_password.*

class OldPasswordActivity : AppCompatActivity() {

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_password)

        btnOldPassword.setOnClickListener {

        }

    }
}
