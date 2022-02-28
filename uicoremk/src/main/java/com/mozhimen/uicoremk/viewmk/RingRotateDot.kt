package com.mozhimen.uicoremk.viewmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.viewmk.commons.ViewMK

/**
 * @ClassName RingProgress
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/8 11:56
 * @Version 1.0
 */
class RingRotateDot @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewMK(context, attrs, defStyleAttr) {

    /*//region # variate
    private val ringStartColor = Color.WHITE
    private val ringEndColor = Color.TRANSPARENT
    private var dotDrawable = R.mipmap.ring_rotate_flash_dot
    private val ringWidth = 10f.dp2px()

    private lateinit var arcPaint: Paint
    private lateinit var dotPaint: Paint
    private lateinit var sweepGradient: SweepGradient

    private var rotateMatrix = Matrix()

    private var dotBitmap: Bitmap? = null

    private var dotPos = FloatArray(2)// 当前点的实际位置
    private var dotTan = FloatArray(2)// 当前点的tangent值,用于计算图片所需旋转的角度
    //endregion

    //region # private function
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.)
        dotDrawable = typedArray.getResourceId(R.)
        typedArray.recycle()

        dotBitmap = (context.getDrawable(dotDrawable) as BitmapDrawable).bitmap
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        initPaint()
    }

    override fun initPaint() {
        super.initPaint()
        arcPaint = Paint()
        arcPaint.isAntiAlias = true
        arcPaint.isDither = true
        arcPaint.isFilterBitmap = true// 开启图像过滤，对位图进行滤波处理。
        arcPaint.color = ringStartColor
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeCap = Paint.Cap.ROUND
        arcPaint.strokeWidth = ringWidth
        //设置渐变色
        sweepGradient = SweepGradient(centerX, centerY, intArrayOf(ringEndColor, ringStartColor), null)
        //arcPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)//将绘制的内容显示在第一次绘制内容之上

        dotPaint = Paint()
        dotPaint.style = Paint.Style.FILL
        dotPaint.isAntiAlias = true
        //dotPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)//canvas去锯齿


        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawCircle(mCenterX.toFloat(), mCenterY, mCenterX - mCircleR - interval, mCirclePaint!!)
    }

    private fun drawRing(canvas: Canvas) {
        matrix.setRotate(startAngle.toFloat(), mCenterX.toFloat(), mCenterY)
        sweepGradient!!.setLocalMatrix(matrix)
        mProgressPaint!!.shader = sweepGradient
        canvas.drawArc(
            RectF(
                0 + mCircleR + interval, 0 + mCircleR + interval,
                mTotalWidth - mCircleR - interval, mTotalHeight - mCircleR - interval
            ),
            (2 + startAngle).toFloat(), 350, false, mProgressPaint!!
        )
        startAngle++
        if (startAngle == 360) {
            startAngle = 1
        }
    }

    private fun drawDot(canvas: Canvas) {
        //绘制白色小星星
        val orbit = Path()
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(
            RectF(
                0 + mCircleR + interval, 0 + mCircleR + interval,
                mTotalWidth - mCircleR - interval, mTotalHeight - mCircleR - interval
            ), (2 + startAngle).toFloat(), 350
        )
        // 创建 PathMeasure
        val measure = PathMeasure(orbit, false)
        measure.getPosTan(measure.length * 1, pos, tan)
        mMatrix!!.reset()
        mMatrix!!.postScale(2, 2)
        mMatrix!!.postTranslate(
            pos[0] - mLititleBitmap!!.width,
            pos[1] - mLititleBitmap!!.height
        ) // 将图片绘制中心调整到与当前点重合
        canvas.drawBitmap(mLititleBitmap!!, mMatrix!!, mbitmapPaint) //绘制球
        mbitmapPaint!!.color = Color.WHITE
        //绘制实心小圆圈
        canvas.drawCircle(pos[0], pos[1], 5, mbitmapPaint!!)

        //启动绘制
        postInvalidateDelayed(10)
    }
    //endregion*/
}