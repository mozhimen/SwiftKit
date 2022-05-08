package com.mozhimen.uicorek.textk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.extsk.sp2px
import com.mozhimen.basicsk.basek.BaseKLayoutLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName TextKEditForm
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/27 16:17
 * @Version 1.0
 */
typealias OnHasFocus = (View, Boolean) -> Unit

class TextKEditForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

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
    private var mBorderBackground = R.drawable.textk_edit_form
    private var mBorderBackgroundFocus = R.drawable.textk_edit_form_focus

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mEditText: EditText

    init {
        initAttrs(attrs,defStyleAttr)
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
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKEditForm)
            mIsRequire = typedArray.getBoolean(R.styleable.TextKEditForm_textKEditForm_isRequired, mIsRequire)
            mRequireIconPos =
                typedArray.getInteger(R.styleable.TextKEditForm_textKEditForm_isRequired, mRequireIconPos)
            mLabel = typedArray.getString(R.styleable.TextKEditForm_textKEditForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.TextKEditForm_textKEditForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.TextKEditForm_textKEditForm_labelColor, mLabelColor)
            mLabelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextKEditForm_textKEditForm_labelMarginLeft,
                    mLabelMarginLeft
                )
            mLabelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextKEditForm_textKEditForm_labelMarginRight,
                    mLabelMarginRight
                )
            mLabelWidth =
                typedArray.getDimensionPixelOffset(R.styleable.TextKEditForm_textKEditForm_labelWidth, mLabelWidth)
            mEditHint = typedArray.getString(R.styleable.TextKEditForm_textKEditForm_editHint)
            mEditType = typedArray.getInteger(R.styleable.TextKEditForm_textKEditForm_editType, mEditType)
            mEditColor = typedArray.getColor(R.styleable.TextKEditForm_textKEditForm_editColor, mEditColor)
            mEditSize = typedArray.getDimensionPixelSize(R.styleable.TextKEditForm_textKEditForm_editSize, mEditSize)
            mBorderBackground =
                typedArray.getResourceId(R.styleable.TextKEditForm_textKEditForm_borderBackground, mBorderBackground)
            mBorderBackgroundFocus = typedArray.getResourceId(
                R.styleable.TextKEditForm_textKEditForm_borderBackgroundHasFocus,
                mBorderBackgroundFocus
            )
            typedArray.recycle()
        }
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.textk_edit_form, this)
        mLabelText = findViewById(R.id.textk_edit_form_label)
        mEditText = findViewById(R.id.textk_edit_form_edit)
        mIsRequireIcon = findViewById(R.id.textk_edit_form_icon)
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