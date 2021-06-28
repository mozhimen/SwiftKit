package com.mozhimen.swiftmk.widget.scan

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.mozhimen.swiftmk.R

/**
 * @ClassName QRScanView
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 14:18
 * @Version 1.0
 */
class QRScanView: View {
    private var mCustomScanLineDrawable: Drawable? = null
    private var mBorderSize = 0
    private var mBorderColor = 0
    private var mAnimTime = 1000
    private var mIsScanLineReverse = false
    private var mOriginQRCodeScanLineBitmap: Bitmap? = null
    private lateinit var mPaint: Paint
    private var mScanLineBitmap: Bitmap? = null
    private var mScanLineTop = 0f
    private var mMoveStepDistance = 0
    private var mAnimDelayTime = 0
    private var mFramingRect: Rect? = null
    private var mRectWidth = 0
    private var mRectHeight = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        mPaint = Paint()
        mRectWidth = BGAQRCodeUtil.dp2px(context, 200f)
        mBorderSize = BGAQRCodeUtil.dp2px(context, 1f)
        mBorderColor = Color.WHITE
        mMoveStepDistance = BGAQRCodeUtil.dp2px(context, 2f)

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.QRScanView)
        val count = typedArray.indexCount
        for (i in 0 until count) {
            initCustomAttr(typedArray.getIndex(i), typedArray)
        }
        typedArray.recycle()

        afterInitCustomAttrs()
    }

    private fun initCustomAttr(attr: Int, typedArray: TypedArray) {
        when (attr) {
            R.styleable.QRScanView_customScanLineDrawable -> {
                mCustomScanLineDrawable = typedArray.getDrawable(attr)
            }
            R.styleable.QRScanView_borderSize -> {
                mBorderSize = typedArray.getDimensionPixelSize(attr, mBorderSize)
            }
            R.styleable.QRScanView_borderColor -> {
                mBorderColor = typedArray.getColor(attr, mBorderColor)
            }
            R.styleable.QRScanView_animTime -> {
                mAnimTime = typedArray.getInteger(attr, mAnimTime)
            }
            R.styleable.QRScanView_isScanLineReverse -> {
                mIsScanLineReverse = typedArray.getBoolean(attr, mIsScanLineReverse)
            }
        }
    }

    private fun afterInitCustomAttrs() {
        mCustomScanLineDrawable?.let {
            mOriginQRCodeScanLineBitmap = (mCustomScanLineDrawable as BitmapDrawable).bitmap
        }
        refreshScanBox()
    }

    private fun refreshScanBox() {
        mCustomScanLineDrawable?.let {
            mScanLineBitmap = mOriginQRCodeScanLineBitmap
        }
        calFramingRect()
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (mFramingRect == null) {
            return
        }

        canvas?.let {
            // 画边框线
            drawBorderLine(canvas)

            // 画扫描线
            drawScanLine(canvas)
        }

        // 移动扫描线的位置
        moveScanLine()
    }

    /**
     * 画边框线
     */
    private fun drawBorderLine(canvas: Canvas) {
        if (mBorderSize > 0) {
            mPaint.style = Paint.Style.STROKE
            mPaint.color = mBorderColor
            mPaint.strokeWidth = mBorderSize.toFloat()
            canvas.drawRect(mFramingRect!!, mPaint)
        }
    }

    /**
     * 画扫描线
     */
    private fun drawScanLine(canvas: Canvas) {
        mScanLineBitmap?.let {
            val lineRect = RectF(mFramingRect!!.left.toFloat(), mScanLineTop,
                mFramingRect!!.right.toFloat(), mScanLineTop + it.height)
            canvas.drawBitmap(it, null, lineRect, mPaint)
        }
    }

    /**
     * 移动扫描线的位置
     */
    private fun moveScanLine() {
        // 处理非网格扫描图片的情况
        mScanLineTop += mMoveStepDistance.toFloat()
        var scanLineSize = 0
        mScanLineBitmap?.let {
            scanLineSize = it.height
        }
        if (mIsScanLineReverse) {
            if (mScanLineTop + scanLineSize >= mFramingRect!!.bottom || mScanLineTop < mFramingRect!!.top) {
                mMoveStepDistance = -mMoveStepDistance
            }
        } else {
            if (mScanLineTop + scanLineSize >= mFramingRect!!.bottom) {
                mScanLineTop = (mFramingRect!!.top + scanLineSize).toFloat()
            }
        }
        postInvalidateDelayed(mAnimDelayTime.toLong(), mFramingRect!!.left, mFramingRect!!.top, mFramingRect!!.right, mFramingRect!!.bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calFramingRect()
    }

    private fun calFramingRect() {
        val leftOffset = (width - mRectWidth) / 2
        mFramingRect = Rect(leftOffset, 0, leftOffset + mRectWidth, mRectHeight)
    }

    fun getScanViewHeight() = mRectHeight

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val mWidthMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        mRectWidth = height
        mRectHeight = height
        mAnimDelayTime = ((1f * mAnimTime * mMoveStepDistance) / mRectHeight).toInt()
        super.onMeasure(mWidthMeasureSpec, heightMeasureSpec)
    }
}