package com.mozhimen.uicorek.layoutk.blur

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IntRange
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKRelative
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.imagek.blur.ImageKBlurRenderScript
import com.mozhimen.basick.utilk.android.graphics.drawable2bitmap
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.blur.commons.ILayoutKBlur

/**
 * @ClassName BlurredLayout
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/5 16:18
 * @Version 1.0
 */
class LayoutKBlur @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseLayoutKRelative(context, attrs, defStyleAttr), ILayoutKBlur {

    //region #全局变量
    private var _isMoveEnable = false    //是否移动背景图片
    private var _isBlurEnable = false    //是否禁用模糊效果
    private var _drawableBlurred: Drawable? = null    //获得的模糊图片Drawable
    private var _bitmapBlurred: Bitmap? = null    //模糊的Bitmap
    private lateinit var _bitmapOrigin: Bitmap    //原图Bitmap
    private lateinit var _imageViewBlurred: ImageView    //模糊ImageView
    private lateinit var _imageViewOrigin: ImageView    //原图ImageView
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    //////////////////////////////////////////////////////////////////////////////////

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBlur)
        _drawableBlurred =
            typedArray.getDrawable(R.styleable.LayoutKBlur_layoutKBlur_imageSrc)
        _isMoveEnable =
            typedArray.getBoolean(R.styleable.LayoutKBlur_layoutKBlur_isMoveEnable, false)
        _isBlurEnable =
            typedArray.getBoolean(R.styleable.LayoutKBlur_layoutKBlur_isBlurEnable, true)
        typedArray.recycle()
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_blurred, this)
        _imageViewOrigin = findViewById(R.id.layoutk_img_origin)
        _imageViewBlurred = findViewById(R.id.layoutk_img_blurred)

        //模糊图片
        _drawableBlurred?.let {
            _bitmapOrigin = it.drawable2bitmap()
            _bitmapBlurred = ImageKBlurRenderScript.blurBitmapOfAndroid1(context, _bitmapOrigin)
        }

        //设置模糊禁用
        if (_isBlurEnable) _imageViewBlurred.visibility = VISIBLE
        //设置背景位移
        _drawableBlurred?.let {
            setMove(_isMoveEnable)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setImageView(_bitmapOrigin, _bitmapBlurred!!)
    }

    //////////////////////////////////////////////////////////////////////////////////

    override fun setBlurImageView(blurBitmap: Bitmap) {
        _bitmapOrigin = blurBitmap
        _bitmapBlurred = ImageKBlurRenderScript.blurBitmapOfAndroid1(context, blurBitmap)
        setImageView(_bitmapOrigin, _bitmapBlurred!!)
        setMove(_isMoveEnable)
    }

    override fun setBlurImageView(blurDrawable: Drawable) {
        _bitmapOrigin = blurDrawable.drawable2bitmap()
        _bitmapBlurred = ImageKBlurRenderScript.blurBitmapOfAndroid1(context, _bitmapOrigin)
        setImageView(_bitmapOrigin, _bitmapBlurred!!)
        setMove(_isMoveEnable)
    }

    @Throws(Exception::class)
    override fun setBlurLevel(@IntRange(from = 0, to = 100) level: Int) {
        require(level in 0..100) { "$TAG No validate level, the value must be 0~100" }
        if (_isBlurEnable) {
            _imageViewOrigin.imageAlpha = (255f - level * 2.55).toInt()
        }
    }

    override fun setBlurOffset(offset: Int) {
        _imageViewOrigin.top = -offset
        _imageViewBlurred.top = -offset
    }

    override fun showBlurImageView() {
        _imageViewBlurred.visibility = VISIBLE
    }

    override fun setBlurEnable(enable: Boolean) {
        if (enable) {
            _isBlurEnable = false
            _imageViewBlurred.visibility = VISIBLE
        } else {
            _isBlurEnable = true
            _imageViewOrigin.alpha = 1f
            _imageViewBlurred.visibility = INVISIBLE
        }
    }

    private fun setMove(isMove: Boolean) {//设置背景图片的移动效果
        if (isMove) {
            val height = UtilKScreen.getRealHeight()
            setBlurredHeight(height, _imageViewOrigin)
            setBlurredHeight(height, _imageViewBlurred)
        }
    }

    private fun setBlurredHeight(height: Int, imageView: ImageView) {//改变图片的高度
        val layoutParams: ViewGroup.LayoutParams = imageView.layoutParams
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = height + 100
        imageView.requestLayout()
    }

    private fun setImageView(originBitmap: Bitmap, blurredBitmap: Bitmap) {
        _imageViewOrigin.setImageBitmap(originBitmap)
        _imageViewBlurred.setImageBitmap(blurredBitmap)//填充ImageView
    }
}