package com.viaann.chatin.fragment.SlideFirstLogin


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.viaann.chatin.R
import com.viaann.chatin.activity.EditProfileActivity
import kotlinx.android.synthetic.main.fragment_add_username.*

/**
 * A simple [Fragment] subclass.
 */
class AddUsernameFragment : Fragment() {

    val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child("profile")
        .child(FirebaseAuth.getInstance().currentUser?.uid!!)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_username, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAddUsername.setOnClickListener {
            if (addUsername.text.toString() == "") {
                val alert = AlertDialog.Builder(context!!, R.style.MyDialogTheme)
                alert.setMessage("Username must be filled")
                alert.setPositiveButton("OK"){alert, which -> }
                alert.show()
            } else {
                getChild.child("username").setValue(addUsername.text.toString())
                val alert = AlertDialog.Builder(context!!, R.style.MyDialogTheme)
                alert.setMessage("Success")
                alert.setPositiveButton("OK"){alert, which -> }
                alert.show()
            }
        }
    }
    }


