package com.mozhimen.uicorek.layoutk.text

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
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.sp2px
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName TextKTextForm
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/16 0:16
 * @Version 1.0
 */
typealias ILayoutKTextFormListener = (view: View) -> Unit

class LayoutKTextForm @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    BaseLayoutKLinear(context, attrs, defStyleAttr) {

    private var _textHint: String? = null
    private var _onFormClick: ILayoutKTextFormListener? = null
    private var _isRequire = false
    private var _requiredIconPos = 0
    private var _label: String? = null
    private var _labelColor = Color.BLACK
    private var _labelTextSize = 15f.sp2px()
    private var _labelWidth = 64f.dp2px()
    private var _labelMarginLeft = 6f.dp2px()
    private var _labelMarginRight = 6f.dp2px()
    private var _textColor = Color.BLACK
    private var _textSize = 15f.sp2px()
    private var _borderBackground = R.drawable.textk_edit_form

    private lateinit var _labelText: TextView
    private lateinit var _isRequireIcon: ImageView
    private lateinit var _textView: TextView

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
        refreshView()
    }

    fun getIsRequire() = _isRequire

    fun setIsRequire(isRequire: Boolean) {
        _isRequire = isRequire
        _isRequireIcon.visibility = if (_isRequire) View.VISIBLE else View.INVISIBLE
    }

    fun setLabel(label: String) {
        _labelText.text = label
    }

    fun getLabel(): String = _labelText.text.toString()

    fun setContent(content: String) {
        _textView.text = content
    }

    fun getContent(): String = _textView.text.toString().trim()

    fun setOnFormClickListener(listener: ILayoutKTextFormListener) {
        this._onFormClick = listener
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKForm)
            _isRequire = typedArray.getBoolean(R.styleable.TextKForm_textKForm_isRequired, _isRequire)
            _requiredIconPos =
                typedArray.getInteger(R.styleable.TextKForm_textKForm_requiredIconPos, _requiredIconPos)
            _label = typedArray.getString(R.styleable.TextKForm_textKForm_label)
            _labelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.TextKForm_textKForm_labelTextSize,
                _labelTextSize
            )
            _labelColor = typedArray.getColor(R.styleable.TextKForm_textKForm_labelColor, _labelColor)
            _labelMarginLeft = typedArray.getDimensionPixelOffset(
                R.styleable.TextKForm_textKForm_labelMarginLeft,
                _labelMarginLeft
            )
            _labelMarginRight = typedArray.getDimensionPixelOffset(
                R.styleable.TextKForm_textKForm_labelMarginRight,
                _labelMarginRight
            )
            _labelWidth =
                typedArray.getDimensionPixelOffset(R.styleable.TextKForm_textKForm_labelWidth, _labelWidth)
            _textHint = typedArray.getString(R.styleable.TextKForm_textKForm_textHint)
            _textColor = typedArray.getColor(R.styleable.TextKForm_textKForm_textColor, _textColor)
            _textSize = typedArray.getDimensionPixelSize(R.styleable.TextKForm_textKForm_textSize, _textSize)
            _borderBackground =
                typedArray.getResourceId(R.styleable.TextKForm_textKForm_borderBackground, _borderBackground)
            typedArray.recycle()
        }
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.textk_form, this)
        _isRequireIcon = findViewById(R.id.textk_text_form_icon)
        _labelText = findViewById(R.id.textk_text_form_label)
        _textView = findViewById(R.id.textk_text_form_text)
    }

    fun refreshView() {
        _isRequireIcon.visibility = if (_isRequire) View.VISIBLE else View.INVISIBLE
        val layoutParams = _isRequireIcon.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.START or when (_requiredIconPos) {
            0 -> Gravity.TOP
            else -> Gravity.CENTER_VERTICAL
        }
        layoutParams.setMargins(_labelMarginLeft, 0, _labelMarginRight, 0)
        _isRequireIcon.layoutParams = layoutParams

        val layoutParams1 = _labelText.layoutParams
        layoutParams1.width = _labelWidth
        _labelText.layoutParams = layoutParams1

        _labelText.text = _label ?: ""
        _labelText.textSize = _labelTextSize.toFloat()
        _labelText.setTextColor(_labelColor)
        _textHint?.let { _textView.text = it }
        _textView.textSize = _textSize.toFloat()
        _textView.setTextColor(_textColor)
        _textView.setBackgroundResource(_borderBackground)
        _textView.setOnClickListener { view ->
            _onFormClick?.let { it(view) }
        }
    }
}