package com.mozhimen.app.uicorek.datak.mos

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.mozhimen.app.R
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.fontStyle
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.datak.commons.DataKItem
import com.mozhimen.uicorek.datak.helpers.DataKViewHolder

/**
 * @ClassName DataItemLoadMore
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/25 0:31
 * @Version 1.0
 */
class DataItemLoadMore(index: Int) : DataKItem<Int, DataKViewHolder>(index) {
    private var parentWidth: Int = 0

    override fun onBindData(holder: DataKViewHolder, position: Int) {
        data?.let {
            val frameLayout = holder.itemView as FrameLayout
            val textView = frameLayout.getChildAt(0) as TextView
            textView.text = it.toString()
        }
    }

    override fun getItemView(parent: ViewGroup): View? {
        val frameLayout = FrameLayout(parent.context)
        val textLP =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val textView = TextView(parent.context)
        textView.textSize = 18f
        textView.setTextColor(UtilKRes.getColor(R.color.blue_light))
        textView.fontStyle(Typeface.BOLD)
        textView.gravity = Gravity.CENTER
        textView.setBackgroundColor(UtilKRes.getColor(R.color.blue_theme))
        frameLayout.addView(textView, textLP)
        frameLayout.setBackgroundColor(UtilKRes.getColor(R.color.white))
        frameLayout.setPadding(0, 0, 0, 10f.dp2px())
        return frameLayout
    }

    override fun onViewAttachedToWindow(holder: DataKViewHolder) {
        //提前给imageview 预设一个高度值  等于parent的宽度
        parentWidth = (holder.itemView.parent as ViewGroup).measuredWidth
        val params = holder.itemView.layoutParams
        if (params.width != parentWidth) {
            params.width = parentWidth
            params.height = 140.dp2px()
            holder.itemView.layoutParams = params
        }
    }
}