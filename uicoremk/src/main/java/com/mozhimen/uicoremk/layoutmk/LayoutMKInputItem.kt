package com.mozhimen.uicoremk.layoutmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.layoutmk.commons.LayoutMKLinear

/**
 * @ClassName LayoutMKInputItem
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/18 12:56
 * @Version 1.0
 */
open class LayoutMKInputItem(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    LayoutMKLinear(context, attrs, defStyleAttr) {

    private lateinit var editText: EditText
    private lateinit var titleView: TextView

    private var bottomLine: Line
    private var topLine: Line

    private var topPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutMKInputItem)

        val titleResourceId =
            typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_titleTextAppearance, 0)
        val title = typedArray.getString(R.styleable.LayoutMKInputItem_layoutMKInputItem_title)
        parseTitleStyle(titleResourceId, title)

        val inputStyleId = typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_inputTextAppearance, 0)
        val hint = typedArray.getString(R.styleable.LayoutMKInputItem_layoutMKInputItem_hint)
        val inputType = typedArray.getInteger(R.styleable.LayoutMKInputItem_layoutMKInputItem_inputType, 0)
        parseInputStyle(inputStyleId, hint, inputType)

        val topLineStyleId = typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_topLineAppearance, 0)
        val bottomLineStyleId =
            typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_bottomLineAppearance, 0)

        parseLineStyle(topLineStyleId)
        parseLineStyle(bottomLineStyleId)

        //上下分割线属性
        val topResId = typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_topLineAppearance, 0)
        val bottomResId = typedArray.getResourceId(R.styleable.LayoutMKInputItem_layoutMKInputItem_bottomLineAppearance, 0)
        topLine = parseLineStyle(topResId)
        bottomLine = parseLineStyle(bottomResId)

        typedArray.recycle()

        initPaint()
    }

    fun getTitleView(): TextView {
        return titleView
    }

    fun getEditText(): EditText {
        return editText
    }

    private fun initPaint() {
        if (topLine.enable) {
            topPaint.color = topLine.color
            topPaint.style = Paint.Style.FILL_AND_STROKE
            topPaint.strokeWidth = topLine.height
        }

        if (bottomLine.enable) {
            bottomPaint.color = bottomLine.color
            bottomPaint.style = Paint.Style.FILL_AND_STROKE
            bottomPaint.strokeWidth = bottomLine.height
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun parseLineStyle(resId: Int): Line {
        val line = Line()
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutMKInputItemLineAppearance)
        line.color =
            typedArray.getColor(
                R.styleable.LayoutMKInputItemLineAppearance_layoutMKInputItem_color,
                ContextCompat.getColor(context, R.color.blue_light)
            )
        line.height =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutMKInputItemLineAppearance_layoutMKInputItem_height, 0)
                .toFloat()
        line.leftMargin =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutMKInputItemLineAppearance_layoutMKInputItem_leftMargin, 0)
                .toFloat()
        line.rightMargin =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutMKInputItemLineAppearance_layoutMKInputItem_rightMargin, 0)
                .toFloat()
        line.enable = typedArray.getBoolean(R.styleable.LayoutMKInputItemLineAppearance_layoutMKInputItem_enable, false)

        typedArray.recycle()
        return line
    }

    inner class Line {
        var color = 0
        var height = 0f
        var leftMargin = 0f
        var rightMargin = 0f
        var enable: Boolean = false
    }

    //解析右侧输入框资源属性
    @SuppressLint("CustomViewStyleable")
    private fun parseInputStyle(resId: Int, hint: String?, inputType: Int) {
        //去加载 去读取 自定义sytle属性
        orientation = HORIZONTAL

        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutMKInputItemInputTextAppearance)

        val hintColor = typedArray.getColor(
            R.styleable.LayoutMKInputItemInputTextAppearance_layoutMKInputItem_hintColor,
            ContextCompat.getColor(context, R.color.blue_light)

        )
        val inputColor = typedArray.getColor(
            R.styleable.LayoutMKInputItemInputTextAppearance_layoutMKInputItem_inputColor,
            ContextCompat.getColor(context, R.color.blue_normal)
        )
        //px
        val textSize = typedArray.getDimensionPixelSize(
            R.styleable.LayoutMKInputItemInputTextAppearance_layoutMKInputItem_textSize,
            15f.sp2px().toInt()
        )

        val maxInputLength =
            typedArray.getInteger(R.styleable.LayoutMKInputItemInputTextAppearance_layoutMKInputItem_maxInputLength, 0)

        editText = EditText(context)
        if (maxInputLength > 0) {
            editText.filters = arrayOf(InputFilter.LengthFilter(maxInputLength))//最多可输入的字符数
        }
        editText.setPadding(0, 0, 0, 0)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        editText.layoutParams = params

        editText.hint = hint
        editText.setTextColor(inputColor)
        editText.setHintTextColor(hintColor)
        editText.gravity = Gravity.LEFT or (Gravity.CENTER)
        editText.setBackgroundColor(Color.TRANSPARENT)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())

        /**
         * <enum name="text" value="0"></enum>
         * <enum name="password" value="1"></enum>
         * <enum name="number" value="2"></enum>
         */
        if (inputType == 0) {
            editText.inputType = InputType.TYPE_CLASS_TEXT
        } else if (inputType == 1) {
            editText.inputType =
                InputType.TYPE_TEXT_VARIATION_PASSWORD or (InputType.TYPE_CLASS_TEXT)
        } else if (inputType == 2) {
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }

        addView(editText)
        typedArray.recycle()
    }

    //解析title资源属性
    @SuppressLint("CustomViewStyleable")
    private fun parseTitleStyle(resId: Int, title: String?) {
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutMKInputItemTitleTextAppearance)
        val titleColor = typedArray.getColor(
            R.styleable.LayoutMKInputItemTitleTextAppearance_layoutMKInputItem_titleColor,
            ContextCompat.getColor(context, R.color.blue_dark)
        )
        val titleSize = typedArray.getDimensionPixelSize(
            R.styleable.LayoutMKInputItemTitleTextAppearance_layoutMKInputItem_titleSize,
            15f.sp2px().toInt()
        )
        val minWidth = typedArray.getDimensionPixelOffset(R.styleable.LayoutMKInputItemTitleTextAppearance_layoutMKInputItem_minWidth, 0)

        titleView = TextView(context)
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())  //sp---当做sp在转换一次
        titleView.setTextColor(titleColor)
        titleView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        titleView.minWidth = minWidth
        titleView.gravity = Gravity.LEFT or (Gravity.CENTER)
        titleView.text = title

        addView(titleView)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            //巨坑
            if (topLine.enable) {
                canvas.drawLine(
                    topLine.leftMargin,
                    0f,
                    measuredWidth - topLine.rightMargin,
                    0f,
                    topPaint
                )
            }

            if (bottomLine.enable) {
                canvas.drawLine(
                    bottomLine.leftMargin,
                    height - bottomLine.height,
                    measuredWidth - bottomLine.rightMargin,
                    height - bottomLine.height,
                    bottomPaint
                )
            }
        }
    }
}