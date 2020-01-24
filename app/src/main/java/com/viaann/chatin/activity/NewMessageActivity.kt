package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.R
import com.viaann.chatin.adapter.ContactAdapter
import com.viaann.chatin.adapter.NewMessageAdapter
import com.viaann.chatin.model.Contact
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.fragment_contact.*

class NewMessageActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val ref = FirebaseDatabase.getInstance().reference
    private val uid = "${auth.currentUser?.uid}"
    private val query = ref.child("/users/contact/$uid/").orderByChild("idAccount")
    lateinit var postListener: ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select Contact"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val contactList: MutableList<Contact> = mutableListOf()
                contactList.clear()
                for (i in dataSnapshot.children) {
                    val getContact = i.getValue(Contact::class.java)
                    contactList.add(getContact!!)
                }
                val adapter = NewMessageAdapter(contactList, applicationContext)
                adapter.notifyDataSetChanged()
                rvNewMessage.layoutManager = LinearLayoutManager(applicationContext)
                rvNewMessage.adapter = adapter


            }
        }
        query.addListenerForSingleValueEvent(postListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


