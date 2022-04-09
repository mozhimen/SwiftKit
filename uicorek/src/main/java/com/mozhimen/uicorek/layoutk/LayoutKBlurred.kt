package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.mozhimen.basicsk.utilk.UtilKBitmap
import com.mozhimen.basicsk.utilk.UtilKBitmapBlur
import com.mozhimen.uicorek.R

/**
 * @ClassName BlurredLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:18
 * @Version 1.0
 */
class LayoutKBlurred : RelativeLayout {
    constructor(context: Context?) : super(context) {
        init(context!!)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!)
        initAttr(context, attrs!!)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context!!)
        initAttr(context, attrs!!)
    }

    //region #全局变量
    private val ALPHA_MAX_VALUE = 255

    private val TAG = "LayoutKBlurred>>>>>"

    private lateinit var mContext: Context

    //是否移动背景图片
    private var isMove = false

    //是否禁用模糊效果
    private var isDisableBlurred = false

    //原图Bitmap
    private lateinit var mOriginBitmap: Bitmap

    //模糊的Bitmap
    private var mBlurredBitmap: Bitmap? = null

    //模糊ImageView
    private lateinit var blurredImageView: ImageView

    //原图ImageView
    private lateinit var originImageView: ImageView
    //endregion

    private fun init(context: Context) {
        mContext = context
        LayoutInflater.from(context).inflate(R.layout.layoutk_blurred, this)
        originImageView = findViewById(R.id.layoutk_img_origin)
        blurredImageView = findViewById(R.id.layoutk_img_blurred)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initAttr(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBlurred)
        val drawable = typedArray.getDrawable(R.styleable.LayoutKBlurred_layoutKBlurred_src)
        isMove = typedArray.getBoolean(R.styleable.LayoutKBlurred_layoutKBlurred_isMove, false)
        isDisableBlurred = typedArray.getBoolean(R.styleable.LayoutKBlurred_layoutKBlurred_isDisableBlurred, false)

        typedArray.recycle()

        //模糊图片
        drawable?.let {
            mOriginBitmap = UtilKBitmap.drawable2Bitmap(drawable)
            mBlurredBitmap = UtilKBitmapBlur.blur(context, mOriginBitmap)
        }

        //设置模糊禁用
        if (!isDisableBlurred) {
            blurredImageView.visibility = VISIBLE
        }

        //设置背景位移
        drawable?.let {
            setMove(context, isMove)
        }
    }

    /**
     * 设置背景图片的移动效果
     */
    private fun setMove(context: Context, isMove: Boolean) {
        if (isMove) {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val point = Point()
            display.getSize(point)
            val height = point.y
            setBlurredHeight(height, originImageView)
            setBlurredHeight(height, blurredImageView)
        }
    }

    /**
     * 改变图片的高度
     */
    private fun setBlurredHeight(height: Int, imageView: ImageView) {
        val layoutParams: ViewGroup.LayoutParams = imageView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = height + 100
        imageView.requestLayout()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setImageView()
    }

    /**
     * 填充ImageView
     */
    private fun setImageView() {
        blurredImageView.setImageBitmap(mBlurredBitmap)
        originImageView.setImageBitmap(mOriginBitmap)
    }

    //region #自定义方法
    /**
     * 添加待模糊图片
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setBlurredImageView(blurredBitmap: Bitmap?) {
        blurredBitmap?.let {
            mOriginBitmap = blurredBitmap
            mBlurredBitmap = UtilKBitmapBlur.blur(mContext, blurredBitmap)
            setImageView()
            setMove(mContext, isMove)
        }
    }

    /**
     * 添加待模糊图片2
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setBlurredImageView(blurDrawable: Drawable?) {
        blurDrawable?.let {
            mOriginBitmap = UtilKBitmap.drawable2Bitmap(blurDrawable)
            mBlurredBitmap = UtilKBitmapBlur.blur(mContext, mOriginBitmap)
            setImageView()
            setMove(mContext, isMove)
        }
    }

    /**
     * 设置模糊程度
     */
    fun setBlurredLevel(level: Int) {
        if (level < 0 || level > 100) {
            throw IllegalStateException("No validate level, the value must be 0~100")
        }
        if (!isDisableBlurred) {
            originImageView.imageAlpha = (ALPHA_MAX_VALUE - level * 2.55).toInt()
        }
    }

    /**
     * 设置图片上移的距离
     */
    fun setBlurredTop(height: Int) {
        originImageView.top = -height
        blurredImageView.top = -height
    }

    /**
     * 显示模糊的图片
     */
    fun showBlurredImage() {
        blurredImageView.visibility = VISIBLE
    }

    /**
     * 禁用模糊效果
     */
    @SuppressLint("Range")
    fun disableBlurredEffect() {
        isDisableBlurred = true
        originImageView.alpha = ALPHA_MAX_VALUE.toFloat()
        blurredImageView.visibility = INVISIBLE
    }

    /**
     * 启用模糊效果
     */
    fun enableBlurredEffect() {
        isDisableBlurred = false
        blurredImageView.visibility = VISIBLE
    }
    //endregion
}