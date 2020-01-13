package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.R
import com.viaann.chatin.model.Contact
import com.viaann.chatin.utils.invisible
import com.viaann.chatin.utils.visible
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val uid = "${auth.currentUser?.uid}"
    private val myRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)


        supportActionBar?.title = "Add Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnAddContact.invisible()

        imageSearchContact.setOnClickListener {

            val ref = FirebaseDatabase.getInstance().reference
            val query = ref.child("/users/profile/").orderByChild("idAccount")
                .equalTo(txtAddContact.text.toString())


            val postListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentIdAccount = dataSnapshot.child("idAccount").getValue(String::class.java)

                    for (i in dataSnapshot.children) {
                        if (i.child("idAccount").getValue(String::class.java)?.contains(txtAddContact.text.toString())!!) {
                            val getStatus = i.child("status").getValue(String::class.java)
                            val getUsername = i.child("username").getValue(String::class.java)
                            val getImageUrl = i.child("imageUrl").getValue(String::class.java)
                            val getIdAccount = i.child("idAccount").getValue(String::class.java)


                            if (currentIdAccount == getIdAccount) {
                                btnAddContact.invisible()
                                tvContactUsername.text = "You cant add yourself as a friend"
                                imageAddContact.invisible()
                            } else {
                                tvContactUsername.text = getUsername
                                Glide.with(applicationContext)
                                    .load(getImageUrl)
                                    .fitCenter()
                                    .into(imageAddContact)
                                btnAddContact.visible()
                            }

                            btnAddContact.setOnClickListener {
                                val contact = HashMap<String, String>()
                                contact.put("idAccount", getIdAccount.toString())
                                contact.put("imageUrl", getImageUrl.toString())
                                contact.put("status", getStatus.toString())
                                contact.put("username", getUsername.toString())

                                myRef.child("contact")
                                    .child(uid)
                                    .child(getIdAccount.toString())
                                    .setValue(contact)
                            }
                        }
                    }
                }

            }
            query.addListenerForSingleValueEvent(postListener)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
