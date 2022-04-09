package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.viewk.commons.ViewK

/**
 * @ClassName Radar
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 20:11
 * @Version 1.0
 */
class RadarWave @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewK(context, attrs, defStyleAttr) {

    //region # variate
    private var radarColor = 0x287FF1
    private var waveColor = 0xFFFFFF
    private var waveCount = 4
    private var isWaveOverTurn = false
    private var moveAngleStep = 3
    private var animTime = 3000

    private lateinit var radarPaint: Paint
    private lateinit var wavePaint: Paint
    private lateinit var waveRectF: RectF

    private val radarMatrix = Matrix()

    private var waveRadius = realRadius / waveCount
    private var interval = animTime * moveAngleStep / 360
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
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarWave)
        radarColor = typedArray.getColor(R.styleable.RadarWave_radarWave_radarColor, radarColor)
        waveColor = typedArray.getColor(R.styleable.RadarWave_radarWave_waveColor, waveColor)
        waveCount = typedArray.getInteger(R.styleable.RadarWave_radarWave_waveCount, waveCount)
        isWaveOverTurn = typedArray.getBoolean(R.styleable.RadarWave_radarWave_isWaveOverTurn, isWaveOverTurn)
        moveAngleStep = typedArray.getInteger(R.styleable.RadarWave_radarWave_angleStep, moveAngleStep)
        animTime = typedArray.getInteger(R.styleable.RadarWave_radarWave_animTime, animTime)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
        initPaint()
    }

    override fun initData() {
        super.initData()
        waveRadius = realRadius / waveCount
        interval = animTime * moveAngleStep / 360
    }

    override fun initPaint() {
        super.initPaint()
        wavePaint = Paint()
        wavePaint.style = Paint.Style.STROKE
        wavePaint.isAntiAlias = true
        wavePaint.color = waveColor
        wavePaint.strokeWidth = waveRadius

        radarPaint = Paint()
        radarPaint.style = Paint.Style.FILL_AND_STROKE
        radarPaint.isAntiAlias = true
        radarPaint.shader = SweepGradient(realRadius, realRadius, intArrayOf(Color.TRANSPARENT, radarColor), null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawWave(canvas)
            drawRadar(canvas)
            rotateAngle()
        }
    }

    private fun drawRadar(canvas: Canvas) {
        canvas.concat(radarMatrix)
        canvas.drawCircle(centerX, centerY, realRadius, radarPaint)
    }

    private fun drawWave(canvas: Canvas) {
        (1..waveCount).forEach {
            waveRectF = RectF(
                centerX - realRadius + waveRadius / 2 + waveRadius * (waveCount - it),
                centerY - realRadius + waveRadius / 2 + waveRadius * (waveCount - it),
                centerX + realRadius - waveRadius / 2 - waveRadius * (waveCount - it),
                centerY + realRadius - waveRadius / 2 - waveRadius * (waveCount - it)
            )
            wavePaint.alpha = if (!isWaveOverTurn) {
                255 / waveCount * (waveCount - it)
            } else {
                255 / waveCount * it
            }
            canvas.drawArc(waveRectF, 0f, 360f, false, wavePaint)
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