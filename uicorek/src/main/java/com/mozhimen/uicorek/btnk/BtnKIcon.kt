package com.mozhimen.uicorek.btnk

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.extsk.sp2px
import com.mozhimen.basicsk.basek.BaseKLayoutLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName BtnKIcon
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:06
 * @Version 1.0
 */
class BtnKIcon @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
    }

    private var background = R.drawable.btnk_icon_background
    private var iconResId: Int = 0
    private var iconSize = 50f.dp2px()
    private var iconMarginRight = 12f.dp2px()
    private var text: String? = null
    private var textColor = 0x000000
    private var textSize = 16f.sp2px()

    private lateinit var btnKIcon: ImageView
    private lateinit var btnKBox: LinearLayout
    private lateinit var btnKText: TextView

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnKIcon)
            background = typedArray.getResourceId(R.styleable.BtnKIcon_btnKIcon_background, background)
            iconResId = typedArray.getResourceId(R.styleable.BtnKIcon_btnKIcon_iconSrc, 0)
            iconSize = typedArray.getDimensionPixelOffset(R.styleable.BtnKIcon_btnKIcon_iconSize, iconSize)
            iconMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnKIcon_btnKIcon_iconMarginRight,
                    iconMarginRight
                )
            text = typedArray.getString(R.styleable.BtnKIcon_btnKIcon_text)
            textColor = typedArray.getColor(R.styleable.BtnKIcon_btnKIcon_textColor, textColor)
            textSize = typedArray.getDimensionPixelSize(R.styleable.BtnKIcon_btnKIcon_textSize, textSize)
            typedArray.recycle()
        }
    }

    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.btnk_icon, this)
        btnKIcon = findViewById(R.id.btnk_icon_img)
        btnKText = findViewById(R.id.btnk_icon_text)
        btnKBox = findViewById(R.id.btnk_icon_box)

        btnKBox.setBackgroundResource(background)
        if (iconResId != 0) {
            btnKIcon.visibility = View.VISIBLE
            btnKIcon.setImageResource(iconResId)
        } else {
            btnKIcon.visibility = View.GONE
        }
        val layoutParams = btnKIcon.layoutParams as LayoutParams
        layoutParams.width = iconSize
        layoutParams.height = iconSize
        layoutParams.setMargins(0, 0, iconMarginRight, 0)
        btnKIcon.layoutParams = layoutParams

        btnKText.text = text
        btnKText.setTextColor(textColor)
        btnKText.textSize = textSize.toFloat()
    }
}