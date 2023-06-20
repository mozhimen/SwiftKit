package com.mozhimen.uicorek.imagek

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.commons.IUicoreK
import kotlin.math.min


/**
 * @ClassName ImageKCircleText
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/18 11:00
 * @Version 1.0
 */
class ImageKCircleText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr), IUicoreK {
    override val TAG: String = "ImageKCircleText>>>>>"
    companion object {
        private val SCALE_TYPE = ScaleType.CENTER_CROP
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888

        private const val DEFAULT_COLOR_DRAWABLE_DIMENSION = 2
        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_OVERLAY = false
        private const val DEFAULT_FILL_COLOR = Color.TRANSPARENT
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_TEXT_SIZE = 22
        private const val DEFAULT_TEXT_PADDING = 4
    }

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()

    private val mShaderMatrix: Matrix = Matrix()
    private val mBitmapPaint = Paint()
    private val mBorderPaint = Paint()
    private val mFillPaint = Paint()
    private val mTextPaint = Paint()

    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mFillColor = DEFAULT_FILL_COLOR
    private var mTextContent: String? = null
    private var mTextColor = DEFAULT_TEXT_COLOR
    private var mTextSize = DEFAULT_TEXT_SIZE
    private var mTextPadding = DEFAULT_TEXT_PADDING

    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null
    private var mBitmapWidth = 0
    private var mBitmapHeight = 0

    private var mDrawableRadius = 0f
    private var mBorderRadius = 0f

    private var mColorFilter: ColorFilter? = null

