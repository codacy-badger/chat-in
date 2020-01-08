package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.fragment.ContactFragment
import com.viaann.chatin.fragment.MessageFragment
import com.viaann.chatin.R
import com.viaann.chatin.SlideFirstLogin.SlideActivity
import com.viaann.chatin.fragment.SettingFragment
import com.viaann.chatin.model.User
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val uid = "${auth.currentUser?.uid}"
    private val myRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else if (auth.currentUser != null) {

            if (!auth.currentUser!!.isEmailVerified) {

            } else {
                return
            }
        }

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val getIdAccount = dataSnapshot.child("idAccount").getValue(User::class.java)
                if (dataSnapshot.hasChild(uid)) {
                    return
                } else if  (!dataSnapshot.hasChild(uid) && auth.currentUser != null)  {
                    // add new child field and value in real time database

                } else {
                    return
                }
            }
        }
        myRef.addListenerForSingleValueEvent(postListener)

        //add fragment to bottom navigation view
        bottom_navigation.setOnNavigationItemSelectedListener {
            item -> when (item.itemId) {
                R.id.contact -> {
                    loadContactFragment(savedInstanceState)
                }

                R.id.message -> {
                    loadMessageFragment(savedInstanceState)
                }

                R.id.setting -> {
                    loadSettingFragment(savedInstanceState)
                }
            }
            true

        }

        bottom_navigation.selectedItemId = R.id.message
    }

    private fun loadContactFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    ContactFragment(), ContactFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadMessageFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    MessageFragment(), MessageFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadSettingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    SettingFragment(), SettingFragment::class.java.simpleName)
                .commit()
        }
    }
}
