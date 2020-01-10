package com.viaann.chatin.activity.ChangeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.viaann.chatin.R
import com.viaann.chatin.activity.EditProfileActivity
import kotlinx.android.synthetic.main.activity_change_status.*
import kotlinx.android.synthetic.main.activity_display_name.*

class ChangeStatusActivity : AppCompatActivity() {

    companion object {
        const val  KEY_CHANGE_STATUS = "CHANGE_STATUS"
    }

    private val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child("profile")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_status)

        supportActionBar?.title = "Status"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val oldStatus = intent.getStringExtra(KEY_CHANGE_STATUS)
        changeStatus.setText(oldStatus)


        btnSaveChangeStatus.setOnClickListener {
            getChild.child("status").setValue(changeStatus.text.toString())
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
