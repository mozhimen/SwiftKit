package com.mozhimen.uicoremk.btnmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mozhimen.uicoremk.R
import com.mozhimen.uicoremk.layoutmk.commons.LayoutMKLinear

/**
 * @ClassName BtnMKSpinnerForm
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:13
 * @Version 1.0
 */
typealias OnBtnMKSpinnerFormSelected = (Int) -> Unit

class BtnMKSpinnerForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutMKLinear(context, attrs, defStyleAttr) {

    private lateinit var itemAdapter: ArrayAdapter<String>
    private var mIsRequire = false
    private var mRequiredIconPos = 0
    private var mLabelTextSize = 16f.sp2px()
    private var mLabel: String? = null
    private var mLabelColor = Color.BLACK
    private var mLabelMarginLeft = 6f.dp2px()
    private var mLabelMarginRight = 6f.dp2px()
    private var mLabelWidth = 64f.dp2px()
    private var mSpinnerList = arrayListOf("")
    private var mSpinnerItemLayout = R.layout.btnmk_spinner_form_item
    private var mOnBtnMKSpinnerFormSelected: OnBtnMKSpinnerFormSelected? = null
    private var mBorderBackground = R.drawable.btnmk_icon_background

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mSpinner: Spinner
    private lateinit var mSpinnerContainer: FrameLayout

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

    fun setCallback(onBtnMKSpinnerFormSelected: OnBtnMKSpinnerFormSelected) {
        this.mOnBtnMKSpinnerFormSelected = onBtnMKSpinnerFormSelected
    }

    fun setSelectItem(position: Int) {
        if (position in 0..mSpinnerList.size) {
            mSpinner.setSelection(position)
        }
    }

    fun setEntries(list: List<String>, pos: Int = 0) {
        mSpinnerList.clear()
        mSpinnerList.addAll(list)
        itemAdapter.notifyDataSetChanged()
        if (list.isNotEmpty() && pos in list.indices) {
            mSpinner.setSelection(pos)
        }
    }

    fun getSelectItem(): Pair<Int, String> {
        val item = if (mSpinnerList.isNullOrEmpty()) "" else mSpinner.selectedItem as String
        return mSpinnerList.indexOf(item) to item
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnMKSpinnerForm)
            mIsRequire = typedArray.getBoolean(R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_isRequired, mIsRequire)
            mRequiredIconPos =
                typedArray.getInteger(R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_requiredIconPos, mRequiredIconPos)
            mLabel = typedArray.getString(R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_labelColor, mLabelColor)
            mLabelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_labelMarginLeft,
                    mLabelMarginLeft
                )
            mLabelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_labelMarginRight,
                    mLabelMarginRight
                )
            mLabelWidth = typedArray.getDimensionPixelOffset(
                R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_labelWidth,
                mLabelWidth
            )
            mSpinnerList.addAll(
                generateStringArray(
                    typedArray.getResourceId(
                        R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_spinnerEntries,
                        0
                    )
                )
            )
            mSpinnerItemLayout =
                typedArray.getResourceId(
                    R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_spinnerItemLayout,
                    mSpinnerItemLayout
                )
            mBorderBackground =
                typedArray.getResourceId(
                    R.styleable.BtnMKSpinnerForm_btnMKSpinnerForm_borderBackground,
                    mBorderBackground
                )
            typedArray.recycle()
        }
    }

    private fun generateStringArray(resourceId: Int): Array<String> {
        return if (resourceId != 0) {
            resources.getStringArray(resourceId)
        } else {
            emptyArray()
        }
    }

    @SuppressLint("InflateParams")
    override fun initView() {
        LayoutInflater.from(context).inflate(R.layout.btnmk_spinner_form, this)
        mIsRequireIcon = findViewById(R.id.btnmk_spinner_form_icon)
        mLabelText = findViewById(R.id.btnmk_spinner_form_label)
        mSpinner = findViewById(R.id.btnmk_spinner_form_spinner)
        mSpinnerContainer = findViewById(R.id.btnMK_spinner_form_spinner_container)
    }

    private fun refreshView() {
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

        itemAdapter = ArrayAdapter(context, mSpinnerItemLayout, mSpinnerList)
        mSpinner.adapter = itemAdapter

        mSpinner.setSelection(0)
        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mOnBtnMKSpinnerFormSelected?.let { it(p2) }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        mSpinnerContainer.setBackgroundResource(mBorderBackground)
    }
}