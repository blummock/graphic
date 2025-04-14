package com.blummock.graphic.recycler

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

internal class GridDividerItemDecoration(
    private val dividerSize: Int,
    private val dividerColor: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = dividerColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)

            val params = view.layoutParams as RecyclerView.LayoutParams
            val left = view.left - params.leftMargin
            val right = view.right + params.rightMargin
            val top = view.top - params.topMargin
            val bottom = view.bottom + params.bottomMargin

            c.drawRect(
                left.toFloat(),
                bottom.toFloat(),
                right.toFloat(),
                (bottom + dividerSize).toFloat(),
                paint
            )

            c.drawRect(
                right.toFloat(),
                top.toFloat(),
                (right + dividerSize).toFloat(),
                bottom.toFloat(),
                paint
            )
        }
    }
}