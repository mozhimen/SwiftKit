package com.mozhimen.uicoremk.viewmk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.viewmk.commons.ViewMK
import kotlin.math.asin

/**
 * @ClassName RingProgress
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/8 11:56
 * @Version 1.0
 */
class RingRotate @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewMK(context, attrs, defStyleAttr) {

    //region # variate
    private var ringStartColor = 0xFFFFFF
    private var ringEndColor = Color.TRANSPARENT
    private var ringWidth = 10f.dp2px()
    private var moveAngleStep = 3
    private var animTime = 2000

    private lateinit var ringPaint: Paint
    private lateinit var sweepGradient: Shader
    private lateinit var ringRectF: RectF

    private var ringMatrix = Matrix()

    private var isStop = false
    private var interval = animTime * moveAngleStep / 360
    private var angleOffset = asin(ringWidth / 2f / realRadius)
    //endregion

    override fun requireStart() {
        super.requireStart()
        isStop = false
        invalidate()
    }

    override fun requireStop() {
        super.requireStop()
        isStop = true
    }

    //region # private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingRotate)
        ringStartColor = typedArray.getColor(R.styleable.RingRotate_ringRotate_ringStartColor, ringStartColor)
        ringEndColor = typedArray.getColor(R.styleable.RingRotate_ringRotate_ringEndColor, ringEndColor)
        ringWidth = typedArray.getDimensionPixelSize(R.styleable.RingRotate_ringRotate_ringWidth, ringWidth)
        moveAngleStep = typedArray.getInteger(R.styleable.RingRotate_ringRotate_angleStep, moveAngleStep)
        animTime = typedArray.getInteger(R.styleable.RingRotate_ringRotate_animTime, animTime)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initData()
        initPaint()
        initView()
    }

    override fun initData() {
        super.initData()
        interval = animTime * moveAngleStep / 360
        angleOffset =
            Math.toDegrees(asin(ringWidth / 2f / (realRadius - ringWidth / 2f)).toDouble()).toFloat()
    }

    override fun initView() {
        super.initView()
        ringRectF = RectF(
            centerX - realRadius + ringWidth / 2,
            centerY - realRadius + ringWidth / 2,
            centerX + realRadius - ringWidth / 2,
            centerY + realRadius - ringWidth / 2
        )
    }

    override fun initPaint() {
        super.initPaint()
        ringPaint = Paint()
        ringPaint.isAntiAlias = true
        ringPaint.strokeWidth = ringWidth.toFloat()
        ringPaint.style = Paint.Style.STROKE
        ringPaint.strokeCap = Paint.Cap.ROUND
        sweepGradient = SweepGradient(
            centerX,
            centerY,
            intArrayOf(ringEndColor, ringStartColor), null
        )
        ringPaint.shader = sweepGradient

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawRotateRing(canvas)
            rotateRing()
        }
    }

    private fun rotateRing() {
        ringMatrix.postRotate(moveAngleStep.toFloat(), centerX, centerY)
        if (!isStop) {
            postInvalidateDelayed(interval.toLong())
        }
    }

    private fun drawRotateRing(canvas: Canvas) {
        canvas.concat(ringMatrix)
        canvas.drawArc(ringRectF, angleOffset, 290f, false, ringPaint)
    }
    //endregion
}