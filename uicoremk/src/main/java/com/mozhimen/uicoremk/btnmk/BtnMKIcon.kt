package com.mozhimen.uicoremk.btnmk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.layoutmk.commons.LayoutMKLinear

/**
 * @ClassName BtnMKIcon
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:06
 * @Version 1.0
 */
class BtnMKIcon @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutMKLinear(context, attrs, defStyleAttr) {

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    private var background = R.drawable.btnmk_icon_background
    private var iconResId: Int = 0
    private var iconSize = 50f.dp2px()
    private var iconMarginRight = 12f.dp2px()
    private var text: String? = null
    private var textColor = 0x000000
    private var textSize = 16f.sp2px()

    private lateinit var btnMKIcon: ImageView
    private lateinit var btnMKBox: LinearLayout
    private lateinit var btnMKText: TextView

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnMKIcon)
            background = typedArray.getResourceId(R.styleable.BtnMKIcon_btnMKIcon_background, background)
            iconResId = typedArray.getResourceId(R.styleable.BtnMKIcon_btnMKIcon_iconSrc, 0)
            iconSize = typedArray.getDimensionPixelOffset(R.styleable.BtnMKIcon_btnMKIcon_iconSize, iconSize)
            iconMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnMKIcon_btnMKIcon_iconMarginRight,
                    iconMarginRight
                )
            text = typedArray.getString(R.styleable.BtnMKIcon_btnMKIcon_text)
            textColor = typedArray.getColor(R.styleable.BtnMKIcon_btnMKIcon_textColor, textColor)
            textSize = typedArray.getDimensionPixelSize(R.styleable.BtnMKIcon_btnMKIcon_textSize, textSize)
            typedArray.recycle()
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.btnmk_icon, this)
        btnMKIcon = findViewById(R.id.btnmk_icon_img)
        btnMKText = findViewById(R.id.btnmk_icon_text)
        btnMKBox = findViewById(R.id.btnmk_icon_box)

        btnMKBox.setBackgroundResource(background)
        if (iconResId != 0) {
            btnMKIcon.visibility = View.VISIBLE
            btnMKIcon.setImageResource(iconResId)
        } else {
            btnMKIcon.visibility = View.GONE
        }
        val layoutParams = btnMKIcon.layoutParams as LayoutParams
        layoutParams.width = iconSize
        layoutParams.height = iconSize
        layoutParams.setMargins(0, 0, iconMarginRight, 0)
        btnMKIcon.layoutParams = layoutParams

        btnMKText.text = text
        btnMKText.setTextColor(textColor)
        btnMKText.textSize = textSize.toFloat()
    }
}