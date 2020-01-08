package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.viaann.chatin.R
import com.viaann.chatin.activity.ChangeActivity.ChangeEmailActivity
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity() {

    private val user = FirebaseAuth.getInstance().currentUser
    private val getChild = FirebaseDatabase.getInstance()
        .getReference("usersProfile")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
        .child("profile")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        supportActionBar?.title = "Acccount Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvSettingChangeEmail.setOnClickListener {
            val intent = Intent(this, ChangeEmailActivity::class.java)
            startActivity(intent)
        }

        tvSettingChangePassword.setOnClickListener {

        }

        tvSettingDeleteAccount.setOnClickListener {
            val alert = AlertDialog.Builder(this@AccountSettingActivity, R.style.MyDialogTheme)

            alert.setTitle("Delete Account")
            alert.setMessage("Sure Delete Account?")
            alert.setPositiveButton("Yes"){alert, which ->
                // remove account in firebase auth
                user?.delete()

                // for remove child value in database
                getChild.removeValue()

                Toast.makeText(this, "Account Has been Delete", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            alert.setNegativeButton("Cancel"){alert, which->
                Toast.makeText(this, "Cancel to Delete account", Toast.LENGTH_SHORT).show()
            }
            alert.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
