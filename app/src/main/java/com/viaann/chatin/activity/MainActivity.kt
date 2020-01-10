package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.fragment.ContactFragment
import com.viaann.chatin.fragment.MessageFragment
import com.viaann.chatin.R
import com.viaann.chatin.fragment.SettingFragment
import com.viaann.chatin.fragment.SlideFirstLogin.SlideActivity
import com.viaann.chatin.model.User
import kotlinx.android.synthetic.main.activity_main.*
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
                val intent = Intent(this, SlideActivity::class.java)
                startActivity(intent)
            } else {

            }
        }

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val getIdAccount = dataSnapshot.child("idAccount").getValue(User::class.java)
                if (dataSnapshot.hasChild("/profile/$uid/")) {
                    return
                } else if  (!dataSnapshot.hasChild("/profile/$uid/") && auth.currentUser != null)  {
                    // add new child field and value in real time database
                    val user = HashMap<String, String>()
                    user.put("email", auth.currentUser?.email!!)
                    user.put("idAccount", "a")
                    user.put("imageUrl", "")
                    user.put("status", "")
                    user.put("username", "")
                    myRef.child("profile").child(auth.currentUser?.uid!!).setValue(user)
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
