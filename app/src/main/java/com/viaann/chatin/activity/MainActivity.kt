package com.viaann.chatin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.viaann.chatin.ContactFragment
import com.viaann.chatin.MessageFragment
import com.viaann.chatin.R
import com.viaann.chatin.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {

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
