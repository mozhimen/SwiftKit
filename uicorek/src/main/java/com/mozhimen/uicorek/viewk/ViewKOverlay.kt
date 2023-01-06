package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.SparseArray
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.sp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.viewk.commons.ViewK
import java.util.*
import kotlin.math.min

/**
 * @ClassName ViewKOverlay
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/25 13:32
 * @Version 1.0
 */
class ViewKOverlay @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewK(context, attrs, defStyleAttr) {

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
        private const val BOX_TYPE_RECT = 0
    }

    private var _isShowLabel = true
    private var _labelTextSize = 16f.sp2px()
    private var _labelTextColor = Color.WHITE
    private var _labelBackgroundColor = UtilKRes.getColor(android.R.color.holo_blue_dark)
    private var _boxType = BOX_TYPE_RECT
    private var _boxLineWidth = 2f.dp2px()
    private var _boxLineColor = UtilKRes.getColor(android.R.color.holo_blue_dark)

    private var _scaleFactorWidth: Float = 1f
    private var _scaleFactorHeight: Float = 1f
    private var _results: LinkedList<Detection> = LinkedList<Detection>()
    private var _boxPaint = Paint()
    private var _textBackgroundPaint = Paint()
    private var _textPaint = Paint()
    private var _bounds = Rect()

    init {
        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKArcRotate)
        _isShowLabel = typedArray.getBoolean(R.styleable.ViewKOverlay_viewKOverlay_isShowLabel, _isShowLabel)
        _labelTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ViewKOverlay_viewKOverlay_labelTextSize, _labelTextSize)
        _labelTextColor = typedArray.getColor(R.styleable.ViewKOverlay_viewKOverlay_labelTextColor, _labelTextColor)
        _labelBackgroundColor =
            typedArray.getColor(R.styleable.ViewKOverlay_viewKOverlay_labelBackgroundColor, _labelBackgroundColor)
        _boxType = typedArray.getInteger(R.styleable.ViewKOverlay_viewKOverlay_boxType, _boxType)
        _boxLineWidth =
            typedArray.getDimensionPixelOffset(R.styleable.ViewKOverlay_viewKOverlay_boxLineWidth, _boxLineWidth)
        _boxLineColor = typedArray.getColor(R.styleable.ViewKOverlay_viewKOverlay_boxLineColor, _boxLineColor)
        typedArray.recycle()
    }

    fun setObjectRect(
        imageWidth: Int,
        imageHeight: Int,
        detectionResults: List<Detection>,
    ) {
        _results.clear()
        _results.addAll(detectionResults)

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        _scaleFactorWidth = width * 1f / imageWidth
        _scaleFactorHeight = height * 1f / imageHeight
        invalidate()
    }

    fun clearObjectRect() {
        _results.clear()
        invalidate()
    }

    override fun initPaint() {
        _boxPaint.color = _boxLineColor
        _boxPaint.strokeWidth = _boxLineWidth.toFloat()
        _boxPaint.style = Paint.Style.STROKE

        if (_isShowLabel) {
            _textBackgroundPaint.color = _labelBackgroundColor
            _textBackgroundPaint.style = Paint.Style.FILL

            _textPaint.color = _labelTextColor
            _textPaint.style = Paint.Style.FILL
            _textPaint.textSize = _labelTextSize.toFloat()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        if (_results.isNotEmpty()) {
            for (result in _results) {
                val boundingBox = result.boundingBox

                val top = boundingBox.top * _scaleFactorHeight
                val bottom = boundingBox.bottom * _scaleFactorHeight
                val left = boundingBox.left * _scaleFactorWidth
                val right = boundingBox.right * _scaleFactorWidth

                if (_boxType == BOX_TYPE_RECT) {
                    drawRect(canvas, left, top, right, bottom)
                } else {
                    drawCircle(canvas, left, top, right, bottom)
                }

                if (_isShowLabel) {
                    drawText(canvas, result, left, top)
                }
            }
        }
    }

    /**
     * draw text
     * @param canvas Canvas
     * @param result Detection
     * @param left Float
     * @param top Float
     */
    private fun drawText(canvas: Canvas, result: Detection, left: Float, top: Float) {
        // Create text to display alongside detected objects
        result.category ?: return
        val drawableText =
            result.category.label + " " + String.format("%.2f", result.category.score)

        // Draw rect behind display text
        _textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, _bounds)
        val textWidth = _bounds.width()
        val textHeight = _bounds.height()
        canvas.drawRect(
            left,
            top,
            left + textWidth + BOUNDING_RECT_TEXT_PADDING,
            top + textHeight + BOUNDING_RECT_TEXT_PADDING,
            _textBackgroundPaint
        )

        // Draw text for detected object
        canvas.drawText(drawableText, left, top + _bounds.height(), _textPaint)
    }

    /**
     * bounding box around detected objects
     * @param canvas Canvas
     * @param left Float
     * @param top Float
     * @param right Float
     * @param bottom Float
     */
    private fun drawRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        val drawableRect = RectF(left, top, right, bottom)
        canvas.drawRect(drawableRect, _boxPaint)
    }

    /**
     * bounding box around detected objects
     * @param canvas Canvas
     * @param left Float
     * @param top Float
     * @param right Float
     * @param bottom Float
     */
    private fun drawCircle(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        val cx: Float = (left + right) / 2f
        val cy: Float = (top + bottom) / 2f
        val radius: Float = min(right - left, bottom - top) / 2f
        canvas.drawCircle(cx, cy, radius, _boxPaint)
    }

    data class Detection(
        val boundingBox: RectF,
        val category: Category?
    ) {
        data class Category(
            val label: String,
            val score: Float
        )
    }
}