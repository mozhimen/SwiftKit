package com.mozhimen.uicorek.viewk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.view.BaseKView

import com.mozhimen.uicorek.R

/**
 * @ClassName ViewKSignature
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/10 22:34
 * @Version 1.0
 */
class ViewKSignature @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    BaseKView(context, attrs, defStyleAttr) {

    //region # variate
    private var _lineWidth = 2f.dp2px()

    private val _paint = Paint()
    private val _path = Path()

    //Optimizes painting by invalidating the smallest possible area.
    private var _lastTouchX = 0f
    private var _lastTouchY = 0f
    private val _dirtyRect = RectF()
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    /**
     * Erases the signature.
     */
    fun clear() {
        _path.reset()

        // Repaints the entire view.
        postInvalidate()
    }

    /**
     * 是否签字
     * @return Boolean
     */
    fun isSign() = _lastTouchX != 0f || _lastTouchY != 0f

    /**
     * 获取签字图像
     * @return Bitmap
     */
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

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKSignature)
        _lineWidth =
            typedArray.getDimensionPixelSize(R.styleable.ViewKSignature_viewKSignature_lineWidth, _lineWidth)
        typedArray.recycle()
    }

    override fun initPaint() {
        _paint.isAntiAlias = true
        _paint.color = Color.BLACK
        _paint.style = Paint.Style.STROKE
        _paint.strokeJoin = Paint.Join.ROUND
        _paint.strokeWidth = _lineWidth.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(_path, _paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                _path.moveTo(eventX, eventY)
                _lastTouchX = eventX
                _lastTouchY = eventY
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
                    _path.lineTo(historicalX, historicalY)
                    i++
                }

                // After replaying history, connect the line to the touch point.
                _path.lineTo(eventX, eventY)
            }
            else -> {
                Log.e(TAG, "onTouchEvent Ignored touch event: $event")
                return false
            }
        }

        // Include half the stroke width to avoid clipping.
        val halfLineWidth = _lineWidth / 2f
        postInvalidate(
            (_dirtyRect.left - halfLineWidth).toInt(),
            (_dirtyRect.top - halfLineWidth).toInt(),
            (_dirtyRect.right + halfLineWidth).toInt(),
            (_dirtyRect.bottom + halfLineWidth).toInt()
        )
        _lastTouchX = eventX
        _lastTouchY = eventY
        return true
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
        if (historicalX < _dirtyRect.left) {
            _dirtyRect.left = historicalX
        } else if (historicalX > _dirtyRect.right) {
            _dirtyRect.right = historicalX
        }
        if (historicalY < _dirtyRect.top) {
            _dirtyRect.top = historicalY
        } else if (historicalY > _dirtyRect.bottom) {
            _dirtyRect.bottom = historicalY
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private fun resetDirtyRect(eventX: Float, eventY: Float) {

        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        _dirtyRect.left = _lastTouchX.coerceAtMost(eventX)
        _dirtyRect.right = _lastTouchX.coerceAtLeast(eventX)
        _dirtyRect.top = _lastTouchY.coerceAtMost(eventY)
        _dirtyRect.bottom = _lastTouchY.coerceAtLeast(eventY)
    }
}