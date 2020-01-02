package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.viaann.chatin.ContactFragment
import com.viaann.chatin.MessageFragment
import com.viaann.chatin.R
import com.viaann.chatin.SettingFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else if (auth.currentUser != null){

            if (!auth.currentUser!!.isEmailVerified) {
                // send email verification
                auth.currentUser!!.sendEmailVerification()
                // signout account
                auth.signOut()
                // move to login activity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "unverified account, please verified your account on email", Toast.LENGTH_LONG).show()
            } else {

            }
        }

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
                .replace(R.id.main_container, ContactFragment(), ContactFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadMessageFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MessageFragment(), MessageFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadSettingFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, SettingFragment(), SettingFragment::class.java.simpleName)
                .commit()
        }
    }
}
