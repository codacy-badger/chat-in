package com.viaann.chatin.activity

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.viaann.chatin.R
import com.viaann.chatin.activity.ChangeActivity.ChangeIdActivity
import com.viaann.chatin.activity.ChangeActivity.ChangeNameActivity
import com.viaann.chatin.activity.ChangeActivity.ChangeStatusActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.*
import kotlin.collections.HashMap

class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 1
    }

    private var firebaseStorage = FirebaseStorage.getInstance()
    private var storageRefrence = firebaseStorage.reference
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvEditDisplayName.setOnClickListener {
            val intent = Intent(this, ChangeNameActivity::class.java)
            // will pass the saved display name
            startActivity(intent)
        }

        tvEditStatus.setOnClickListener {
            val intent = Intent(this, ChangeStatusActivity::class.java)
            // will pass the saved status
            startActivity(intent)
        }

        tvChangeId.setOnClickListener {
            val intent = Intent(this, ChangeIdActivity::class.java)
            // will pass the saved id account
            startActivity(intent)
        }

        changeImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type ="image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"),
                REQUEST_CODE_PICK_IMAGE)
        }

        btnSaveChange.setOnClickListener {
            uploadImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null) {
            filePath = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            changeImg.setImageBitmap(bitmap)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageRefrence.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref.putFile(filePath!!)

            val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                if (!it.isSuccessful) {
                    it.exception?.let {
                        throw  it
                    }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener {
                if (it.isSuccessful) {
                    val downloadUrl = it.result
                    addUploadRecordToDb(downloadUrl.toString())
                }
            }
        }
    }

    private fun addUploadRecordToDb(uri: String) {
        val db = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved to DB", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail Saved to DB", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUsername() {

    }

    private fun updateStatus() {

    }

    private fun updateIdAccount() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
