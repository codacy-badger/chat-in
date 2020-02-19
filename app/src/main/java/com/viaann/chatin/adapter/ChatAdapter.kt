package com.viaann.chatin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.viaann.chatin.R
import com.viaann.chatin.model.Chat
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter (private val context: Context, private val chat: List<Chat?>)
    : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    companion object {
        const val LEFT = 0
        const val RIGHT = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == RIGHT) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_my_message, parent, false)
            return ViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_their_message, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return chat.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chat[position]!!)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.name)
        val avatar = view.findViewById<CircleImageView>(R.id.avatar)
        val message = view.findViewById<TextView>(R.id.message_body)

        fun bind (items: Chat) {
            name.text = items.username
            message.text = items.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val user = FirebaseAuth.getInstance().currentUser
        if (chat.get(position)?.sender.equals(user?.uid)) {
            return RIGHT
        } else {
            return LEFT
        }
    }
}