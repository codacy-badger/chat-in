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
import com.viaann.chatin.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_change_id.*
import kotlinx.android.synthetic.main.activity_display_name.*
import kotlinx.android.synthetic.main.activity_edit_profile.*

class ChangeIdActivity : AppCompatActivity() {

    companion object {
        const val KEY_CHANGE_ID = "CHANGE_ID"
    }

    private val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child("profile")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_id)

        supportActionBar?.title = "Id Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val oldId = intent.getStringExtra(KEY_CHANGE_ID)
        changeId.setText(oldId)

        btnSaveChangeId.setOnClickListener {
            if (changeId.text.toString() == "") {
                val alert = AlertDialog.Builder(this, R.style.MyDialogTheme)

                alert.setMessage("Id Account must be filled")
                alert.setPositiveButton("OK"){alert, which ->
                }
                alert.show()

            } else {
                getChild.child("idAccount").setValue(changeId.text.toString())
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