    private var mReady = false
    private var mSetupPending = false
    private var mBorderOverlay = false

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ImageKCircleText, defStyleAttr, 0)
        mBorderWidth =
            typedArray.getDimensionPixelSize(R.styleable.ImageKCircleText_imageKCircleText_borderWidth, DEFAULT_BORDER_WIDTH)
        mBorderColor =
            typedArray.getColor(R.styleable.ImageKCircleText_imageKCircleText_borderColor, DEFAULT_BORDER_COLOR)
        mBorderOverlay =
            typedArray.getBoolean(R.styleable.ImageKCircleText_imageKCircleText_borderOverlay, DEFAULT_BORDER_OVERLAY)
        mFillColor =
            typedArray.getColor(R.styleable.ImageKCircleText_imageKCircleText_fillColor, DEFAULT_FILL_COLOR)
        mTextContent =
            typedArray.getString(R.styleable.ImageKCircleText_imageKCircleText_textContent)
        mTextColor =
            typedArray.getColor(R.styleable.ImageKCircleText_imageKCircleText_textColor, DEFAULT_TEXT_COLOR)
        mTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ImageKCircleText_imageKCircleText_textSize, DEFAULT_TEXT_SIZE)
        mTextPadding =
            typedArray.getDimensionPixelSize(R.styleable.ImageKCircleText_imageKCircleText_textPadding, DEFAULT_TEXT_PADDING)
        typedArray.recycle()
    }

    override fun initView() {
        super.setScaleType(SCALE_TYPE)
        mReady = true
        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) { String.format("ScaleType %s not supported.", scaleType) }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (mBitmap == null && TextUtils.isEmpty(mTextContent)) {
            return
        }
        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, mDrawableRadius, mFillPaint)
        }
        if (mBitmap != null) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, mDrawableRadius, mBitmapPaint)
        }
        if (mBorderWidth != 0) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, mBorderRadius, mBorderPaint)
        }
        if (!TextUtils.isEmpty(mTextContent)) {
            val fm = mTextPaint.fontMetricsInt
            canvas.drawText(
                mTextContent!!, width / 2 - mTextPaint.measureText(mTextContent) / 2, (height / 2 - fm.descent + (fm.bottom - fm.top) / 2).toFloat(), mTextPaint
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    fun getTextContent(): String? {
        return mTextContent
    }


    fun setText(@StringRes textResId: Int) {
        setText(resources.getString(textResId))
    }

    fun setText(textStr: String?) {
        mTextContent = textStr
        invalidate()
    }

    fun getTextColor(): Int {
        return mTextColor
    }

    fun setTextColor(@ColorInt mTextColor: Int) {
        this.mTextColor = mTextColor
        mTextPaint.color = mTextColor
        invalidate()
    }

    fun setTextColorResource(@ColorRes colorResource: Int) {
        setTextColor(resources.getColor(colorResource))
    }

    fun getTextSize(): Int {
        return mTextSize
    }

    fun setTextSize(textSize: Int) {
        mTextSize = textSize
        mTextPaint.textSize = textSize.toFloat()
        invalidate()
    }

    fun getTextPadding(): Int {
        return mTextPadding
    }

    fun setTextPadding(mTextPadding: Int) {
        this.mTextPadding = mTextPadding
        invalidate()
    }

    fun getBorderColor(): Int {
        return mBorderColor
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        if (borderColor == mBorderColor) {
            return
        }
        mBorderColor = borderColor
        mBorderPaint.color = mBorderColor
        invalidate()
    }

    fun setBorderColorResource(@ColorRes borderColorRes: Int) {
        setBorderColor(context.resources.getColor(borderColorRes))
    }

    fun getFillColor(): Int {
        return mFillColor
    }

    fun setFillColor(@ColorInt fillColor: Int) {
        if (fillColor == mFillColor) {
            return
        }
        mFillColor = fillColor
        mFillPaint.color = fillColor
        invalidate()
    }

    fun setFillColorResource(@ColorRes fillColorRes: Int) {
        setFillColor(context.resources.getColor(fillColorRes))
    }

    fun getBorderWidth(): Int {
        return mBorderWidth
    }

    fun setBorderWidth(borderWidth: Int) {
        if (borderWidth == mBorderWidth) {
            return
        }
        mBorderWidth = borderWidth
        setup()
    }

    fun isBorderOverlay(): Boolean {
        return mBorderOverlay
    }

    fun setBorderOverlay(borderOverlay: Boolean) {
        if (borderOverlay == mBorderOverlay) {
            return
        }
        mBorderOverlay = borderOverlay
        setup()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        mBitmap = bm
        setup()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        mBitmap = if (uri != null) getBitmapFromDrawable(drawable) else null
        setup()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }
        mColorFilter = cf
        mBitmapPaint.setColorFilter(mColorFilter)
        invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(DEFAULT_COLOR_DRAWABLE_DIMENSION, DEFAULT_COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG)
            } else {
                Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }
        if (width == 0 && height == 0) {
            return
        }
        if (mBitmap == null && TextUtils.isEmpty(mTextContent)) {
            invalidate()
            return
        }
        mTextPaint.isAntiAlias = true
        mTextPaint.color = mTextColor
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD)
        mTextPaint.textSize = mTextSize.toFloat()
        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()
        mFillPaint.style = Paint.Style.FILL
        mFillPaint.isAntiAlias = true
        mFillPaint.color = mFillColor
        mBorderRect[0f, 0f, width.toFloat()] = height.toFloat()
        mBorderRadius = min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f)
        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay) {
            mDrawableRect.inset(mBorderWidth.toFloat(), mBorderWidth.toFloat())
        }
        mDrawableRadius = min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)
        if (mBitmap != null) {
            mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mBitmapHeight = mBitmap!!.height
            mBitmapWidth = mBitmap!!.width
            mBitmapPaint.isAntiAlias = true
            mBitmapPaint.setShader(mBitmapShader)
            updateShaderMatrix()
        }
        invalidate()
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f
        mShaderMatrix.set(null)
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate((dx + 0.5f).toInt() + mDrawableRect.left, (dy + 0.5f).toInt() + mDrawableRect.top)
        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        if (!TextUtils.isEmpty(mTextContent)) {
            var textMeasuredSize = mTextPaint.measureText(mTextContent).toInt()
            textMeasuredSize += 2 * mTextPadding
            if (widthMeasureSpecMode == MeasureSpec.AT_MOST && heightMeasureSpecMode == MeasureSpec.AT_MOST) {
                if (textMeasuredSize > measuredWidth || textMeasuredSize > measuredHeight) {
                    setMeasuredDimension(textMeasuredSize, textMeasuredSize)
                }
            }
        }
    }
}