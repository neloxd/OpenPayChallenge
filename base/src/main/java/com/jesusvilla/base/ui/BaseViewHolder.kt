package com.jesusvilla.base.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jesús Villa on 22/04/24
 */
abstract class BaseViewHolder<T> protected constructor(view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun bind(data: T, position: Int)
}