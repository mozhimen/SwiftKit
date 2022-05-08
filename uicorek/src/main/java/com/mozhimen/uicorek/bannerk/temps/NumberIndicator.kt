package com.mozhimen.uicorek.bannerk.temps

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.mozhimen.basicsk.extsk.dp2px
import com.mozhimen.basicsk.basek.BaseKLayoutFrame
import com.mozhimen.uicorek.bannerk.commons.IBannerKIndicator

/**
 * @ClassName NumberIndicator
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/19 23:18
 * @Version 1.0
 */
class NumberIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseKLayoutFrame(context, attrs, defStyleAttr),
    IBannerKIndicator<FrameLayout> {

    companion object {
        private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    private var _pointHorizontalPadding = 0//指示点左右内间距

    private var _pointVerticalPadding = 0//指示点上下内间距

    init {
        initView()
    }

    override fun initView() {
        _pointHorizontalPadding = 10f.dp2px()
        _pointVerticalPadding = 10f.dp2px()
    }

    override fun get(): FrameLayout = this

    override fun inflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }

        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL
        groupView.setPadding(0, 0, _pointHorizontalPadding, _pointVerticalPadding)

        val indexTv = TextView(context)
        indexTv.text = "1"
        indexTv.setTextColor(Color.WHITE)
        groupView.addView(indexTv)

        val symbolTv = TextView(context)
        symbolTv.text = " / "
        symbolTv.setTextColor(Color.WHITE)
        groupView.addView(symbolTv)

        val countTv = TextView(context)
        countTv.text = count.toString()
        countTv.setTextColor(Color.WHITE)
        groupView.addView(countTv)

        val groupViewParams = LayoutParams(VMC, VMC)
        groupViewParams.gravity = Gravity.END or Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onItemChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        val indexTv = viewGroup.getChildAt(0) as TextView
        val countTv = viewGroup.getChildAt(2) as TextView
        indexTv.text = (current + 1).toString()
        countTv.text = count.toString()
    }
}