package com.mozhimen.uicorek.viewk.scan

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.android.view.dp2px
import com.mozhimen.basick.utilk.android.view.sp2px
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.viewk.bases.BaseViewK
import java.util.*
import kotlin.math.min

/**
 * @ClassName ViewKScanOverlay
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/25 13:32
 * @Version 1.0
 */
class ViewKScanOverlay @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseViewK(context, attrs, defStyleAttr) {

    private var _isShowLabel = true
    private var _labelTextSize = 16f.sp2px().toInt()
    private var _labelTextColor = Color.WHITE
    private var _labelBackgroundColor = UtilKRes.getColor(android.R.color.holo_blue_dark)
    private var _boxType = 0
    private var _boxLineWidth = 2f.dp2px().toInt()
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
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKScanOverlay)
        _isShowLabel = typedArray.getBoolean(R.styleable.ViewKScanOverlay_viewKScanOverlay_isShowLabel, _isShowLabel)
        _labelTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ViewKScanOverlay_viewKScanOverlay_labelTextSize, _labelTextSize)
        _labelTextColor = typedArray.getColor(R.styleable.ViewKScanOverlay_viewKScanOverlay_labelTextColor, _labelTextColor)
        _labelBackgroundColor =
            typedArray.getColor(R.styleable.ViewKScanOverlay_viewKScanOverlay_labelBackgroundColor, _labelBackgroundColor)
        _boxType = typedArray.getInteger(R.styleable.ViewKScanOverlay_viewKScanOverlay_boxType, _boxType)
        _boxLineWidth =
            typedArray.getDimensionPixelOffset(R.styleable.ViewKScanOverlay_viewKScanOverlay_boxLineWidth, _boxLineWidth)
        _boxLineColor = typedArray.getColor(R.styleable.ViewKScanOverlay_viewKScanOverlay_boxLineColor, _boxLineColor)
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

                if (_boxType == 0) {
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
            left + textWidth + 8,
            top + textHeight + 8,
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