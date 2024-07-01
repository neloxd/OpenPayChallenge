package com.jesusvilla.profile.presentation.holder

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jesusvilla.base.databinding.ItemKnownForBinding
import com.jesusvilla.base.ui.BaseViewHolder
import com.jesusvilla.core.BuildConfig
import com.jesusvilla.profile.domain.model.KnownForUI

class KnownForViewHolder(private val binding: ItemKnownForBinding) :
    BaseViewHolder<KnownForUI>(binding.root) {
    override fun bind(data: KnownForUI, position: Int) {
        with(binding) {
            tvName.text = data.name
            tvOriginalName.text = data.originalName
            tvOverview.text = data.overview
            if(data.image.isNotEmpty()) {
                Glide.with(root.context)
                    .load(BuildConfig.BASE_URL_IMAGE + data.image.substring(1))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivOverview)
            }
        }
    }
}