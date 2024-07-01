package com.jesusvilla.base.ui

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jesusvilla.base.databinding.ItemKnownForBinding
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.core.BuildConfig

class BaseResponseViewHolder(private val binding: ItemKnownForBinding) :
    BaseViewHolder<BaseUI>(binding.root) {
    override fun bind(data: BaseUI, position: Int) {
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