package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.viaann.chatin.R
import com.viaann.chatin.adapter.ChatAdapter
import com.viaann.chatin.model.Chat
import kotlinx.android.synthetic.main.activity_chat_room.*
import java.util.*
import kotlin.collections.HashMap

class ChatRoomActivity : AppCompatActivity() {

    companion object {
        const val ARGS_CHAT = "ARGS_CHAT"
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        var message = findViewById<EditText>(R.id.editTextMessage)
        val intent = intent
        val items = intent.getParcelableExtra<com.viaann.chatin.model.Message>(ARGS_CHAT)
        val toolbar = findViewById<Toolbar>(R.id.toolbarChat)
        val toolbarTitle = toolbar.findViewById<TextView>(R.id.tvToolbarChat)
        val arrowBack = toolbar.findViewById<ImageView>(R.id.arrowBackChat)

        arrowBack.setOnClickListener {
            onBackPressed()
        }
        toolbarTitle.text = items?.username

        val userid = items?.idAccount


        btnSend.setOnClickListener {
            val msg = message.text.toString().trim()
            val chat = Chat()
            if  (msg != "") {
                sendMessage(auth.currentUser?.uid!!, userid!! , msg, items?.username!!)
            }
            toolbarTitle.text = chat.username
            readMessage(auth.currentUser?.uid!!, userid!!)
            message.setText("")
        }
    }

    private fun sendMessage(mSender: String, mReceiver: String, mMessage: String, mUsername: String) {
        val auth = FirebaseAuth.getInstance()
        val myRef = FirebaseDatabase.getInstance().getReference("users")

        val chat = HashMap<String, Any>()
        chat.put("sender", mSender)
        chat.put("receiver", mReceiver)
        chat.put("message", mMessage)
        chat.put("username", mUsername)

        myRef.child("chat").child(auth.currentUser?.uid!!).setValue(chat)

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val getUsername = dataSnapshot.child("username").getValue(String::class.java)
                val getReceiver = dataSnapshot.child("receiver").getValue(String::class.java)
                val getSender = dataSnapshot.child("sender").getValue(String::class.java)
                val getMessage = dataSnapshot.child("message").getValue(String::class.java)

                val mChat = Chat()
                with (mChat) {
                    username = getUsername
                    receiver = getReceiver
                    sender = getSender
                    message = getMessage
                }

            }

        }
        myRef.addListenerForSingleValueEvent(postListener)
    }


    private fun readMessage(uid: String, userid: String) {
        val chats: MutableList<Chat?> = mutableListOf()
        val myRef = FirebaseDatabase.getInstance().getReference("chat")

        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chats.clear()
                for (i in dataSnapshot.children) {
                    val mChat: Chat? = i.getValue<Chat>(Chat::class.java)
                    if (mChat?.receiver.equals(userid) && mChat?.sender.equals(uid) ||
                            mChat?.receiver.equals(uid) && mChat?.sender.equals(uid)) {
                        chats.add(mChat)
                    }
                }
                val chatAdapter = ChatAdapter(applicationContext, chats)
                chatAdapter.notifyDataSetChanged()
                rvChatLog.adapter = chatAdapter
            }
        }
        myRef.addValueEventListener(postListener)
    }
}