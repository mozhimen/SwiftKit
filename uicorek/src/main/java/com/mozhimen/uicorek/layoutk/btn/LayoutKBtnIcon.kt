package com.mozhimen.uicorek.layoutk.btn

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.basick.utilk.res.dp2px
import com.mozhimen.basick.utilk.res.sp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKBtnIcon
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:06
 * @Version 1.0
 */
class LayoutKBtnIcon @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLayoutKLinear(context, attrs, defStyleAttr) {

    private var _background = R.drawable.layoutk_btn_icon_background
    private var _iconResId: Int = 0
    private var _iconSize = 50f.dp2px().toInt()
    private var _iconMarginRight = 12f.dp2px().toInt()
    private var _text: String? = null
    private var _textColor = 0x000000
    private var _textSize = 15f.sp2px()

    private lateinit var _btnIcon: ImageView
    private lateinit var _btnBox: LinearLayout
    private lateinit var _btnText: TextView

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKBtnIcon)
        _background =
            typedArray.getResourceId(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_background, _background)
        _iconResId =
            typedArray.getResourceId(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_iconSrc, 0)
        _iconSize =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_iconSize, _iconSize)
        _iconMarginRight =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_iconMarginRight, _iconMarginRight)
        _text =
            typedArray.getString(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_text)
        _textColor =
            typedArray.getColor(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_textColor, _textColor)
        _textSize =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKBtnIcon_layoutKBtnIcon_textSize, _textSize.toInt()).toFloat()
        typedArray.recycle()
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layoutk_btn_icon, this)
        _btnIcon = findViewById(R.id.btnk_icon_img)
        _btnText = findViewById(R.id.btnk_icon_text)
        _btnBox = findViewById(R.id.btnk_icon_box)

        _btnBox.setBackgroundResource(_background)
        if (_iconResId != 0) {
            _btnIcon.visibility = View.VISIBLE
            _btnIcon.setImageResource(_iconResId)
        } else {
            _btnIcon.visibility = View.GONE
        }
        val layoutParams = _btnIcon.layoutParams as LayoutParams
        layoutParams.width = _iconSize
        layoutParams.height = _iconSize
        layoutParams.setMargins(0, 0, _iconMarginRight, 0)
        _btnIcon.layoutParams = layoutParams

        _btnText.text = _text
        _btnText.setTextColor(_textColor)
        _btnText.textSize = _textSize
    }
}