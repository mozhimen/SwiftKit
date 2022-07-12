package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.uicorek.R
import kotlin.math.asin

/**
 * @ClassName CircleRotate
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 17:51
 * @Version 1.0
 */
class ArcRotate @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKView(context, attrs, defStyleAttr) {

    //region # variate
    private var _arcStartColor = Color.WHITE
    private var _arcEndColor = Color.TRANSPARENT
    private var _animTime = 1000
    private var _angleStep = 3
    private var _arcNum = 1
    private var _arcWidth = 2f.dp2px()

    @Volatile
    private var _isStop = false
    private var _interval = _animTime * _angleStep / 360
    private var _angleOffset = asin(_arcWidth / 2f / realRadius)
    private var _angleInterval = 30
    private var _angleSweep = (360f - _arcNum * _angleInterval) / _arcNum

    private lateinit var _arcPaint: Paint
    private var _arcMatrix = Matrix()
    private lateinit var _arcRectF: RectF

    //endregion

    //region # private function
    init {
        initAttrs(attrs, defStyleAttr)
    }

    fun setArcColor(startColor: Int, endColor: Int) {
        _arcStartColor = startColor
        _arcEndColor = endColor
    }

    override fun requireStart() {
        _isStop = false
        invalidate()
    }

    override fun requireStop() {
        _isStop = true
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcRotate)
        _arcStartColor = typedArray.getColor(R.styleable.ArcRotate_arcRotate_arcStartColor, _arcStartColor)
        _arcEndColor = typedArray.getColor(R.styleable.ArcRotate_arcRotate_arcEndColor, _arcEndColor)
        _arcWidth = typedArray.getDimensionPixelSize(R.styleable.ArcRotate_arcRotate_arcWidth, _arcWidth)
        _angleStep = typedArray.getInteger(R.styleable.ArcRotate_arcRotate_angleStep, _angleStep)
        _arcNum = typedArray.getInteger(R.styleable.ArcRotate_arcRotate_arcNum, _arcNum)
        _animTime = typedArray.getInteger(R.styleable.ArcRotate_arcRotate_animTime, _animTime)
        _angleInterval = typedArray.getInteger(R.styleable.ArcRotate_arcRotate_angleInterval, _angleInterval)
        typedArray.recycle()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
        initPaint()
    }

    override fun initData() {
        _interval = _animTime * _angleStep / 360
        _angleOffset =
            Math.toDegrees(asin(_arcWidth / 2f / (realRadius - _arcWidth / 2f)).toDouble()).toFloat()
        _angleSweep = (360f - _arcNum * _angleInterval - _angleOffset) / _arcNum
        require(_angleSweep > 0) { "圆弧数 * 圆弧间隔 + 偏移量超过了360°" }
    }

    override fun initPaint() {
        _arcPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        _arcPaint.style = Paint.Style.STROKE
        _arcPaint.strokeCap = Paint.Cap.ROUND
        _arcPaint.isAntiAlias = true
        _arcPaint.strokeWidth = _arcWidth.toFloat()
        _arcRectF = RectF(
            centerX - realRadius + _arcWidth / 2, centerY - realRadius + _arcWidth / 2,
            centerX + realRadius - _arcWidth / 2, centerY + realRadius - _arcWidth / 2
        )
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawArcLine(canvas)
            rotateRing()
        }
    }

    private fun rotateRing() {
        _arcMatrix.postRotate(_angleStep.toFloat(), centerX, centerY)
        if (!_isStop) {
            postInvalidateDelayed(_interval.toLong())
        }
    }

    private fun drawArcLine(canvas: Canvas) {
        canvas.concat(_arcMatrix)
        for (i in 0 until _arcNum) {
            val sweepGradient = SweepGradient(
                realRadius, realRadius,
                intArrayOf(_arcStartColor, _arcEndColor),
                floatArrayOf(
                    (_angleOffset + i * (_angleInterval + _angleSweep)) / 360f,
                    (_angleOffset + i * (_angleInterval + _angleSweep) + _angleSweep) / 360f
                )
            )
            _arcPaint.shader = sweepGradient
            canvas.drawArc(_arcRectF, _angleOffset + i * (_angleInterval + _angleSweep), _angleSweep, false, _arcPaint)
        }
    }
    //endregion
}