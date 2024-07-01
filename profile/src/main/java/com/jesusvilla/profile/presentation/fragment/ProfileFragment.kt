package com.jesusvilla.profile.presentation.fragment

import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jesusvilla.base.ui.BaseFragment
import com.jesusvilla.base.ui.getInsetDividerItemDecoration
import com.jesusvilla.core.BuildConfig
import com.jesusvilla.profile.R
import com.jesusvilla.profile.databinding.FragmentProfileBinding
import com.jesusvilla.profile.domain.model.KnownForUI
import com.jesusvilla.profile.domain.model.ProfileUI
import com.jesusvilla.profile.presentation.adapter.KnownForAdapter
import com.jesusvilla.profile.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by hiltNavGraphViewModels(R.id.profile_graph)
    override fun getBaseViewModel() = viewModel

    private val adapter by lazy {
        setupAdapter()
    }

    override fun setupUI() {
        super.setupUI()
        with(binding) {
            list.addItemDecoration(getInsetDividerItemDecoration())
            list.adapter = adapter
        }
        if(isNetworkAvailable()) {
            viewModel.getProfile()
        } else {
            viewModel.getLocalProfile()
        }
    }

    override fun setUpObservers() {
        super.setUpObservers()
        viewModel.getProfileUI().observe(viewLifecycleOwner) { setupProfile(it) }
    }

    private fun setupAdapter() = KnownForAdapter(requireContext(), listOf())

    private fun setupProfile(profileUI: ProfileUI) {
        with(binding) {
            itemDetailSummary.text = profileUI.name
            if(profileUI.path.isNotEmpty()) {
                Glide.with(root.context)
                    .load(BuildConfig.BASE_URL_IMAGE + profileUI.path.substring(1))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .optionalCircleCrop()
                    .into(itemDetailImage)
            }
            setItems(profileUI.list)
        }
    }

    private fun setItems(list: List<KnownForUI>) {
        adapter.updateData(list)
    }
}