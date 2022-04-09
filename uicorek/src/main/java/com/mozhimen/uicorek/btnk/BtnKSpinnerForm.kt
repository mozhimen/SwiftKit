package com.mozhimen.uicorek.btnk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.commons.LayoutKLinear

/**
 * @ClassName BtnKSpinnerForm
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:13
 * @Version 1.0
 */
typealias OnBtnKSpinnerFormSelected = (Int) -> Unit

class BtnKSpinnerForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LayoutKLinear(context, attrs, defStyleAttr) {

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
    private var mSpinnerItemLayout = R.layout.btnk_spinner_form_item
    private var mOnBtnKSpinnerFormSelected: OnBtnKSpinnerFormSelected? = null
    private var mBorderBackground = R.drawable.btnk_icon_background

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

    fun setCallback(onBtnKSpinnerFormSelected: OnBtnKSpinnerFormSelected) {
        this.mOnBtnKSpinnerFormSelected = onBtnKSpinnerFormSelected
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
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BtnKSpinnerForm)
            mIsRequire = typedArray.getBoolean(R.styleable.BtnKSpinnerForm_btnKSpinnerForm_isRequired, mIsRequire)
            mRequiredIconPos =
                typedArray.getInteger(R.styleable.BtnKSpinnerForm_btnKSpinnerForm_requiredIconPos, mRequiredIconPos)
            mLabel = typedArray.getString(R.styleable.BtnKSpinnerForm_btnKSpinnerForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.BtnKSpinnerForm_btnKSpinnerForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.BtnKSpinnerForm_btnKSpinnerForm_labelColor, mLabelColor)
            mLabelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnKSpinnerForm_btnKSpinnerForm_labelMarginLeft,
                    mLabelMarginLeft
                )
            mLabelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.BtnKSpinnerForm_btnKSpinnerForm_labelMarginRight,
                    mLabelMarginRight
                )
            mLabelWidth = typedArray.getDimensionPixelOffset(
                R.styleable.BtnKSpinnerForm_btnKSpinnerForm_labelWidth,
                mLabelWidth
            )
            mSpinnerList.addAll(
                generateStringArray(
                    typedArray.getResourceId(
                        R.styleable.BtnKSpinnerForm_btnKSpinnerForm_spinnerEntries,
                        0
                    )
                )
            )
            mSpinnerItemLayout =
                typedArray.getResourceId(
                    R.styleable.BtnKSpinnerForm_btnKSpinnerForm_spinnerItemLayout,
                    mSpinnerItemLayout
                )
            mBorderBackground =
                typedArray.getResourceId(
                    R.styleable.BtnKSpinnerForm_btnKSpinnerForm_borderBackground,
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
        LayoutInflater.from(context).inflate(R.layout.btnk_spinner_form, this)
        mIsRequireIcon = findViewById(R.id.btnk_spinner_form_icon)
        mLabelText = findViewById(R.id.btnk_spinner_form_label)
        mSpinner = findViewById(R.id.btnk_spinner_form_spinner)
        mSpinnerContainer = findViewById(R.id.btnK_spinner_form_spinner_container)
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
                mOnBtnKSpinnerFormSelected?.let { it(p2) }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        mSpinnerContainer.setBackgroundResource(mBorderBackground)
    }
}