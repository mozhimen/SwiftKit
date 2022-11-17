package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IntRange
import com.mozhimen.uicorek.layoutk.commons.LayoutKRelative
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapTrans
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapConv
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.uicorek.R

/**
 * @ClassName BlurredLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:18
 * @Version 1.0
 */
class LayoutKBlurred @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutKRelative(context, attrs, defStyleAttr) {

    //region #全局变量
    //是否移动背景图片
    private var _isMove = false

    //是否禁用模糊效果
    private var _isBlurredEnable = false

    //获得的模糊图片Drawable
    private var _blurredDrawable: Drawable? = null

    //原图Bitmap
    private lateinit var _originBitmap: Bitmap

    //模糊的Bitmap
    private var _blurredBitmap: Bitmap? = null

    //模糊ImageView
    private lateinit var _blurredImageView: ImageView

    //原图ImageView
    private lateinit var _originImageView: ImageView
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    /**
     * 添加待模糊图片
     */
    fun setBlurredImageView(blurredBitmap: Bitmap) {
        _originBitmap = blurredBitmap
        _blurredBitmap = UtilKBitmapConv.blurBitmap(blurredBitmap)
        setImageView(_originBitmap, _blurredBitmap!!)
        setMove(_isMove)
    }

    /**
     * 添加待模糊图片2
     */
    fun setBlurredImageView(blurDrawable: Drawable) {
        _originBitmap = UtilKBitmapTrans.drawable2Bitmap(blurDrawable)
        _blurredBitmap = UtilKBitmapConv.blurBitmap(_originBitmap)
        setImageView(_originBitmap, _blurredBitmap!!)
        setMove(_isMove)
    }

    /**
     * 设置模糊程度
     */
    fun setBlurredLevel(@IntRange(from = 0, to = 100) level: Int) {
        require(level in 0..100) { "No validate level, the value must be 0~100" }
        if (_isBlurredEnable) {
            _originImageView.imageAlpha = (255f - level * 2.55).toInt()
        }
    }

    /**
     * 设置图片上移的距离
     */
    fun setBlurredTop(height: Int) {
        _originImageView.top = -height
        _blurredImageView.top = -height
    }

    /**
     * 显示模糊的图片
     */
    fun showBlurredImage() {
        _blurredImageView.visibility = VISIBLE
    }

    /**
     * 禁用模糊效果
     */
    fun disableBlurredEffect() {
        _isBlurredEnable = true
        _originImageView.alpha = 1f
        _blurredImageView.visibility = INVISIBLE
    }

    /**
     * 启用模糊效果
     */
    fun enableBlurredEffect() {
        _isBlurredEnable = false
        _blurredImageView.visibility = VISIBLE
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_blurred, this)
        _originImageView = findViewById(R.id.layoutk_img_origin)
        _blurredImageView = findViewById(R.id.layoutk_img_blurred)

        //模糊图片
        _blurredDrawable?.let {
            _originBitmap = UtilKBitmapTrans.drawable2Bitmap(it)
            _blurredBitmap = UtilKBitmapConv.blurBitmap(_originBitmap)
        }

        //设置模糊禁用
        if (_isBlurredEnable) {
            _blurredImageView.visibility = VISIBLE
        }

        //设置背景位移
        _blurredDrawable?.let {
            setMove(_isMove)
        }
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBlurred)
        _blurredDrawable = typedArray.getDrawable(R.styleable.LayoutKBlurred_layoutKBlurred_src)
        _isMove = typedArray.getBoolean(R.styleable.LayoutKBlurred_layoutKBlurred_isMove, false)
        _isBlurredEnable = typedArray.getBoolean(R.styleable.LayoutKBlurred_layoutKBlurred_isBlurredEnable, true)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setImageView(_originBitmap, _blurredBitmap!!)
    }

    private fun setMove(isMove: Boolean) {//设置背景图片的移动效果
        if (isMove) {
            val height = UtilKScreen.getScreenHeight()
            setBlurredHeight(height, _originImageView)
            setBlurredHeight(height, _blurredImageView)
        }
    }

    private fun setBlurredHeight(height: Int, imageView: ImageView) {//改变图片的高度
        val layoutParams: ViewGroup.LayoutParams = imageView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = height + 100
        imageView.requestLayout()
    }

    private fun setImageView(originBitmap: Bitmap, blurredBitmap: Bitmap) {
        _originImageView.setImageBitmap(originBitmap)
        _blurredImageView.setImageBitmap(blurredBitmap)//填充ImageView
    }
}