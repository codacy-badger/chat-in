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
import com.viaann.chatin.activity.NewMessageActivity
import com.viaann.chatin.adapter.ContactAdapter
import com.viaann.chatin.adapter.MessageAdapter
import com.viaann.chatin.model.Contact
import com.viaann.chatin.model.Message
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * A simple [Fragment] subclass.
 */
class MessageFragment : Fragment() {

    companion object {
        const val ARGS_MESSAGE = "ARGS_MESSAGE"
    }

    private val auth = FirebaseAuth.getInstance()
    private val ref = FirebaseDatabase.getInstance().reference
    private val uid = "${auth.currentUser?.uid}"
    private val query = ref.child("/users/message/$uid/").orderByChild("idAccount")
    lateinit var postListener: ValueEventListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val messageList: MutableList<Message> = mutableListOf()
                messageList.clear()
                for (i in dataSnapshot.children) {
                    val getMessage = i.getValue(Message::class.java)
                    messageList.add(getMessage!!)
                }
                val adapter = MessageAdapter(context!!, messageList)
                adapter.notifyDataSetChanged()
                rvMessage.layoutManager = LinearLayoutManager(context)
                rvMessage.adapter = adapter

                }
            }
        query.addListenerForSingleValueEvent(postListener)

        addNewMessage.setOnClickListener {
            val intent = Intent(context, NewMessageActivity::class.java)
            startActivity(intent)
        }
    }
}
