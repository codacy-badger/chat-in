package com.viaann.chatin.fragment.SlideFirstLogin


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

import com.viaann.chatin.R
import com.viaann.chatin.activity.EditProfileActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_add_image.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddImageFragment : Fragment() {

    private var firebaseStorage = FirebaseStorage.getInstance()
    private var storageRefrence = firebaseStorage.reference
    private var filePath: Uri? = null
    private val auth = FirebaseAuth.getInstance()

    private val getChild = FirebaseDatabase.getInstance()
        .getReference("users")
        .child("profile")
        .child(auth.currentUser?.uid!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type ="image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"),
                EditProfileActivity.REQUEST_CODE_PICK_IMAGE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EditProfileActivity.REQUEST_CODE_PICK_IMAGE && data != null) {
            filePath = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
            addImage.setImageBitmap(bitmap)
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
                    addUploadRecordToDb(downloadUrl.toString())
                }
            }
        }
    }

    private fun addUploadRecordToDb(uri: String) {
        getChild.child("imageUrl").setValue(uri)
    }

}
