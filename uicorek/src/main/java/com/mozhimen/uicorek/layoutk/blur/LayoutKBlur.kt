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
import com.mozhimen.basick.imagek.blur.ImageKBlur
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
    private var _blurredDrawable: Drawable? = null    //获得的模糊图片Drawable
    private var _blurredBitmap: Bitmap? = null    //模糊的Bitmap
    private lateinit var _originBitmap: Bitmap    //原图Bitmap
    private lateinit var _blurredImageView: ImageView    //模糊ImageView
    private lateinit var _originImageView: ImageView    //原图ImageView
    //endregion

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    override fun setBlurImageView(blurBitmap: Bitmap) {
        _originBitmap = blurBitmap
        _blurredBitmap = ImageKBlur.blurBitmap(blurBitmap)
        setImageView(_originBitmap, _blurredBitmap!!)
        setMove(_isMoveEnable)
    }

    override fun setBlurImageView(blurDrawable: Drawable) {
        _originBitmap = blurDrawable.drawable2bitmap()
        _blurredBitmap = ImageKBlur.blurBitmap(_originBitmap)
        setImageView(_originBitmap, _blurredBitmap!!)
        setMove(_isMoveEnable)
    }

    @Throws(Exception::class)
    override fun setBlurLevel(@IntRange(from = 0, to = 100) level: Int) {
        require(level in 0..100) { "$TAG No validate level, the value must be 0~100" }
        if (_isBlurEnable) {
            _originImageView.imageAlpha = (255f - level * 2.55).toInt()
        }
    }

    override fun setBlurOffset(offset: Int) {
        _originImageView.top = -offset
        _blurredImageView.top = -offset
    }

    override fun showBlurImageView() {
        _blurredImageView.visibility = VISIBLE
    }

    override fun setBlurEnable(enable: Boolean) {
        if (enable) {
            _isBlurEnable = false
            _blurredImageView.visibility = VISIBLE
        } else {
            _isBlurEnable = true
            _originImageView.alpha = 1f
            _blurredImageView.visibility = INVISIBLE
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_blurred, this)
        _originImageView = findViewById(R.id.layoutk_img_origin)
        _blurredImageView = findViewById(R.id.layoutk_img_blurred)

        //模糊图片
        _blurredDrawable?.let {
            _originBitmap = it.drawable2bitmap()
            _blurredBitmap = ImageKBlur.blurBitmap(_originBitmap)
        }

        //设置模糊禁用
        if (_isBlurEnable) _blurredImageView.visibility = VISIBLE
        //设置背景位移
        _blurredDrawable?.let {
            setMove(_isMoveEnable)
        }
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBlur)
        _blurredDrawable =
            typedArray.getDrawable(R.styleable.LayoutKBlur_layoutKBlur_imageSrc)
        _isMoveEnable =
            typedArray.getBoolean(R.styleable.LayoutKBlur_layoutKBlur_isMoveEnable, false)
        _isBlurEnable =
            typedArray.getBoolean(R.styleable.LayoutKBlur_layoutKBlur_isBlurEnable, true)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setImageView(_originBitmap, _blurredBitmap!!)
    }

    private fun setMove(isMove: Boolean) {//设置背景图片的移动效果
        if (isMove) {
            val height = UtilKScreen.getRealHeight()
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