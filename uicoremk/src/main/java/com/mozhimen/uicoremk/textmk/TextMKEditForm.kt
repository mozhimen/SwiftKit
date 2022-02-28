package com.mozhimen.uicoremk.textmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.layoutmk.commons.LayoutMKLinear

/**
 * @ClassName TextMKEditForm
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/27 16:17
 * @Version 1.0
 */
typealias OnHasFocus = (View, Boolean) -> Unit

class TextMKEditForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    LayoutMKLinear(context, attrs, defStyle) {

    private var mOnHasFocus: OnHasFocus? = null
    private var mIsRequire = false
    private var mRequireIconPos = 0
    private var mLabel: String? = null
    private var mLabelColor = Color.BLACK
    private var mLabelTextSize = 16f.sp2px()
    private var mLabelMarginLeft = 6f.dp2px()
    private var mLabelMarginRight = 6f.dp2px()
    private var mLabelWidth = 64f.dp2px()
    private var mEditHint: String? = null
    private var mEditType = 0
    private var mEditColor = Color.BLACK
    private var mEditSize = 16f.sp2px()
    private var mBorderBackground = R.drawable.textmk_edit_form
    private var mBorderBackgroundFocus = R.drawable.textmk_edit_form_focus

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mEditText: EditText

    init {
        initAttrs(attrs)
        initView()
        refreshView()
    }

    fun getIsRequire() = mIsRequire

    fun setIsRequire(isRequire: Boolean) {
        mIsRequire = isRequire
        mIsRequireIcon.visibility = if (mIsRequire) View.VISIBLE else View.INVISIBLE
    }

    fun setLabel(label: String) {
        mLabelText.text = label
    }

    fun getLabel(): String = mLabelText.text.toString()

    fun setContent(content: String) {
        mEditText.setText(content)
    }

    fun getContent(): String = mEditText.text.toString().trim()

    fun getEditView(): EditText {
        return this.mEditText
    }

    fun setOnFocusListener(listener: OnHasFocus) {
        this.mOnHasFocus = listener
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextMKEditForm)
            mIsRequire = typedArray.getBoolean(R.styleable.TextMKEditForm_textMKEditForm_isRequired, mIsRequire)
            mRequireIconPos =
                typedArray.getInteger(R.styleable.TextMKEditForm_textMKEditForm_isRequired, mRequireIconPos)
            mLabel = typedArray.getString(R.styleable.TextMKEditForm_textMKEditForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.TextMKEditForm_textMKEditForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.TextMKEditForm_textMKEditForm_labelColor, mLabelColor)
            mLabelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextMKEditForm_textMKEditForm_labelMarginLeft,
                    mLabelMarginLeft
                )
            mLabelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextMKEditForm_textMKEditForm_labelMarginRight,
                    mLabelMarginRight
                )
            mLabelWidth =
                typedArray.getDimensionPixelOffset(R.styleable.TextMKEditForm_textMKEditForm_labelWidth, mLabelWidth)
            mEditHint = typedArray.getString(R.styleable.TextMKEditForm_textMKEditForm_editHint)
            mEditType = typedArray.getInteger(R.styleable.TextMKEditForm_textMKEditForm_editType, mEditType)
            mEditColor = typedArray.getColor(R.styleable.TextMKEditForm_textMKEditForm_editColor, mEditColor)
            mEditSize = typedArray.getDimensionPixelSize(R.styleable.TextMKEditForm_textMKEditForm_editSize, mEditSize)
            mBorderBackground =
                typedArray.getResourceId(R.styleable.TextMKEditForm_textMKEditForm_borderBackground, mBorderBackground)
            mBorderBackgroundFocus = typedArray.getResourceId(
                R.styleable.TextMKEditForm_textMKEditForm_borderBackgroundHasFocus,
                mBorderBackgroundFocus
            )
            typedArray.recycle()
        }
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.textmk_edit_form, this)
        mLabelText = findViewById(R.id.textmk_edit_form_label)
        mEditText = findViewById(R.id.textmk_edit_form_edit)
        mIsRequireIcon = findViewById(R.id.textmk_edit_form_icon)
    }

    fun refreshView() {
        mIsRequireIcon.visibility = if (mIsRequire) View.VISIBLE else View.INVISIBLE
        val layoutParams = mIsRequireIcon.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.START or when (mRequireIconPos) {
            0 -> Gravity.TOP
            else -> Gravity.CENTER_VERTICAL
        }
        layoutParams.setMargins(mLabelMarginLeft, 0, mLabelMarginRight, 0)
        mIsRequireIcon.layoutParams = layoutParams
        val layoutParams1 = mLabelText.layoutParams
        layoutParams1.width = mLabelWidth
        mLabelText.layoutParams = layoutParams1
        mLabelText.text = mLabel ?: ""
        mLabelText.textSize = mLabelTextSize.toFloat()
        mLabelText.setTextColor(mLabelColor)
        mEditText.inputType = when (mEditType) {
            1 -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
            2 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            3 -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
        mEditText.hint = mEditHint ?: ""
        mEditText.textSize = mEditSize.toFloat()
        mEditText.setTextColor(mEditColor)
        mEditText.setBackgroundResource(mBorderBackground)
        mEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                mEditText.setBackgroundResource(mBorderBackgroundFocus)
            } else {
                mEditText.setBackgroundResource(mBorderBackground)
            }
            mOnHasFocus?.let { it(view, hasFocus) }
        }
    }
}