package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import com.mozhimen.basick.basek.BaseKLayoutLinear
import com.mozhimen.basick.extsk.setInputMaxLength
import com.mozhimen.basick.extsk.setPadding
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKInputItem
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/18 12:56
 * @Version 1.0
 */
class LayoutKInputItem @JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private lateinit var _editView: EditText
    private lateinit var _titleView: TextView
    private var _topCrossLine: CrossLine? = null
    private var _bottomCrossLine: CrossLine? = null
    private var _topLinePaint = Paint()
    private var _bottomLinePaint = Paint()

    init {
        dividerDrawable = ColorDrawable()
        showDividers = SHOW_DIVIDER_BEGINNING
        orientation = HORIZONTAL

        initAttrs(attrs, defStyleAttr)
        initPaint()
    }

    fun getTitleView(): TextView = _titleView

    fun getInputView(): EditText = _editView

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKInputItem)

        val titleResId =
            typedArray.getResourceId(R.styleable.LayoutKInputItem_layoutKInputItem_titleAppearance, 0)
        val title =
            typedArray.getString(R.styleable.LayoutKInputItem_layoutKInputItem_title)
        parseTitleStyle(titleResId, title)

        val editResId =
            typedArray.getResourceId(R.styleable.LayoutKInputItem_layoutKInputItem_inputAppearance, 0)
        val hint =
            typedArray.getString(R.styleable.LayoutKInputItem_layoutKInputItem_hint)
        val inputType =
            typedArray.getInteger(R.styleable.LayoutKInputItem_layoutKInputItem_inputType, 0)
        parseInputStyle(editResId, hint, inputType)

        val topResId =
            typedArray.getResourceId(R.styleable.LayoutKInputItem_layoutKInputItem_topLineAppearance, 0)
        val bottomResId =
            typedArray.getResourceId(R.styleable.LayoutKInputItem_layoutKInputItem_bottomLineAppearance, 0)
        _topCrossLine = parseLineStyle(topResId)
        _bottomCrossLine = parseLineStyle(bottomResId)

        typedArray.recycle()
    }

    private fun initPaint() {
        _topCrossLine?.let {
            _topLinePaint.color = _topCrossLine!!.color
            _topLinePaint.style = Paint.Style.FILL_AND_STROKE
            _topLinePaint.strokeWidth = _topCrossLine!!.height
        }

        _bottomCrossLine?.let {
            _bottomLinePaint.color = _bottomCrossLine!!.color
            _bottomLinePaint.style = Paint.Style.FILL_AND_STROKE
            _bottomLinePaint.strokeWidth = _bottomCrossLine!!.height
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            _topCrossLine?.let {
                canvas.drawRect(it.leftMargin, 0f, measuredWidth - it.rightMargin, 0f + it.height, _topLinePaint)
            }

            _bottomCrossLine?.let {
                canvas.drawRect(
                    it.leftMargin, measuredHeight.toFloat() - it.height, measuredWidth - it.rightMargin, measuredHeight.toFloat(), _bottomLinePaint
                )
            }
        }
    }

    inner class CrossLine {
        var color = 0
        var height = 0f
        var leftMargin = 0f
        var rightMargin = 0f
    }

    @SuppressLint("CustomViewStyleable")
    private fun parseTitleStyle(resId: Int, title: String?) {
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutKInputItem_TitleAppearance)
        val titleColor: Int =
            typedArray.getColor(R.styleable.LayoutKInputItem_TitleAppearance_titleAppearance_titleColor, UtilKRes.getColor(R.color.blue_dark))
        val titleSize: Float =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKInputItem_TitleAppearance_titleAppearance_titleSize, 15f.sp2px()).toFloat()
        val leftMargin =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKInputItem_TitleAppearance_titleAppearance_leftPadding, 0)
        val titleMinWidth: Int =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKInputItem_TitleAppearance_titleAppearance_minWidth, 0)
        typedArray.recycle()

        _titleView = TextView(context)
        _titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        _titleView.setTextColor(titleColor)
        _titleView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        _titleView.setPadding(leftMargin, 0, 0, 0)
        _titleView.minWidth = titleMinWidth
        _titleView.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        _titleView.setBackgroundColor(Color.TRANSPARENT)
        _titleView.text = title
        addView(_titleView)
    }

    @SuppressLint("CustomViewStyleable")
    fun parseInputStyle(resId: Int, hint: String?, inputType: Int) {

        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutKInputItem_InputAppearance)
        val hintColor: Int =
            typedArray.getColor(R.styleable.LayoutKInputItem_InputAppearance_inputAppearance_hintColor, UtilKRes.getColor(R.color.blue_light))
        val inputColor: Int =
            typedArray.getColor(
                R.styleable.LayoutKInputItem_InputAppearance_inputAppearance_inputColor, UtilKRes.getColor(R.color.blue_normal)
            )
        val inputSize: Int =
            typedArray.getDimensionPixelSize(R.styleable.LayoutKInputItem_InputAppearance_inputAppearance_textSize, 15f.sp2px())
        val inputMaxLength: Int =
            typedArray.getInteger(R.styleable.LayoutKInputItem_InputAppearance_inputAppearance_maxInputLength, 0)
        typedArray.recycle()

        _editView = EditText(context)
        _editView.setInputMaxLength(inputMaxLength)
        _editView.setPadding(0, 0)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        _editView.layoutParams = params

        _editView.hint = hint
        _editView.setTextColor(inputColor)
        _editView.setHintTextColor(hintColor)
        _editView.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        _editView.setBackgroundColor(Color.TRANSPARENT)
        _editView.setTextSize(TypedValue.COMPLEX_UNIT_PX, inputSize.toFloat())

        _editView.inputType = when (inputType) {
            1 -> InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT//name="password" value="1"
            2 -> InputType.TYPE_CLASS_NUMBER//name="number" value="2"
            else -> InputType.TYPE_CLASS_TEXT//name="text" value="0"
        }

        addView(_editView)
    }

    @SuppressLint("CustomViewStyleable")
    fun parseLineStyle(resId: Int): CrossLine? {
        if (resId == 0) return null
        val line = CrossLine()
        val typedArray = context.obtainStyledAttributes(resId, R.styleable.LayoutKInputItem_LineAppearance)
        line.color =
            typedArray.getColor(R.styleable.LayoutKInputItem_LineAppearance_lineAppearance_color, UtilKRes.getColor(R.color.blue_normal))
        line.height =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKInputItem_LineAppearance_lineAppearance_height, 0).toFloat()
        line.leftMargin =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKInputItem_LineAppearance_lineAppearance_leftMargin, 0).toFloat()
        line.rightMargin =
            typedArray.getDimensionPixelOffset(R.styleable.LayoutKInputItem_LineAppearance_lineAppearance_rightMargin, 0).toFloat()

        typedArray.recycle()
        return line
    }
}