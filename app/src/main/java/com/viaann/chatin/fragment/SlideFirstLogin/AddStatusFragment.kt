package com.viaann.chatin.fragment.SlideFirstLogin


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.viaann.chatin.R
import com.viaann.chatin.activity.EditProfileActivity
import kotlinx.android.synthetic.main.fragment_add_id.*
import kotlinx.android.synthetic.main.fragment_add_status.*

/**
 * A simple [Fragment] subclass.
 */
class AddStatusFragment : Fragment() {


    val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child("profile")
        .child(FirebaseAuth.getInstance().currentUser?.uid!!)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddStatus.setOnClickListener {
            getChild.child("status").setValue(addStatus.text.toString())
        }

    }
}
