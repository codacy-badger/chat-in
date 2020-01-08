package com.viaann.chatin.SlideFirstLogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.viaann.chatin.R
import com.viaann.chatin.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_slide.*

class SlideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
    }
}
