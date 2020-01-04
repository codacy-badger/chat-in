package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.viaann.chatin.R

class AccountSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        supportActionBar?.title = "Acccount Setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
