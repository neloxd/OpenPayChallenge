package com.jesusvilla.profile.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jesusvilla.base.databinding.ItemKnownForBinding
import com.jesusvilla.base.ui.BaseAdapter
import com.jesusvilla.base.ui.BaseViewHolder
import com.jesusvilla.profile.domain.model.KnownForUI
import com.jesusvilla.profile.presentation.holder.KnownForViewHolder

class KnownForAdapter(
    context: Context,
    callbacks: List<Callback<KnownForUI>> = listOf()
) : BaseAdapter<KnownForUI, BaseViewHolder<*>>(
    mutableListOf(),
    context,
    callbacks
) {


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItesms: List<KnownForUI>) {
        items.clear()
        items.addAll(newItesms)
        notifyDataSetChanged()
    }

    fun getItems(): List<KnownForUI> {
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return KnownForViewHolder(ItemKnownForBinding.inflate(LayoutInflater.from(context), parent, false))
    }
}