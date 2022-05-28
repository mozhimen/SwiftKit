package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.uicorek.R

/**
 * @ClassName Radar
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 20:11
 * @Version 1.0
 */
class RadarRipple @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKView(context, attrs, defStyleAttr) {

    //region # variate
    private var bgColor = 0xFFFFFF
    private var radarColor = 0x287FF1
    private var lineColor = 0xFFFFFF
    private var lineAlpha = 100//0-255
    private var lineWidth = 2f.dp2px()
    private var lineCount = 4
    private var moveAngleStep = 3
    private var animTime = 3000

    private lateinit var radarPaint: Paint
    private lateinit var bgPaint: Paint
    private lateinit var circlePaint: Paint

    private var interval = animTime * moveAngleStep / 360
    private val radarMatrix = Matrix()
    private var isStop = false
    //endregion

    override fun requireStart() {
        isStop = false
        invalidate()
    }

    override fun requireStop() {
        isStop = true
    }

    //region # private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarRipple)
        bgColor = typedArray.getColor(R.styleable.RadarRipple_radarRipple_bgColor, bgColor)
        radarColor = typedArray.getColor(R.styleable.RadarRipple_radarRipple_radarColor, radarColor)
        lineColor = typedArray.getColor(R.styleable.RadarRipple_radarRipple_lineColor, lineColor)
        lineAlpha = typedArray.getInteger(R.styleable.RadarRipple_radarRipple_lineAlpha, lineAlpha)
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.RadarRipple_radarRipple_lineWidth, lineWidth)
        lineCount = typedArray.getInteger(R.styleable.RadarRipple_radarRipple_lineCount, lineCount)
        moveAngleStep = typedArray.getInteger(R.styleable.RadarRipple_radarRipple_angleStep, moveAngleStep)
        animTime = typedArray.getInteger(R.styleable.RadarRipple_radarRipple_animTime, animTime)
        typedArray.recycle()

        initData()
    }

    override fun initData() {
        super.initData()
        interval = animTime * moveAngleStep / 360
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPaint()
    }

    override fun initPaint() {
        super.initPaint()
        bgPaint = Paint()
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
        bgPaint.color = bgColor

        circlePaint = Paint()
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = lineWidth.toFloat()
        circlePaint.isAntiAlias = true
        circlePaint.color = lineColor
        circlePaint.alpha = lineAlpha

        radarPaint = Paint()
        radarPaint.style = Paint.Style.FILL_AND_STROKE
        radarPaint.isAntiAlias = true
        radarPaint.shader = SweepGradient(realRadius.toFloat(), realRadius.toFloat(), intArrayOf(Color.TRANSPARENT, radarColor), null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawBg(canvas)
            drawCircle(canvas)
            drawRadar(canvas)
            rotateAngle()
        }
    }

    private fun drawBg(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, realRadius.toFloat(), bgPaint)
    }

    private fun drawRadar(canvas: Canvas) {
        canvas.concat(radarMatrix)
        canvas.drawCircle(centerX, centerY, realRadius.toFloat(), radarPaint)
    }

    private fun drawCircle(canvas: Canvas) {
        (1..lineCount).forEach {
            val radius = realRadius / (lineCount + 1) * it
            canvas.drawCircle(centerX, centerY, radius.toFloat(), circlePaint)
        }
    }

    private fun rotateAngle() {
        radarMatrix.postRotate(moveAngleStep.toFloat(), centerX, centerY)
        if (!isStop) {
            postInvalidateDelayed(interval.toLong())
        }
    }
    //endregion
}