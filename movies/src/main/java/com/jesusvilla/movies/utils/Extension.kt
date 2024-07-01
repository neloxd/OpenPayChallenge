package com.jesusvilla.movies.utils

import com.jesusvilla.base.models.BaseUI

fun List<BaseUI>.toFilterList(selectOption: Int = 1): List<BaseUI> {
    return when(selectOption) {
        2 -> {
            sortedBy {
                it.voteAverage
            }.take(10)
        }
        3 -> {
            sortedBy {
                it.voteCount
            }.take(10)
        }
        else -> this
    }
}