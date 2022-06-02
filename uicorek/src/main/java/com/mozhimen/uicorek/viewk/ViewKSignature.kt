package com.mozhimen.uicorek.viewk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.basick.logk.LogK
import com.mozhimen.uicorek.R

/**
 * @ClassName ViewKSignature
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/10 22:34
 * @Version 1.0
 */
class ViewKSignature @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKView(context, attrs, defStyleAttr) {

    private var lineWidth = 2f.dp2px()

    private val paint = Paint()
    private val path = Path()

    /**
     * Optimizes painting by invalidating the smallest possible area.
     */
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private val dirtyRect = RectF()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKSignature)
        lineWidth =
            typedArray.getDimensionPixelSize(R.styleable.ViewKSignature_viewKSignature_lineWidth, lineWidth)
        typedArray.recycle()
        initPaint()
    }

    /**
     * Erases the signature.
     */
    fun clear() {
        path.reset()

        // Repaints the entire view.
        invalidate()
    }

    fun isSign() = lastTouchX != 0f || lastTouchY != 0f

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //利用bitmap生成画布
        val paint = Paint()
        paint.color = Color.WHITE
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat(), paint)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        //把view中的内容绘制在画布上
        this.draw(canvas)
        return bitmap
    }

    override fun initPaint() {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = lineWidth.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(eventX, eventY)
                lastTouchX = eventX
                lastTouchY = eventY
                // There is no end point yet, so don't waste cycles invalidating.
                return true
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                // Start tracking the dirty region.
                resetDirtyRect(eventX, eventY)

                // When the hardware tracks events faster than they are delivered, the
                // event will contain a history of those skipped points.
                val historySize = event.historySize
                var i = 0
                while (i < historySize) {
                    val historicalX = event.getHistoricalX(i)
                    val historicalY = event.getHistoricalY(i)
                    expandDirtyRect(historicalX, historicalY)
                    path.lineTo(historicalX, historicalY)
                    i++
                }

                // After replaying history, connect the line to the touch point.
                path.lineTo(eventX, eventY)
            }
            else -> {
                LogK.et(TAG, "onTouchEvent Ignored touch event: $event")
                return false
            }
        }

        // Include half the stroke width to avoid clipping.
        val halfLineWidth = lineWidth / 2f
        postInvalidate(
            (dirtyRect.left - halfLineWidth).toInt(),
            (dirtyRect.top - halfLineWidth).toInt(),
            (dirtyRect.right + halfLineWidth).toInt(),
            (dirtyRect.bottom + halfLineWidth).toInt()
        )
        lastTouchX = eventX
        lastTouchY = eventY
        return true
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private fun resetDirtyRect(eventX: Float, eventY: Float) {

        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = lastTouchX.coerceAtMost(eventX)
        dirtyRect.right = lastTouchX.coerceAtLeast(eventX)
        dirtyRect.top = lastTouchY.coerceAtMost(eventY)
        dirtyRect.bottom = lastTouchY.coerceAtLeast(eventY)
    }
}