package com.mozhimen.uicoremk.viewmk

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.viewmk.commons.ViewMK
import kotlinx.coroutines.*

/**
 * @ClassName QRScanView
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:01
 * @Version 1.0
 */
@SuppressLint("UseCompatLoadingForDrawables")
class SquareQRScan @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewMK(context, attrs, defStyleAttr) {

    //region # variate
    private var borderWidth = 1f.dp2px()
    private var borderColor = 0xFFFFFF
    private var lineDrawable: Drawable? = null
    private var lineWidth = 2f.dp2px()
    private var lineColor = 0xFFFFFF
    private var animTime = 1000
    private var distance = 2f.dp2px()
    private var isLineReverse = false
    private var successDrawable = R.mipmap.viewmk_square_qr_scan_success
    private var successDrawableSize = 20f.dp2px()
    private var successAnimTime = 1000
    private var squareQrScanCallback: SquareQrScanCallback? = null
    private var logoBitmap: Bitmap
    private var lineBitmap: Bitmap? = null

    private lateinit var linePaint: Paint
    private lateinit var rectPaint: Paint
    private lateinit var frameRect: Rect

    private var moveStep = distance
    private val moveRate = 3f
    private var distanceSuccess = distance * moveRate
    private var animInterval: Float = animTime * distance / 20f.dp2px().toFloat()
    private var lineScaleInterval: Float = successAnimTime * distanceSuccess * 2f / 60f
    private var logoScaleInterval: Float =
        successAnimTime * distanceSuccess * 2f / successDrawableSize

    private var isSuccess = false
    private var lineTopOffset = 0
    private var lineScale = 0f
    private var logoScale = 0f
    //endregion

    interface SquareQrScanCallback {
        fun onAnimEnd()
    }

    fun setSquareQrScanCallback(squareQrScanCallback: SquareQrScanCallback) {
        this.squareQrScanCallback = squareQrScanCallback
    }

    fun requireSuccess() {
        isSuccess = true
        GlobalScope.launch(Dispatchers.Main) {
            delay(successAnimTime.toLong())
            squareQrScanCallback?.onAnimEnd()
        }
    }

