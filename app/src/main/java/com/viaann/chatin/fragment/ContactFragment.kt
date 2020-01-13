package com.viaann.chatin.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.R
import com.viaann.chatin.activity.NewContactActivity
import com.viaann.chatin.adapter.ContactAdapter
import com.viaann.chatin.model.Contact
import kotlinx.android.synthetic.main.fragment_contact.*

/**
 * A simple [Fragment] subclass.
 */
class ContactFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private val ref = FirebaseDatabase.getInstance().reference
    private val uid = "${auth.currentUser?.uid}"
    private val query = ref.child("/users/contact/$uid/").orderByChild("idAccount")
    lateinit var postListener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                val adapter = ContactAdapter(contactList, context!!)
                adapter.notifyDataSetChanged()
                rvContact.layoutManager = LinearLayoutManager(context)
                rvContact.adapter = adapter
            }
        }
        query.addListenerForSingleValueEvent(postListener)

        addNewContact.setOnClickListener {
            val intent = Intent(context, NewContactActivity::class.java)
            startActivity(intent)
        }
    }

}
