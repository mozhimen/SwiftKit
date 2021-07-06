package com.mozhimen.swiftmk.widget.layout

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import com.mozhimen.swiftmk.R
import com.mozhimen.swiftmk.utils.image.BlurBitmap
import com.mozhimen.swiftmk.utils.image.ImageUtil
import java.lang.IllegalStateException

/**
 * @ClassName BlurredLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:18
 * @Version 1.0
 */
class BlurredLayout : RelativeLayout {
    constructor(context: Context?) : super(context) {
        init(context!!)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {

    }

    //region #全局变量
    private val ALPHA_MAX_VALUE = 255

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
        LayoutInflater.from(context).inflate(R.layout.layout_blurred, this)
        originImageView = findViewById(R.id.blurred_img_origin)
        blurredImageView = findViewById(R.id.blurred_img_blurred)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun initAttr(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurredLayout)
        val drawable = typedArray.getDrawable(R.styleable.BlurredLayout_src)
        isMove = typedArray.getBoolean(R.styleable.BlurredLayout_isMove, false)
        isDisableBlurred = typedArray.getBoolean(R.styleable.BlurredLayout_isDisableBlurred, false)

        typedArray.recycle()

        //模糊图片
        drawable?.let {
            mOriginBitmap = ImageUtil.drawableToBitmap(drawable)
            mBlurredBitmap = BlurBitmap.blur(context, mOriginBitmap)
        }

        //设置模糊禁用
        if (!isDisableBlurred) {
            blurredImageView.visibility = View.VISIBLE
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
        val layoutParams = imageView.layoutParams as ViewGroup.LayoutParams
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
            mBlurredBitmap = BlurBitmap.blur(mContext, blurredBitmap)
        }
    }

    /**
     * 添加待模糊图片2
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setBlurredImageView(blurDrawable: Drawable?) {
        blurDrawable?.let {
            mOriginBitmap = ImageUtil.drawableToBitmap(blurDrawable)
            mBlurredBitmap = BlurBitmap.blur(mContext, mOriginBitmap)
            setImageView()
            setMove(mContext, isMove)
        }
    }

    /**
     * 设置模糊程度
     */
    fun setBlurredLevel(level: Int) {
        if (level < 0 || level > 100){
            throw IllegalStateException()
        }
    }
    //endregion
}