package com.viaann.chatin.activity.ChangeActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.viaann.chatin.R

class ChangeIdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_id)

        supportActionBar?.title = "Id Account"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
