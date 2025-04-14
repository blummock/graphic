package com.blummock.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class GraphicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var points: List<PointF> = emptyList()
    private val path = Path()

    private var offsetX = 0f
    private var offsetY = 0f
    private var lastFocusX = 0f
    private var lastFocusY = 0f

    private var minX = 0f
    private var maxX = 1f
    private var minY = 0f
    private var maxY = 1f

    private var dataWidth = 1f
    private var dataHeight = 1f

    private var scaleFactor = 1f

    private val paddingLeft = 0f
    private val paddingBottom = 0f
    private val paddingTop = 0f
    private val paddingRight = 0f

    private val graphPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 8f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val axisPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 2f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.DKGRAY
        textSize = 28f
        isAntiAlias = true
    }

    private val scaleGestureDetector =
        ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 5f)
                invalidate()
                return true
            }
        })


    fun setPoints(points: List<PointF>) {
        this.points = points
        minX = points.minOfOrNull { it.x } ?: 0f
        maxX = points.maxOfOrNull { it.x } ?: 1f
        minY = points.minOfOrNull { it.y } ?: 0f
        maxY = points.maxOfOrNull { it.y } ?: 1f
        dataWidth = (maxX - minX).takeIf { it != 0f } ?: 1f
        dataHeight = (maxY - minY).takeIf { it != 0f } ?: 1f
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        if (event.pointerCount >= 2) {
            val focusX = (event.getX(0) + event.getX(1)) / 2f
            val focusY = (event.getY(0) + event.getY(1)) / 2f

            when (event.actionMasked) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    lastFocusX = focusX
                    lastFocusY = focusY
                }

                MotionEvent.ACTION_MOVE -> {
                    val dx = focusX - lastFocusX
                    val dy = focusY - lastFocusY

                    offsetX += dx
                    offsetY += dy

                    lastFocusX = focusX
                    lastFocusY = focusY
                    invalidate()
                }

                MotionEvent.ACTION_POINTER_UP -> {
                    lastFocusX = 0f
                    lastFocusY = 0f
                }
            }
        }

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        val scaleGraphX = contentWidth / dataWidth * scaleFactor
        val scaleGraphY = contentHeight / dataHeight * scaleFactor

        val zeroX = paddingLeft + (-minX * scaleGraphX)
        val zeroY = paddingTop + contentHeight - (-minY * scaleGraphY)

        offsetX = offsetX
            .coerceIn(-contentWidth / 2 * scaleFactor, contentWidth / 2 * scaleFactor)
        offsetY = offsetY
            .coerceIn(-contentHeight / 2 * scaleFactor, contentHeight / 2 * scaleFactor)

        fun alignX(value: Float) = paddingLeft + (value - minX) * scaleGraphX + offsetX

        fun alignY(value: Float) =
            paddingTop + contentHeight - (value - minY) * scaleGraphY + offsetY

        canvas.drawLine(
            paddingLeft,
            zeroY + offsetY,
            paddingLeft + contentWidth,
            zeroY + offsetY,
            axisPaint
        )

        canvas.drawLine(
            zeroX + offsetX,
            paddingTop,
            zeroX + offsetX,
            paddingTop + contentHeight,
            axisPaint
        )

        canvas.drawText("$maxX", alignX(maxX) - 60f, zeroY + 30f + offsetY, textPaint)
        canvas.drawText("$minX", alignX(minX), zeroY + 30f + offsetY, textPaint)

        canvas.drawText("$maxY", zeroX + offsetX, alignY(maxY) + 20f, textPaint)
        canvas.drawText("$minY", zeroX + offsetX, alignY(minY), textPaint)

        if (points.isNotEmpty()) {
            path.reset()
            path.moveTo(alignX(points[0].x), alignY(points[0].y))

            for (i in 1 until points.size) {
                path.lineTo(alignX(points[i].x), alignY(points[i].y))
            }
            canvas.drawPath(path, graphPaint)
        }
    }
}