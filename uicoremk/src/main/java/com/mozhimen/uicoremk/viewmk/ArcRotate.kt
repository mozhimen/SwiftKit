package com.mozhimen.uicoremk.viewmk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.mozhimen.basicsmk.utilmk.dp2px
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.viewmk.commons.ViewMK

/**
 * @ClassName CircleRotate
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/7 17:51
 * @Version 1.0
 */
class ArcRotate @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewMK(context, attrs, defStyleAttr) {

    //region # variate
    private var gradientStart = 0xFFFFFF
    private var gradientEnd = 0xFFFFFF

    //private var gradientDistance = 10f.dp2px()
    private var lineWidth = 2f.dp2px()

    private lateinit var arcPaint: Paint

    private lateinit var arcRectF: RectF
    private lateinit var sweepGradientTop: SweepGradient
    private lateinit var sweepGradientBottom: SweepGradient
    //endregion

    //region # private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcRotate)
        gradientStart = typedArray.getColor(R.styleable.ArcRotate_arcRotate_gradientStart, gradientStart)
        gradientEnd = typedArray.getColor(R.styleable.ArcRotate_arcRotate_gradientEnd, gradientEnd)
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.ArcRotate_arcRotate_lineWidth, lineWidth)
        typedArray.recycle()

        //init paint
        initPaint()
    }

    override fun initPaint() {
        super.initPaint()
        arcPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeCap = Paint.Cap.ROUND
        arcPaint.strokeWidth = lineWidth.toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sweepGradientTop =
            SweepGradient(
                realRadius.toFloat(), realRadius.toFloat(),
                intArrayOf(gradientStart, gradientEnd),
                floatArrayOf(0.25f, 0.5f)
            )
        sweepGradientBottom =
            SweepGradient(
                realRadius.toFloat(), realRadius.toFloat(),
                intArrayOf(gradientStart, gradientEnd),
                floatArrayOf(0.75f, 1f)
            )
        arcRectF = RectF(
            centerX - realRadius + lineWidth, centerY - realRadius + lineWidth,
            centerX + realRadius - lineWidth, centerY + realRadius - lineWidth
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawCircleLine(canvas)
        }
    }

    private fun drawCircleLine(canvas: Canvas) {
        arcPaint.shader = sweepGradientTop
        canvas.drawArc(arcRectF, 90f, 90f, false, arcPaint)
        arcPaint.shader = sweepGradientBottom
        canvas.drawArc(arcRectF, 90f, 90f, false, arcPaint)
    }
    //endregion
}