package com.iram.thegalleryapp.presentation.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * GridDividerItemDecoration class for adding border to grid layout
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 5th Feb 2025
 */

class GridDividerItemDecoration(
    private val spanCount: Int,
    private val borderSize: Int,
    private val borderColor: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = borderSize.toFloat()
        color = borderColor
        isAntiAlias = true
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val column = position % spanCount
        val row = position / spanCount
        val totalRows = (parent.adapter?.itemCount ?: 0) / spanCount

        outRect.left = borderSize / 2
        outRect.right = borderSize / 2
        outRect.top = borderSize / 2
        outRect.bottom = borderSize / 2

        // Ensure last column has right spacing
        if (column == spanCount - 1) {
            outRect.right = borderSize
        }

        // Ensure last row has bottom spacing
        if (row == totalRows - 1) {
            outRect.bottom = borderSize
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val left = child.left.toFloat()
            val top = child.top.toFloat()
            val right = child.right.toFloat()
            val bottom = child.bottom.toFloat()

            // Draw border around each item
            c.drawRect(left, top, right, bottom, paint)
        }
    }
}
