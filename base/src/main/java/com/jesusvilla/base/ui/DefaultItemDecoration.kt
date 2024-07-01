package com.jesusvilla.base.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.jesusvilla.base.R

fun Context.getDefaultItemDecorator(@Orientation orientation: Int = VERTICAL) = DividerItemDecoration(this, orientation)

fun Fragment.getInsetDividerItemDecoration() = InsetDividerItemDecoration(0, 0, com.jesusvilla.core.R.color.gray_basic)
