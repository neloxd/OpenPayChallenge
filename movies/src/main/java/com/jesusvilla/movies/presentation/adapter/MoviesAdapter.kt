package com.jesusvilla.movies.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jesusvilla.base.databinding.ItemKnownForBinding
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.base.ui.BaseAdapter
import com.jesusvilla.base.ui.BaseResponseViewHolder
import com.jesusvilla.base.ui.BaseViewHolder

class MoviesAdapter(
    context: Context,
    callbacks: List<Callback<BaseUI>> = listOf()
) : BaseAdapter<BaseUI, BaseViewHolder<*>>(
    mutableListOf(),
    context,
    callbacks
) {


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItesms: List<BaseUI>) {
        items.clear()
        items.addAll(newItesms)
        notifyDataSetChanged()
    }

    fun getItems(): List<BaseUI> {
        return items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return BaseResponseViewHolder(ItemKnownForBinding.inflate(LayoutInflater.from(context), parent, false))
    }
}