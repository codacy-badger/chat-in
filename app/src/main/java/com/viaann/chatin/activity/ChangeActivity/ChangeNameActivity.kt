package com.viaann.chatin.activity.ChangeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.viaann.chatin.R
import com.viaann.chatin.activity.EditProfileActivity
import kotlinx.android.synthetic.main.activity_display_name.*

class ChangeNameActivity : AppCompatActivity() {

    companion object {
        const val KEY_CHANGE_NAME = "CHANGE_NAME"
    }

    private val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
        .child("profile")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_name)

        supportActionBar?.title = "Display Name"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val oldUsername = intent.getStringExtra(KEY_CHANGE_NAME)
        changeName.setText(oldUsername)


        btnSaveChangeName.setOnClickListener {
            if (changeName.text.toString() == "") {
                val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)

                alert.setMessage("Name must be filled")
                alert.setPositiveButton("OK"){alert, which ->
                }
                alert.show()
            } else {
                getChild.child("username").setValue(changeName.text.toString())
                val intent = Intent(this, EditProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
