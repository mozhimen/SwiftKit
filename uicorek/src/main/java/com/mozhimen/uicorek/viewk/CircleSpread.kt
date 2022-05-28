package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.basek.BaseKView
import com.mozhimen.uicorek.R

/**
 * @ClassName CircleSpread
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/1 15:40
 * @Version 1.0
 */
class CircleSpread @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    BaseKView(context, attrs, defStyleAttr) {

    //region #variate
    private var centerColor = 0xFFFFFF //中心圆颜色
    private var centerDrawable: Drawable? = null//中心圆图片
    private var centerRadius = 20f.dp2px()//中心圆半径
    private var spreadColor = 0xFFFFFF //扩散圆颜色
    private var spreadCount = 4 //扩散圆最大数
    private var spreadIsRipple = true//是否是波纹(否为波浪)
    private var circlesDistance = 5f.dp2px() //每个扩散圆的距离
    private var animTime = 2000 //从中心圆扩散到边界圆的间隔

    private lateinit var centerPaint: Paint //中心圆paint
    private lateinit var spreadPaint: Paint //扩散圆paint
    private lateinit var circleRactF: RectF

    private var centerBitmap: Bitmap? = null
    private var realSpreadCount = spreadCount
    private var radiusAddStep = 2f.dp2px()
    private var alphaMinusStep = 255f * radiusAddStep / (realRadius - centerRadius)
    private var interval: Long =
        animTime.toLong() * radiusAddStep.toLong() / (realRadius.toLong() - centerRadius.toLong())

    private val spreadCircles = ArrayDeque<Pair<Float, Float>>()//半径,alpha

    private var isStop = false
    //endregion

    override fun requireStop() {
        isStop = true
    }

    override fun requireStart() {
        isStop = false
        spreadCircles.add(centerRadius.toFloat() to 255f)
        invalidate()
    }

    //region #private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleSpread)
        centerColor =
            typedArray.getColor(R.styleable.CircleSpread_circleSpread_centerColor, centerColor)
        centerDrawable =
            typedArray.getDrawable(R.styleable.CircleSpread_circleSpread_centerDrawable)
        centerRadius = typedArray.getDimensionPixelSize(
            R.styleable.CircleSpread_circleSpread_centerRadius,
            centerRadius
        )
        spreadColor =
            typedArray.getColor(R.styleable.CircleSpread_circleSpread_spreadColor, spreadColor)
        spreadCount =
            typedArray.getInteger(R.styleable.CircleSpread_circleSpread_spreadCount, spreadCount)
        spreadIsRipple = typedArray.getBoolean(
            R.styleable.CircleSpread_circleSpread_spreadIsRipple,
            spreadIsRipple
        )
        circlesDistance =
            typedArray.getDimensionPixelOffset(
                R.styleable.CircleSpread_circleSpread_circlesDistance,
                circlesDistance
            )
        animTime = typedArray.getInteger(R.styleable.CircleSpread_circleSpread_animTime, animTime)
        typedArray.recycle()

        //init data
        centerDrawable?.let {
            centerBitmap = (it as BitmapDrawable).bitmap
        }

        //init paint
        initPaint()
    }

    override fun initPaint() {
        super.initPaint()
        centerPaint = Paint()
        centerPaint.isAntiAlias = true
        centerPaint.color = centerColor

        spreadPaint = Paint()
        spreadPaint.color = spreadColor
        spreadPaint.isAntiAlias = true
        if (spreadIsRipple) {
            spreadPaint.style = Paint.Style.FILL
        } else {
            spreadPaint.style = Paint.Style.STROKE
        }
        spreadPaint.alpha = 255
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //计算真实的阔散圆数量
        realSpreadCount =
            if (((realRadius - centerRadius) / circlesDistance) < spreadCount) {
                ((realRadius - centerRadius) / circlesDistance).toInt()
            } else spreadCount
        spreadCircles.add(centerRadius.toFloat() to 255f)
        //计算时间及步长
        interval =
            animTime.toLong() * radiusAddStep.toLong() / (realRadius.toLong() - centerRadius.toLong())
        alphaMinusStep = 255f * radiusAddStep / (realRadius - centerRadius)
        //rect
        circleRactF =
            RectF(
                centerX - centerRadius,
                centerY - centerRadius,
                centerX + centerRadius,
                centerY + centerRadius
            )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawCircleSpread(canvas)
            drawCircleCenter(canvas)
            changeRadiusAndAlpha()
        }
    }

    private fun changeRadiusAndAlpha() {
        spreadCircles.forEachIndexed { i, c ->
            spreadCircles[i] =
                c.first + radiusAddStep to c.second - alphaMinusStep
        }

        if (spreadCircles[spreadCircles.size - 1].first - centerRadius >= circlesDistance && spreadCircles.size < realSpreadCount && !isStop) {
            spreadCircles.addLast(centerRadius.toFloat() to 255f)
        }

        if (spreadCircles[0].first >= realRadius || spreadCircles[0].second <= 0f) {
            spreadCircles.removeFirst()
        }

        if (spreadCircles.size > 0) {
            postInvalidateDelayed(interval)
        }
    }

    private fun drawCircleCenter(canvas: Canvas) {
        centerBitmap?.let {
            canvas.drawBitmap(it, null, circleRactF, centerPaint)
        } ?: canvas.drawCircle(centerX, centerY, centerRadius.toFloat(), centerPaint)
    }

    private fun drawCircleSpread(canvas: Canvas) {
        spreadCircles.forEach {
            spreadPaint.alpha = it.second.toInt()
            canvas.drawCircle(centerX, centerY, it.first, spreadPaint)
        }
    }
    //endregion
}