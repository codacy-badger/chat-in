package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.viaann.chatin.R

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        supportActionBar?.title = "Add Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
