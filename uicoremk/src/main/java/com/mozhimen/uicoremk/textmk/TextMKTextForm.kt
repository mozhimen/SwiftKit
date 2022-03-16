package com.mozhimen.uicoremk.textmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.layoutmk.commons.LayoutMKLinear

/**
 * @ClassName TextMKTextForm
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/16 0:16
 * @Version 1.0
 */
typealias OnFormClick = (View) -> Unit

class TextMKTextForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutMKLinear(context, attrs, defStyleAttr) {

    private var mTextHint: String? = null
    private var mOnFormClick: OnFormClick? = null
    private var mIsRequire = false
    private var mRequiredIconPos = 0
    private var mLabel: String? = null
    private var mLabelColor = Color.BLACK
    private var mLabelTextSize = 16f.sp2px()
    private var mLabelWidth = 64f.dp2px()
    private var mLabelMarginLeft = 6f.dp2px()
    private var mLabelMarginRight = 6f.dp2px()
    private var mTextColor = Color.BLACK
    private var mTextSize = 18f.sp2px()
    private var mBorderBackground = R.drawable.textmk_edit_form

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mTextView: TextView

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
        mTextView.text = content
    }

    fun getContent(): String = mTextView.text.toString().trim()

    fun setOnFormClickListener(listener: OnFormClick) {
        this.mOnFormClick = listener
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextMKTextForm)
            mIsRequire = typedArray.getBoolean(R.styleable.TextMKTextForm_textMKTextForm_isRequired, mIsRequire)
            mRequiredIconPos =
                typedArray.getInteger(R.styleable.TextMKTextForm_textMKTextForm_requiredIconPos, mRequiredIconPos)
            mLabel = typedArray.getString(R.styleable.TextMKTextForm_textMKTextForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.TextMKTextForm_textMKTextForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.TextMKTextForm_textMKTextForm_labelColor, mLabelColor)
            mLabelMarginLeft = typedArray.getDimensionPixelOffset(
                R.styleable.TextMKTextForm_textMKTextForm_labelMarginLeft,
                mLabelMarginLeft
            )
            mLabelMarginRight = typedArray.getDimensionPixelOffset(
                R.styleable.TextMKTextForm_textMKTextForm_labelMarginRight,
                mLabelMarginRight
            )
            mLabelWidth =
                typedArray.getDimensionPixelOffset(R.styleable.TextMKTextForm_textMKTextForm_labelWidth, mLabelWidth)
            mTextHint = typedArray.getString(R.styleable.TextMKTextForm_textMKTextForm_textHint)
            mTextColor = typedArray.getColor(R.styleable.TextMKTextForm_textMKTextForm_textColor, mTextColor)
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextMKTextForm_textMKTextForm_textSize, mTextSize)
            mBorderBackground =
                typedArray.getResourceId(R.styleable.TextMKTextForm_textMKTextForm_borderBackground, mBorderBackground)
            typedArray.recycle()
        }
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.textmk_text_form, this)
        mIsRequireIcon = findViewById(R.id.textmk_text_form_icon)
        mLabelText = findViewById(R.id.textmk_text_form_label)
        mTextView = findViewById(R.id.textmk_text_form_text)
    }

    fun refreshView() {
        mIsRequireIcon.visibility = if (mIsRequire) View.VISIBLE else View.INVISIBLE
        val layoutParams = mIsRequireIcon.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.START or when (mRequiredIconPos) {
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
        mTextHint?.let { mTextView.text = it }
        mTextView.textSize = mTextSize.toFloat()
        mTextView.setTextColor(mTextColor)
        mTextView.setBackgroundResource(mBorderBackground)
        mTextView.setOnClickListener { view ->
            mOnFormClick?.let { it(view) }
        }
    }
}