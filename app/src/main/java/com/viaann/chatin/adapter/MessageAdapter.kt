package com.viaann.chatin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.viaann.chatin.R
import com.viaann.chatin.activity.ChatRoomActivity
import com.viaann.chatin.activity.MainActivity
import com.viaann.chatin.fragment.MessageFragment
import com.viaann.chatin.model.Contact
import com.viaann.chatin.model.Message
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter (private val context: Context, private val messageList: List<Message>)
    : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object {
        var onItemClick: ((Message) -> Unit)? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent,
            false))
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messageList[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatRoomActivity::class.java)
            intent.putExtra(ChatRoomActivity.ARGS_CHAT, messageList[position])
            context.startActivity(intent)
        }
    }

    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val tvMessage = view.findViewById<TextView>(R.id.tvChatMessage)
        val imgProfile = view.findViewById<CircleImageView>(R.id.imgProfileChat)

        fun bind (items: Message) {
            tvMessage.text = items.message
            tvUsername.text = items.username
            items.imageUrl.let { Picasso.get().load(it).into(imgProfile) }
        }
    }
}