    //region # private function
    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareQRScan)
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.SquareQRScan_squareQRScan_borderWidth, borderWidth)
        borderColor = typedArray.getColor(R.styleable.SquareQRScan_squareQRScan_borderColor, borderColor)
        lineDrawable = typedArray.getDrawable(R.styleable.SquareQRScan_squareQRScan_lineDrawable)
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.SquareQRScan_squareQRScan_lineWidth, lineWidth)
        lineColor = typedArray.getColor(R.styleable.SquareQRScan_squareQRScan_lineColor, lineColor)
        animTime = typedArray.getInteger(R.styleable.SquareQRScan_squareQRScan_animTime, animTime)
        distance = typedArray.getDimensionPixelSize(R.styleable.SquareQRScan_squareQRScan_distance, distance)
        isLineReverse = typedArray.getBoolean(R.styleable.SquareQRScan_squareQRScan_isLineReverse, isLineReverse)
        successDrawable = typedArray.getResourceId(R.styleable.SquareQRScan_squareQRScan_isLineReverse, successDrawable)
        successDrawableSize =
            typedArray.getDimensionPixelSize(R.styleable.SquareQRScan_squareQRScan_successDrawableSize, successDrawableSize)
        successAnimTime = typedArray.getInteger(R.styleable.SquareQRScan_squareQRScan_successAnimTime, successAnimTime)
        typedArray.recycle()

        lineDrawable?.let {
            lineBitmap = (it as BitmapDrawable).bitmap
        }
        logoBitmap = (ContextCompat.getDrawable(context, successDrawable) as BitmapDrawable).bitmap

        initPaint()
    }

    override fun initPaint() {
        super.initPaint()
        linePaint = Paint()
        linePaint.style = Paint.Style.STROKE
        linePaint.color = lineColor
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.strokeWidth = lineWidth.toFloat()

        rectPaint = Paint()
        rectPaint.style = Paint.Style.STROKE
        rectPaint.color = borderColor
        rectPaint.strokeWidth = borderWidth.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
    }

    override fun initData() {
        super.initData()
        moveStep = distance
        distanceSuccess = distance * moveRate
        animInterval = animTime * distance / height.toFloat()
        lineScaleInterval = successAnimTime * distanceSuccess / realRadius
        logoScaleInterval = successAnimTime * distanceSuccess * 2f / successDrawableSize

        frameRect = Rect(
            centerX.toInt() - realRadius.toInt() + borderWidth,
            centerY.toInt() - realRadius.toInt() + borderWidth,
            centerX.toInt() + realRadius.toInt() - borderWidth,
            centerY.toInt() + realRadius.toInt() - borderWidth
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawBorderLine(canvas)
            if (!isSuccess) {
                drawScanLine(canvas)
                moveScanLine()
            } else {
                scaleScanLine(canvas)
                scaleSuccessLogo(canvas)
                changeScaleStep()
            }
        }
    }

    private fun scaleScanLine(canvas: Canvas) {
        if (lineScale <= realRadius) {
            lineBitmap?.let {
                val lineRect = RectF(
                    frameRect.left.toFloat() + lineScale, lineTopOffset.toFloat(),
                    frameRect.right.toFloat() - lineScale, lineTopOffset.toFloat() + it.height
                )
                canvas.drawBitmap(it, null, lineRect, linePaint)
            } ?: kotlin.run {
                val borderRect = Rect(
                    frameRect.left + lineScale.toInt(), lineTopOffset,
                    frameRect.right - lineScale.toInt(), (lineTopOffset + lineWidth)
                )
                canvas.drawRect(borderRect, linePaint)
            }
        }
    }

    private fun scaleSuccessLogo(canvas: Canvas) {
        if (lineScale >= realRadius) {
            val logoRect = RectF(
                width.toFloat() / 2 - logoScale, height.toFloat() / 2 - logoScale,
                width.toFloat() / 2 + logoScale, height.toFloat() / 2 + logoScale
            )
            canvas.drawBitmap(logoBitmap, null, logoRect, linePaint)
        }
    }

    private fun changeScaleStep() {
        if (lineScale >= realRadius && logoScale <= successDrawableSize / 2) {
            logoScale += distanceSuccess
            postInvalidateDelayed(
                logoScaleInterval.toLong(),
                frameRect.left,
                frameRect.top,
                frameRect.right,
                frameRect.bottom
            )
        } else if (lineScale <= realRadius) {
            lineScale += distanceSuccess
            postInvalidateDelayed(
                lineScaleInterval.toLong(),
                frameRect.left,
                frameRect.top,
                frameRect.right,
                frameRect.bottom
            )
        }
    }

    //画边框线
    private fun drawBorderLine(canvas: Canvas) {
        canvas.drawRect(frameRect, rectPaint)
    }

    //画扫描线
    private fun drawScanLine(canvas: Canvas) {
        lineBitmap?.let {
            val lineRect = RectF(
                frameRect.left.toFloat(), lineTopOffset.toFloat(),
                frameRect.right.toFloat(), lineTopOffset.toFloat() + it.height.toFloat()
            )
            canvas.drawBitmap(it, null, lineRect, linePaint)
        } ?: kotlin.run {
            val borderRect = Rect(
                frameRect.left, lineTopOffset,
                frameRect.right, lineTopOffset + lineWidth
            )
            canvas.drawRect(borderRect, linePaint)
        }
    }

    //移动扫描线的位置
    private fun moveScanLine() {
        // 处理非网格扫描图片的情况
        lineTopOffset += moveStep
        var scanLineSize = 0
        lineBitmap?.let {
            scanLineSize = it.height
        } ?: kotlin.run {
            scanLineSize = lineWidth
        }
        if (isLineReverse) {
            if (lineTopOffset + scanLineSize >= frameRect.bottom || lineTopOffset < frameRect.top) {
                moveStep = -moveStep
            }
        } else {
            if (lineTopOffset + scanLineSize >= frameRect.bottom) {
                lineTopOffset = frameRect.top
            }
        }
        postInvalidateDelayed(
            animInterval.toLong(),
            0,
            0,
            width,
            height
        )
    }
    //#endregion
}