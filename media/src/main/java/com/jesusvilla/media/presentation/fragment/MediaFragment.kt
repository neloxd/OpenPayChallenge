package com.jesusvilla.media.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.bumptech.glide.Glide
import com.jesusvilla.base.constants.Constants.CAMERA_PERMISSION
import com.jesusvilla.base.lifecycle.Event
import com.jesusvilla.base.ui.BaseFragment
import com.jesusvilla.base.utils.UiText
import com.jesusvilla.base.utils.requestMultiplePermissionsLauncher
import com.jesusvilla.core.network.permission.allOf
import com.jesusvilla.media.R
import com.jesusvilla.media.databinding.FragmentMediaBinding
import com.jesusvilla.media.viewModel.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MediaFragment: BaseFragment<FragmentMediaBinding>(FragmentMediaBinding::inflate) {

    private val viewModel: MediaViewModel by hiltNavGraphViewModels(R.id.media_graph)
    override fun getBaseViewModel() = viewModel

    private val mediaPermissionLauncher = requestMultiplePermissionsLauncher(
        allOf(CAMERA_PERMISSION)
    )

    private val cameraActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val bitmap = it.data!!.extras?.get("data") as Bitmap
            viewModel.uploadImage(bitmap) {
                setMessage("Photo by Camera Uploaded!", bitmap)
            }
        }
    }

    private val galleryActivityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK && it.data != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it.data!!.data)
            viewModel.uploadImage(bitmap) {
                setMessage("Image by Gallery Uploaded!", bitmap)
            }
        }
    }

    private var hasPermissions = false

    override fun setupUI() {
        super.setupUI()
        setupPermission()
    }

    private fun setupPermission() {
        mediaPermissionLauncher.launch(
            onSuccess = {
                hasPermissions = true
            },
            onCancel = { permissions, rationalePermissionLauncher -> showNotificationToast(
                Event(
                    UiText.DynamicString("Permiso cancelado"))
            )},
            onDenied = { permissions, neverAskAgain -> showNotificationToast(Event(UiText.DynamicString("Permiso denegado")))}
        )
    }

    override fun setupListeners() {
        super.setupListeners()
        with(binding) {
            btnGallery.setOnClickListener {
                if(hasPermissions) {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    galleryActivityResultLauncher.launch(galleryIntent)
                } else {
                    setupPermission()
                }
            }
            btnPic.setOnClickListener {
                if(hasPermissions) {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraActivityResultLauncher.launch(takePictureIntent)
                } else {
                    setupPermission()
                }
            }
        }
    }

    private fun setMessage(message: String, bitmap: Bitmap) {
        with(requireActivity()) {
            runOnUiThread {
                Toast.makeText(this, message, Toast.LENGTH_LONG)
                    .show()
            }
            Glide.with(this).load(bitmap).into(binding.image)
        }
    }

}