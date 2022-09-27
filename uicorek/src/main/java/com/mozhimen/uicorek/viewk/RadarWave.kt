package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.mozhimen.basick.basek.view.BaseKView
import com.mozhimen.basick.basek.commons.IBaseKViewAction
import com.mozhimen.uicorek.R

/**
 * @ClassName Radar
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 20:11
 * @Version 1.0
 */
class RadarWave @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKView(context, attrs, defStyleAttr, defStyleRes), IBaseKViewAction {

    //region # variate
    private var _radarColor = 0x287FF1
    private var _waveColor = 0xFFFFFF
    private var _waveCount = 4
    private var _isWaveOverTurn = false
    private var _moveAngleStep = 3
    private var _animTime = 3000

    private lateinit var _radarPaint: Paint
    private lateinit var _wavePaint: Paint
    private lateinit var _waveRectF: RectF

    private val _radarMatrix = Matrix()

    private var _waveRadius = realRadius / _waveCount
    private var _interval = _animTime * _moveAngleStep / 360
    private var _isStop = false
    //endregion

    override fun requireStart() {
        _isStop = false
        postInvalidate()
    }

    override fun requireStop() {
        _isStop = true
        postInvalidate()
    }

    //region # private function
    init {
        initAttrs(attrs, defStyleAttr)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarWave)
        _radarColor = typedArray.getColor(R.styleable.RadarWave_radarWave_radarColor, _radarColor)
        _waveColor = typedArray.getColor(R.styleable.RadarWave_radarWave_waveColor, _waveColor)
        _waveCount = typedArray.getInteger(R.styleable.RadarWave_radarWave_waveCount, _waveCount)
        _isWaveOverTurn = typedArray.getBoolean(R.styleable.RadarWave_radarWave_isWaveOverTurn, _isWaveOverTurn)
        _moveAngleStep = typedArray.getInteger(R.styleable.RadarWave_radarWave_angleStep, _moveAngleStep)
        _animTime = typedArray.getInteger(R.styleable.RadarWave_radarWave_animTime, _animTime)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
        initPaint()
    }

    override fun initData() {
        _waveRadius = realRadius / _waveCount
        _interval = _animTime * _moveAngleStep / 360
    }

    override fun initPaint() {
        _wavePaint = Paint()
        _wavePaint.style = Paint.Style.STROKE
        _wavePaint.isAntiAlias = true
        _wavePaint.color = _waveColor
        _wavePaint.strokeWidth = _waveRadius

        _radarPaint = Paint()
        _radarPaint.style = Paint.Style.FILL_AND_STROKE
        _radarPaint.isAntiAlias = true
        _radarPaint.shader = SweepGradient(realRadius, realRadius, intArrayOf(Color.TRANSPARENT, _radarColor), null)
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
        canvas.concat(_radarMatrix)
        canvas.drawCircle(centerX, centerY, realRadius, _radarPaint)
    }

    private fun drawWave(canvas: Canvas) {
        (1.._waveCount).forEach {
            _waveRectF = RectF(
                centerX - realRadius + _waveRadius / 2 + _waveRadius * (_waveCount - it),
                centerY - realRadius + _waveRadius / 2 + _waveRadius * (_waveCount - it),
                centerX + realRadius - _waveRadius / 2 - _waveRadius * (_waveCount - it),
                centerY + realRadius - _waveRadius / 2 - _waveRadius * (_waveCount - it)
            )
            _wavePaint.alpha = if (!_isWaveOverTurn) {
                255 / _waveCount * (_waveCount - it)
            } else {
                255 / _waveCount * it
            }
            canvas.drawArc(_waveRectF, 0f, 360f, false, _wavePaint)
        }
    }

    private fun rotateAngle() {
        _radarMatrix.postRotate(_moveAngleStep.toFloat(), centerX, centerY)
        if (!_isStop) {
            postInvalidateDelayed(_interval.toLong())
        }
    }
    //endregion
}