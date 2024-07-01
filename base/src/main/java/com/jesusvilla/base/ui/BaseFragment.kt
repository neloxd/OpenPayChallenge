package com.jesusvilla.base.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.jesusvilla.base.extension.makeToastLong
import com.jesusvilla.base.lifecycle.Event
import com.jesusvilla.base.models.CustomModalModel
import com.jesusvilla.base.models.ProgressDialogModel
import com.jesusvilla.base.utils.UiText
import com.jesusvilla.base.utils.safeNavigate
import com.jesusvilla.base.viewModel.BaseViewModel

/**
 * Created by Jesús Villa on 10/03/24
 */
abstract class BaseFragment<T>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) :
    Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun getBaseViewModel(): BaseViewModel?

    private var progressDialog: ProgressDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return (binding as ViewBinding).root
    }

    protected fun navigate(action: Int, args: Bundle? = null) {
        findNavController().safeNavigate(action, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupListeners()
        setUpObservers()
    }

    open fun setupListeners() {}

    open fun setupUI() {
    }

    open fun setUpObservers() {
        getBaseViewModel()?.run {
            isLoading.observe(viewLifecycleOwner, ::showProgressDialog)
            showNotificationToast.observe(viewLifecycleOwner, ::showNotificationToast)
            onShowErrorModal().observe(viewLifecycleOwner, ::showErrorModal)
        }
    }

    private fun showErrorModal(customModalModel: CustomModalModel) {
        activity?.makeToastLong(customModalModel.body.toString())
    }

    protected fun showNotificationToast(message: Event<UiText>) {
        message.getContentIfNotHandled()?.let {
            activity?.makeToastLong(it.asString(requireContext()))
        }
    }

    protected fun showProgressDialog(isLoading: Boolean, message: String? = null) {
        if (progressDialog == null) {
            progressDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = message
                        ?: getString(com.jesusvilla.base.R.string.progress_dialog_generic_message)
                )
            )
        }

        if (isLoading) {
            if (!progressDialog!!.isAdded) progressDialog!!.show(
                childFragmentManager, ProgressDialogFragment.PROGRESS_DIALOG_TAG
            )
        } else {
            if (progressDialog!!.isAdded) progressDialog!!.dismissNow()
        }
    }

    private fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    protected fun setupBackCallback(backActionNavigation: () -> Unit) {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            backActionNavigation()
        }
    }

    protected fun isNetworkAvailable(): Boolean {
        if(activity is BaseActivity<*>) {
            return (activity as BaseActivity<*>).isAvailable
        }
        return false
    }
}