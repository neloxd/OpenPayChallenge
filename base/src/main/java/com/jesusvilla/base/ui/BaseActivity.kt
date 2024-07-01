package com.jesusvilla.base.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import com.jesusvilla.base.extension.makeToastLong
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.core.network.data.AuthenticationListener
import com.jesusvilla.core.network.data.Session
import timber.log.Timber
import java.util.Arrays

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    var isAvailable: Boolean = false

    //region override region
    abstract fun setupNavigationListener()

    abstract fun getBaseViewModel(): BaseViewModel

    abstract fun getViewBinding(): VB

    protected fun setupObservers() {
    }

    abstract fun initNavController()

    abstract fun initConnectionNetwork()

    //temp
    abstract fun initViews()
    //endregion

    //region Sesion
    private fun initSessionListener() {
        Session.authenticationListener = object : AuthenticationListener {
            override fun onUserLoggedOut() {
                //viewModel.logOut()
                revokeSession()
            }
        }
    }

    private fun revokeSession() {
        runOnUiThread {
            try {
                makeToastLong("SESSION EXPIRADA VALIDAR VALORES EN CABECERA")
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        _binding = getViewBinding()
        setContentView(_binding!!.root)
        initConnectionNetwork()
        initViews()
        setupObservers()
        initNavController()
        setupNavigationListener()
        initSessionListener()
        permissionNotification()
    }

    private fun permissionNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101);
            }
            else {
                // repeat the permission or open app details
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}