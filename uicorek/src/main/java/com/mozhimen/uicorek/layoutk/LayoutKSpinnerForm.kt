package com.mozhimen.uicorek.layoutk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
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
 * @ClassName BtnKSpinnerForm
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 20:13
 * @Version 1.0
 */
typealias ILayoutKSpinnerFormListener = (position: Int) -> Unit

class LayoutKSpinnerForm @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutLinear(context, attrs, defStyleAttr) {

    private lateinit var itemAdapter: ArrayAdapter<String>
    private var mIsRequire = false
    private var mRequiredIconPos = 0
    private var mLabelTextSize = 15f.sp2px()
    private var mLabel: String? = null
    private var mLabelColor = Color.BLACK
    private var mLabelMarginLeft = 6f.dp2px()
    private var mLabelMarginRight = 6f.dp2px()
    private var mLabelWidth = 64f.dp2px()
    private var mSpinnerList = arrayListOf("")
    private var mSpinnerItemLayout = R.layout.layoutk_spinner_form_item
    private var mOnBtnKSpinnerFormSelected: ILayoutKSpinnerFormListener? = null
    private var mBorderBackground = R.drawable.layoutk_btn_icon_background

    private lateinit var mLabelText: TextView
    private lateinit var mIsRequireIcon: ImageView
    private lateinit var mSpinner: Spinner
    private lateinit var mSpinnerContainer: FrameLayout

    init {
        initAttrs(attrs, defStyleAttr)
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

    fun setCallback(onBtnKSpinnerFormSelected: ILayoutKSpinnerFormListener) {
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
        val item = if (mSpinnerList.isEmpty()) "" else mSpinner.selectedItem as String
        return mSpinnerList.indexOf(item) to item
    }

    @SuppressLint("CustomViewStyleable")
    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKSpinnerForm)
            mIsRequire = typedArray.getBoolean(R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_isRequired, mIsRequire)
            mRequiredIconPos =
                typedArray.getInteger(R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_requiredIconPos, mRequiredIconPos)
            mLabel = typedArray.getString(R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_label)
            mLabelTextSize = typedArray.getDimensionPixelSize(
                R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_labelTextSize,
                mLabelTextSize
            )
            mLabelColor = typedArray.getColor(R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_labelColor, mLabelColor)
            mLabelMarginLeft =
                typedArray.getDimensionPixelOffset(
                    R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_labelMarginLeft,
                    mLabelMarginLeft
                )
            mLabelMarginRight =
                typedArray.getDimensionPixelOffset(
                    R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_labelMarginRight,
                    mLabelMarginRight
                )
            mLabelWidth = typedArray.getDimensionPixelOffset(
                R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_labelWidth,
                mLabelWidth
            )
            mSpinnerList.addAll(
                generateStringArray(
                    typedArray.getResourceId(
                        R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_spinnerEntries,
                        0
                    )
                )
            )
            mSpinnerItemLayout =
                typedArray.getResourceId(
                    R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_spinnerItemLayout,
                    mSpinnerItemLayout
                )
            mBorderBackground =
                typedArray.getResourceId(
                    R.styleable.LayoutKSpinnerForm_layoutKSpinnerForm_borderBackground,
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
        LayoutInflater.from(context).inflate(R.layout.layoutk_spinner_form, this)
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