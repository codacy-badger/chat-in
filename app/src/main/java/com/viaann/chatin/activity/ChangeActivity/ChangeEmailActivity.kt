package com.viaann.chatin.activity.ChangeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.R
import kotlinx.android.synthetic.main.activity_change_email.*
import java.lang.Exception
import java.util.*

class ChangeEmailActivity : AppCompatActivity() {

    companion object {
        lateinit var getEmail: String
    }

    private val getChild = FirebaseDatabase.getInstance()
        .getReference("usersProfile")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
        .child("profile")

    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                getEmail = dataSnapshot.child("email").getValue(String::class.java)!!
            }
        }
        getChild.addListenerForSingleValueEvent(postListener)

        changeEmail.setText(getEmail)

        btnSaveChangeEmail.setOnClickListener {

            try {
                getChild.child("email").setValue(changeEmail.text)
                Toast.makeText(this, "Email Updated", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.message
            }
        }
    }
}
