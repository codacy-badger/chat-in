package com.viaann.chatin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.viaann.chatin.R
import com.viaann.chatin.model.Contact
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_new_contact.*

class ContactAdapter (private val contactList: List<Contact>, private val context: Context)
    : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent,
        false))
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position])
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