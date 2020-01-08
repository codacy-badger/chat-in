package com.viaann.chatin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.viaann.chatin.SlideFirstLogin.*

class ViewPagerAdapter (fm : FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AddUsernameFragment()
            }
            1 -> {
                AddStatusFragment()
            }
            2 -> {
                AddImageFragment()
            }
            else -> {
                VerificationFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }
}