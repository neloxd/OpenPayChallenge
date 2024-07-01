package com.jesusvilla.base.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jesusvilla.base.databinding.FragmentProgressDialogBinding
import com.jesusvilla.base.models.ProgressDialogModel

class ProgressDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "ProgressDialogFragment"
        const val IS_CANCELLABLE_PARAM = "isCancellable"
        const val MESSAGE_PARAM = "message"
        const val PROGRESS_DIALOG_TAG = "progressDialogTag"
        fun newInstance(progressDialogModel: ProgressDialogModel): ProgressDialogFragment {
            val fragment = ProgressDialogFragment()
            val args = Bundle()
            args.putBoolean(IS_CANCELLABLE_PARAM, progressDialogModel.dialogOptions.isCancellable)
            args.putString(MESSAGE_PARAM, progressDialogModel.message)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentProgressDialogBinding? = null
    private val binding: FragmentProgressDialogBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvMessage.text = arguments?.getString(MESSAGE_PARAM).orEmpty()
        }
        isCancelable = arguments?.getBoolean(IS_CANCELLABLE_PARAM) ?: false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
