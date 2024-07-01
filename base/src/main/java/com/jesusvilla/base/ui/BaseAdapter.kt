package com.jesusvilla.base.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jesús Villa on 05/01/24
 */
abstract class BaseAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    items: List<T>,
    context: Context,
    callbacks: List<Callback<T>>
) :
    ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is DiffUtilEquality) {
                (oldItem as DiffUtilEquality).realEquals(newItem)
            } else oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is DiffUtilEquality) {
                (oldItem as DiffUtilEquality).realEquals(newItem)
            } else oldItem == newItem
        }
    }) {
    protected val items: ArrayList<T>
    protected val context: Context
    protected val callbacks: List<Callback<T>>

    fun interface Callback<T> {
        fun callback(t: T, position: Int)
    }

    interface DiffUtilEquality {
        fun realEquals(toCompare: Any?): Boolean
        fun viewType(): Int
    }

    //region Base Adapter
    init {
        this.items = items as ArrayList<T>
        this.context = context
        this.callbacks = callbacks
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder as BaseViewHolder<T>).bind(items[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is DiffUtilEquality)
            (items[position] as DiffUtilEquality).viewType()
        else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //region Behavior
    open fun getAll() = items

    open fun addAll(newItems: List<T>?) {
        items.clear()
        items.addAll(newItems!!)
    }

    open fun add(items: T) {
        this.items.add(items)
    }

    open fun delete(item: T) {
        items.remove(item)
    }

    //endregion
    //endregion
}