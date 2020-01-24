package com.viaann.chatin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.viaann.chatin.R
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    companion object {
        const val ARGS_CHAT = "ARGS_CHAT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val intent = intent
        val items = intent.getParcelableExtra<com.viaann.chatin.model.Message>(ARGS_CHAT)
        val toolbar = findViewById<Toolbar>(R.id.toolbarChat)
        val toolbarTitle = toolbar.findViewById<TextView>(R.id.tvToolbarChat)
        val arrowBack = toolbar.findViewById<ImageView>(R.id.arrowBackChat)

        arrowBack.setOnClickListener {
            onBackPressed()
        }
        toolbarTitle.text = items?.username
    }
}
