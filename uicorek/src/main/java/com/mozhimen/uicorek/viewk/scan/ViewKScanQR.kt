package com.mozhimen.uicorek.viewk.scan

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.basick.utilk.android.graphics.asBitmap
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.uicorek.viewk.bases.BaseViewK
import com.mozhimen.uicorek.R

/**
 * @ClassName QRScanView
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:18
 * @Version 1.0
 */
class ViewKScanQR @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : BaseViewK(context, attrs, defStyleAttr, defStyleRes) {

    //region # variate
    private var _lineDrawable: Drawable? = null
    private var _lineWidth = 1f.dp2px().toInt()
    private var _borderLineWidth = 1f.dp2px().toInt()
    private var _borderColor = Color.WHITE
    private var _animTime = 1000
    private var _isScanLineReverse = false

    private var _paint: Paint = Paint()
    private var _scanLineBitmap: Bitmap? = null
    private var _scanLineTop = _borderLineWidth
    private var _moveStepDistance = 2f.dp2px().toInt()
    private var _animDelayTime = 0
    private var _borderRect: Rect? = null
    private var _borderWidth = 200f.dp2px().toInt()
    private var _borderHeight = 200f.dp2px().toInt()
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        afterInitCustomAttrs()
    }

    fun getRectSize(): Int =
        _borderWidth

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewKScanQR)
        _lineDrawable = typedArray.getDrawable(R.styleable.ViewKScanQR_viewKScanQR_lineDrawable)
        _lineWidth = typedArray.getDimensionPixelSize(R.styleable.ViewKScanQR_viewKScanQR_lineWidth, _lineWidth)
        _borderLineWidth =
            typedArray.getDimensionPixelSize(R.styleable.ViewKScanQR_viewKScanQR_borderLineWidth, _borderLineWidth)
        _borderColor = typedArray.getColor(R.styleable.ViewKScanQR_viewKScanQR_borderColor, _borderColor)
        _animTime = typedArray.getInteger(R.styleable.ViewKScanQR_viewKScanQR_animTime, _animTime)
        _isScanLineReverse =
            typedArray.getBoolean(R.styleable.ViewKScanQR_viewKScanQR_isScanLineReverse, _isScanLineReverse)
        typedArray.recycle()
    }

    private fun afterInitCustomAttrs() {
        _lineDrawable?.let {
            _scanLineBitmap = if (_lineDrawable is BitmapDrawable) {
                (_lineDrawable as BitmapDrawable).bitmap
            } else {
                it.asBitmap(_borderWidth, _lineWidth)
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (_borderRect == null) return

        // 画边框线
        drawBorderLine(canvas)

        // 画扫描线
        drawScanLine(canvas)

        // 移动扫描线的位置
        moveScanLine()
    }

    /**
     * 画边框线
     */
    private fun drawBorderLine(canvas: Canvas) {
        if (_borderLineWidth > 0) {
            _paint.style = Paint.Style.STROKE
            _paint.color = _borderColor
            _paint.strokeWidth = _borderLineWidth.toFloat()
            canvas.drawRect(_borderRect!!, _paint)
        }
    }

    /**
     * 画扫描线
     */
    private fun drawScanLine(canvas: Canvas) {
        _scanLineBitmap?.let {
            val lineRect = RectF(
                _borderRect!!.left.toFloat(), _scanLineTop.toFloat(),
                _borderRect!!.right.toFloat(), _scanLineTop.toFloat() + it.height
            )
            canvas.drawBitmap(it, null, lineRect, _paint)
        }
    }

    /**
     * 移动扫描线的位置
     */
    private fun moveScanLine() {
        // 处理非网格扫描图片的情况
        _scanLineTop += _moveStepDistance
        var scanLineWidth = 0
        _scanLineBitmap?.let {
            scanLineWidth = it.height
        }
        if (_isScanLineReverse) {
            if (_scanLineTop + scanLineWidth >= _borderRect!!.bottom || _scanLineTop < _borderRect!!.top) {
                _moveStepDistance = -_moveStepDistance
            }
        } else {
            if (_scanLineTop + scanLineWidth >= _borderRect!!.bottom) {
                _scanLineTop = _borderRect!!.top + scanLineWidth
            }
        }
        postInvalidateDelayed(
            _animDelayTime.toLong(),
            _borderRect!!.left,
            _borderRect!!.top,
            _borderRect!!.right,
            _borderRect!!.bottom
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _borderWidth = h
        _borderHeight = h
        initData()
    }

    override fun initData() {
        _animDelayTime = ((1f * _animTime * _moveStepDistance) / _borderHeight).toInt()
        initBorderRect()
    }

    private fun initBorderRect() {
        val leftOffset = _borderLineWidth / 2
        _borderRect = Rect(leftOffset, leftOffset, _borderWidth - leftOffset, _borderHeight - leftOffset)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val mWidthMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(mWidthMeasureSpec, heightMeasureSpec)
    }
}