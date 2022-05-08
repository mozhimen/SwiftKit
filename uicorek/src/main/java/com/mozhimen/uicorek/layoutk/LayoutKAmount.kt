package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.extsk.sp2px
import com.mozhimen.basicsk.basek.commons.IBaseKLayout
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKAmount
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/3/8 16:23
 * @Version 1.0
 */

typealias LayoutKAmountListener = (Int) -> Unit

class LayoutKAmount @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), IBaseKLayout {

    private var _layoutKAmountListener: LayoutKAmountListener? = null
    private val _attrs = AttrsParser.parseAttrs(context, attrs, defStyleAttr)
    private var _amountValue = _attrs.numDefaultValue

    init {
        initView()
    }

    fun getAmountValue() = _amountValue

    fun setAmountValueChangedListener(layoutKAmountListener: LayoutKAmountListener) {
        this._layoutKAmountListener = layoutKAmountListener
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun initView() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        applyAttrs()
    }

    private fun applyAttrs() {
        val increaseBtn = generateBtn()
        increaseBtn.text = "+"

        val decreaseBtn = generateBtn()
        decreaseBtn.text = "-"

        val amountView = generateAmountTxt()
        amountView.text = _amountValue.toString()

        addView(increaseBtn)
        addView(decreaseBtn)
        addView(amountView)

        decreaseBtn.isEnabled = _amountValue > _attrs.numMinValue
        increaseBtn.isEnabled = _amountValue < _attrs.numMaxValue

        decreaseBtn.setOnClickListener {
            _amountValue--
            amountView.text = _amountValue.toString()
            amountView.isEnabled = _amountValue > _attrs.numMinValue
            increaseBtn.isEnabled = true
            _layoutKAmountListener?.invoke(_amountValue)
        }

        increaseBtn.setOnClickListener {
            _amountValue++
            amountView.text = _amountValue.toString()
            increaseBtn.isEnabled = _amountValue < _attrs.numMaxValue
            decreaseBtn.isEnabled = true
            _layoutKAmountListener?.invoke(_amountValue)
        }
    }

    private fun generateAmountTxt(): TextView {
        val textView = TextView(context)
        textView.setPadding(0)
        textView.setTextColor(_attrs.numTextColor)
        textView.setBackgroundColor(_attrs.numBackground)
        textView.gravity = Gravity.CENTER
        textView.includeFontPadding = false

        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        layoutParams.leftMargin = _attrs.margin
        layoutParams.rightMargin = _attrs.margin
        textView.layoutParams = layoutParams
        textView.minWidth = _attrs.numSize
        return textView
    }

    private fun generateBtn(): Button {
        val button = Button(context)
        button.setBackgroundResource(0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) button.elevation = 0f
        button.setPadding(0)
        button.includeFontPadding = false
        button.setTextColor(_attrs.btnTextColor)
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, _attrs.btnTextSize)
        button.setBackgroundColor(_attrs.btnBackground)
        button.gravity = Gravity.CENTER
        button.layoutParams = LayoutParams(_attrs.btnSize, _attrs.btnSize)
        return button
    }

    private object AttrsParser {
        fun parseAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attrs {
            val array = context.obtainStyledAttributes(
                attrs, R.styleable.LayoutKAmount, defStyleAttr, R.style.LayoutKAmountStyle
            )

            val btnTextSize = array.getDimensionPixelSize(
                R.styleable.LayoutKAmount_layoutKAmount_btn_textSize, 14f.sp2px()
            )
            val btnTextColor = array.getColorStateList(
                R.styleable.LayoutKAmount_layoutKAmount_btn_textColor
            )
            val btnSize = array.getDimensionPixelSize(
                R.styleable.LayoutKAmount_layoutKAmount_btn_size, 20f.dp2px()
            )
            val margin = array.getDimensionPixelOffset(
                R.styleable.LayoutKAmount_layoutKAmount_margin, 0
            )
            val btnBackground = array.getColor(
                R.styleable.LayoutKAmount_layoutKAmount_btn_background, Color.parseColor("#eeeeee")
            )
            val numTextSize = array.getDimensionPixelSize(
                R.styleable.LayoutKAmount_layoutKAmount_num_textSize, 14f.sp2px()
            )
            val numTextColor = array.getColor(
                R.styleable.LayoutKAmount_layoutKAmount_num_textColor, Color.BLACK
            )
            val numSize = array.getDimensionPixelSize(
                R.styleable.LayoutKAmount_layoutKAmount_num_size, 20f.dp2px()
            )
            val numBackground = array.getColor(
                R.styleable.LayoutKAmount_layoutKAmount_num_background, Color.WHITE
            )
            val numDefaultValue = array.getInteger(
                R.styleable.LayoutKAmount_layoutKAmount_num_defaultValue, 1
            )
            val numMinValue = array.getInteger(
                R.styleable.LayoutKAmount_layoutKAmount_num_minValue, 1
            )
            val numMaxValue = array.getInteger(
                R.styleable.LayoutKAmount_layoutKAmount_num_maxValue, Int.MAX_VALUE
            )

            array.recycle()

            return Attrs(
                btnTextSize.toFloat(),
                btnTextColor,
                btnSize,
                btnBackground,
                margin,
                numTextSize.toFloat(),
                numTextColor,
                numSize,
                numBackground,
                numDefaultValue,
                numMinValue,
                numMaxValue
            )
        }
    }

    private data class Attrs(
        val btnTextSize: Float,
        val btnTextColor: ColorStateList?,
        val btnSize: Int,
        val btnBackground: Int,
        val margin: Int,
        val numTextSize: Float,
        val numTextColor: Int,
        val numSize: Int,
        val numBackground: Int,
        val numDefaultValue: Int,
        val numMinValue: Int,
        val numMaxValue: Int
    )
}