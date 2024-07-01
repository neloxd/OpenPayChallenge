package com.jesusvilla.base.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


/**
 * Draws left or right inset dividers at the bottom of every recycler item view. Only supports
 * vertical orientations.
 */
class InsetDividerItemDecoration : RecyclerView.ItemDecoration {
    var leftInset: Int = 0
    var rightInset: Int = 0
    private var mStartPosition = 0

    private val mLinePaint = Paint()
    private val mPositionsToIgnore: MutableList<Int> = ArrayList()

    @JvmOverloads
    constructor(leftInset: Int, rightInset: Int, dividerColor: Int = 0) {
        this.leftInset = leftInset
        this.rightInset = rightInset
        color = dividerColor
    }

    constructor(leftInset: Int, rightInset: Int, dividerColor: Int, startPosition: Int) {
        this.leftInset = leftInset
        this.rightInset = rightInset
        mStartPosition = startPosition
        color = dividerColor
    }

    constructor(
        leftInset: Int,
        rightInset: Int,
        dividerColor: Int,
        startPosition: Int,
        dividerHeight: Int
    ) {
        this.leftInset = leftInset
        this.rightInset = rightInset
        mStartPosition = startPosition
        color = dividerColor
        this.dividerHeight = dividerHeight
    }

    var color: Int
        get() = mLinePaint.color
        set(color) {
            mLinePaint.color = color
        }

    var dividerHeight: Int
        get() = mLinePaint.strokeWidth.toInt()
        set(height) {
            mLinePaint.strokeWidth = height.toFloat()
        }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val visibleItems = parent.layoutManager!!.childCount

        for (i in 0 until visibleItems) {
            val itemView = parent.layoutManager!!.getChildAt(i)
            val adapterPosition = parent.getChildAdapterPosition(itemView!!)

            val shouldDraw =
                adapterPosition != RecyclerView.NO_POSITION && adapterPosition != parent.adapter!!.itemCount - 1 && adapterPosition >= mStartPosition && !shouldIgnoreAdapterPosition(
                    adapterPosition
                )

            if (shouldDraw) {
                val itemRect = Rect()
                itemView.getDrawingRect(itemRect)

                parent.offsetDescendantRectToMyCoords(itemView, itemRect)

                val lineStartX = leftInset
                val lineStartY = itemRect.bottom

                val lineEndX = itemRect.right - rightInset
                val lineEndY = lineStartY

                c.drawLine(
                    lineStartX.toFloat(),
                    lineStartY.toFloat(),
                    lineEndX.toFloat(),
                    lineEndY.toFloat(),
                    mLinePaint
                )
            }
        }
    }

    protected fun shouldIgnoreAdapterPosition(position: Int): Boolean {
        return mPositionsToIgnore.size > 0 && mPositionsToIgnore.contains(position)
    }
}