package com.viaann.chatin.SlideFirstLogin


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
import kotlinx.android.synthetic.main.activity_change_status.*
import kotlinx.android.synthetic.main.fragment_add_status.*

/**
 * A simple [Fragment] subclass.
 */
class AddStatusFragment : Fragment() {

    companion object {
        lateinit var getIdAccount: String
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                getIdAccount = p0.child("idAccount").getValue(String::class.java)!!
            }

        }

        val getChild = FirebaseDatabase.getInstance()
            .getReference("users")
            .child(AddUsernameFragment.getIdAccount)
            .child("profile")

        getChild.addListenerForSingleValueEvent(postListener)

        getChild.child("status").setValue(addStatus.text.toString())
        val intent = Intent(context, EditProfileActivity::class.java)
        startActivity(intent)
    }
}
