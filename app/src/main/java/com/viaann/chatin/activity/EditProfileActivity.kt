package com.viaann.chatin.activity

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.viaann.chatin.R
import com.viaann.chatin.activity.ChangeActivity.ChangeIdActivity
import com.viaann.chatin.activity.ChangeActivity.ChangeNameActivity
import com.viaann.chatin.activity.ChangeActivity.ChangeStatusActivity
import com.viaann.chatin.model.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*
import javax.microedition.khronos.opengles.GL
import kotlin.collections.HashMap

class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1
        const val KEY_CHANGE_NAME = "CHANGE_NAME"
        const val KEY_CHANGE_STATUS = "CHANGE_STATUS"
        const val KEY_CHANGE_ID = "CHANGE_ID"
    }

    private var firebaseStorage = FirebaseStorage.getInstance()
    private var storageRefrence = firebaseStorage.reference
    private var filePath: Uri? = null
    private val auth = FirebaseAuth.getInstance()
    private val user = User()
    private val  getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child(user.idAccount!!) // error nih bangsatt !!
        .child("profile")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val postListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val getUsername = dataSnapshot.child("username").getValue(String::class.java)
                val getStatus =  dataSnapshot.child("status").getValue(String::class.java)
                val getId = dataSnapshot.child("idAccount").getValue(String::class.java)
                val getImage = dataSnapshot.child("imageUrl").getValue(String::class.java)
                val getEmail = dataSnapshot.child("email").getValue(String::class.java)

                // store child value to data class
                val user = User()
                    with (user) {
                        idAccount = getId
                        username = getUsername
                        email = getEmail
                        status = getStatus
                        imageUrl = getImage
                    }

                tvEditDisplayName.text = user.idAccount
                tvEditStatus.text = getStatus
                tvChangeId.text = getId


                Glide.with(applicationContext)
                    .load(getImage)
                    .fitCenter()
                    .into(changeImg)
            }

        }



        getChild.addListenerForSingleValueEvent(postListener)



        tvEditStatus.setOnClickListener {
            val intent = Intent(this, ChangeStatusActivity::class.java)
            // pass the child value to edit text in change activity
            if (tvEditStatus.text.toString() != "not yet filled") {
                intent.putExtra(ChangeStatusActivity.KEY_CHANGE_STATUS, tvEditStatus.text.toString())
            } else {
                intent.putExtra(ChangeStatusActivity.KEY_CHANGE_STATUS, "")
            }
            startActivity(intent)
        }

        tvChangeId.setOnClickListener {
            val intent = Intent(this, ChangeIdActivity::class.java)
            // pass the child value to edit text in change activity
            if (tvChangeId.text.toString() != "not yet filled") {
                intent.putExtra(ChangeIdActivity.KEY_CHANGE_ID, tvChangeId.text.toString())
            } else {
                intent.putExtra(ChangeIdActivity.KEY_CHANGE_ID, "")
            }
            startActivity(intent)
        }

        changeImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type ="image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"),
                REQUEST_CODE_PICK_IMAGE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null) {
            filePath = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            changeImg.setImageBitmap(bitmap)
            uploadImage()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageRefrence.child("${auth.currentUser?.email}/" + UUID.randomUUID().toString())
            val uploadTask = ref.putFile(filePath!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                if (!it.isSuccessful) {
                    it.exception?.let {
                        throw  it
                    }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener {
                if (it.isSuccessful) {
                    val downloadUrl = it.result
                    //addUploadRecordToDb(downloadUrl.toString())
                }
            }
        }
    }

//    private fun addUploadRecordToDb(uri: String) {
//        getChild.child("imageUrl").setValue(uri)
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}