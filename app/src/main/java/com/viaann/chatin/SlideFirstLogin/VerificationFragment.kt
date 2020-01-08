package com.viaann.chatin.SlideFirstLogin


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import com.viaann.chatin.R
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
                dialog.setMessage("Please check your email")
                dialog.setPositiveButton("OK") { dialog, which -> }
                dialog.show()
        }
    }
}
