package com.viaann.chatin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.viaann.chatin.R
import com.viaann.chatin.activity.MainActivity
import com.viaann.chatin.fragment.MessageFragment
import com.viaann.chatin.model.Contact
import de.hdodenhof.circleimageview.CircleImageView

class NewMessageAdapter (private val contactList: List<Contact>, private val context: Context)
    : RecyclerView.Adapter<NewMessageAdapter.ContactViewHolder>(){

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val uid = "${auth.currentUser?.uid}"
    private val myRef = database.getReference("users")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent,
            false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])


        holder.itemView.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(MessageFragment.ARGS_MESSAGE, contactList[position])
            context.startActivity(intent)

            val messageList = mapOf<String?, String?>(
                "idAccount" to contactList[0].idAccount,
                "username" to contactList[0].username,
                "imageUrl" to contactList[0].imageUrl,
                "previewMsg" to "")

            myRef.child("message")
                .child(auth.currentUser?.uid!!)
                .child(contactList[0].username!!)
                .setValue(messageList)
        }
    }

    class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageContact = view.findViewById<CircleImageView>(R.id.imgContact)
        val tvUsernameConatct = view.findViewById<TextView>(R.id.tvUsernameContact)
        val tvStatusContact = view.findViewById<TextView>(R.id.tvStatusContact)

        fun bind (items: Contact) {
            tvUsernameConatct.text = items.username
            tvStatusContact.text = items.status
            items.imageUrl.let { Picasso.get().load(it).into(imageContact) }
        }
    }
}