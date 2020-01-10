package com.viaann.chatin.fragment.SlideFirstLogin


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import com.viaann.chatin.R
import com.viaann.chatin.activity.LoginActivity
import com.viaann.chatin.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_verification.*

/**
 * A simple [Fragment] subclass.
 */
class VerificationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonVerification.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()

            val dialog = AlertDialog.Builder(context, R.style.MyDialogTheme)
            val intent = Intent(context, LoginActivity::class.java)

            dialog.setMessage("Please check your email, and Login again")
            dialog.setPositiveButton("OK") { dialog, which ->
                FirebaseAuth.getInstance().signOut()
                startActivity(intent)
            }
            dialog.show()


        }
    }
}
