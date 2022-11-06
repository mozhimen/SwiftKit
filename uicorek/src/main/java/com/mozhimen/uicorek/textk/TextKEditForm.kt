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
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.basek.BaseKLayoutLinear
import com.mozhimen.uicorek.R

/**
 * @ClassName TextKEditForm
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/27 16:17
 * @Version 1.0
 */
typealias ITextKEditFormFocusListener = (view: View, hasFocus: Boolean) -> Unit

class TextKEditForm @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private var _hasFocus: ITextKEditFormFocusListener? = null
    private var _isRequire = false
    private var _requireIconPos = 0
    private var _label: String? = null
    private var _labelColor = Color.BLACK
    private var _labelTextSize = 15f.sp2px()
    private var _labelMarginLeft = 6f.dp2px()
    private var _labelMarginRight = 6f.dp2px()
    private var _labelWidth = 64f.dp2px()
    private var _editHint: String? = null
    private var _editType = 0
    private var _editColor = Color.BLACK
    private var _editSize = 15f.sp2px()
    private var _borderBackground = R.drawable.textk_edit_form
    private var _borderBackgroundFocus = R.drawable.textk_edit_form_focus

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mEditText: EditText

    init {
        initAttrs(attrs, defStyleAttr)
        initView()
        refreshView()
    }

    fun getIsRequire() = _isRequire

    fun setIsRequire(isRequire: Boolean) {
        _isRequire = isRequire
        mIsRequireIcon.visibility = if (_isRequire) View.VISIBLE else View.INVISIBLE
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

    fun setOnFocusListener(listener: ITextKEditFormFocusListener) {
        this._hasFocus = listener
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextKEditForm)
            _isRequire = typedArray.getBoolean(R.styleable.TextKEditForm_textKEditForm_isRequired, _isRequire)
            _requireIconPos =
                typedArray.getInteger(R.styleable.TextKEditForm_textKEditForm_isRequired, _requireIconPos)
            _label = typedArray.getString(R.styleable.TextKEditForm_textKEditForm_label)
            _labelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.TextKEditForm_textKEditForm_labelTextSize,
                _labelTextSize
            )
            _labelColor = typedArray.getColor(R.styleable.TextKEditForm_textKEditForm_labelColor, _labelColor)
            _labelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextKEditForm_textKEditForm_labelMarginLeft,
                    _labelMarginLeft
                )
            _labelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.TextKEditForm_textKEditForm_labelMarginRight,
                    _labelMarginRight
                )
            _labelWidth =
                typedArray.getDimensionPixelOffset(R.styleable.TextKEditForm_textKEditForm_labelWidth, _labelWidth)
            _editHint = typedArray.getString(R.styleable.TextKEditForm_textKEditForm_editHint)
            _editType = typedArray.getInteger(R.styleable.TextKEditForm_textKEditForm_editType, _editType)
            _editColor = typedArray.getColor(R.styleable.TextKEditForm_textKEditForm_editColor, _editColor)
            _editSize = typedArray.getDimensionPixelSize(R.styleable.TextKEditForm_textKEditForm_editSize, _editSize)
            _borderBackground =
                typedArray.getResourceId(R.styleable.TextKEditForm_textKEditForm_borderBackground, _borderBackground)
            _borderBackgroundFocus = typedArray.getResourceId(
                R.styleable.TextKEditForm_textKEditForm_borderBackgroundHasFocus,
                _borderBackgroundFocus
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
        mIsRequireIcon.visibility = if (_isRequire) View.VISIBLE else View.INVISIBLE
        val layoutParams = mIsRequireIcon.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.START or when (_requireIconPos) {
            0 -> Gravity.TOP
            else -> Gravity.CENTER_VERTICAL
        }
        layoutParams.setMargins(_labelMarginLeft, 0, _labelMarginRight, 0)
        mIsRequireIcon.layoutParams = layoutParams
        val layoutParams1 = mLabelText.layoutParams
        layoutParams1.width = _labelWidth
        mLabelText.layoutParams = layoutParams1
        mLabelText.text = _label ?: ""
        mLabelText.textSize = _labelTextSize.toFloat()
        mLabelText.setTextColor(_labelColor)
        mEditText.inputType = when (_editType) {
            1 -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
            2 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            3 -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            else -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        }
        mEditText.hint = _editHint ?: ""
        mEditText.textSize = _editSize.toFloat()
        mEditText.setTextColor(_editColor)
        mEditText.setBackgroundResource(_borderBackground)
        mEditText.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                mEditText.setBackgroundResource(_borderBackgroundFocus)
            } else {
                mEditText.setBackgroundResource(_borderBackground)
            }
            _hasFocus?.let { it(view, hasFocus) }
        }
    }
}