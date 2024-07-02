package com.jesusvilla.media.viewModel

import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.media.di.FirebaseStorageInstanceQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    @FirebaseStorageInstanceQualifier private val storage: FirebaseStorage
) : BaseViewModel() {

    fun uploadImage(imageBitmap: Bitmap, invokeUri:(uri: String) -> Unit) {
        _isLoading.postValue(true)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val storageRef = storage.reference.child("openpay_images").child(fileName)
        // Convert the Bitmap to a byte array
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Upload the image to Firebase Storage
        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnCompleteListener { task ->
            _isLoading.postValue(false)
            if (task.isSuccessful) {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    invokeUri(uri.toString())
                } } else {
                // Image upload failed
                val exception = task.exception
                // Handle the exception
                Timber.e(exception)
            }
        }
            .addOnFailureListener{
                _isLoading.postValue(false)
            }
    }
}