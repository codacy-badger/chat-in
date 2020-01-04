package com.viaann.chatin.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.viaann.chatin.R
import com.viaann.chatin.activity.AccountSettingActivity
import com.viaann.chatin.activity.EditProfileActivity
import com.viaann.chatin.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvChangeAccount.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            FirebaseAuth.getInstance().signOut()
            startActivity(intent)
        }

        tvEditProfile.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        tvAccountSetting.setOnClickListener {
            val intent = Intent(context, AccountSettingActivity::class.java)
            startActivity(intent)
        }
    }

}
