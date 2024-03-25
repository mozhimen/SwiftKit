package com.mozhimen.basick.utilk.android.widget

import android.view.View
import android.widget.LinearLayout

/**
 * @ClassName UtilKLinearLayout
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/17 12:09
 * @Version 1.0
 */
fun LinearLayout.addView_ofWeight(view: View, weight: Float) {
    UtilKLinearLayout.addView_ofWeight(this, view, weight)
}

object UtilKLinearLayout {
    @JvmStatic
    fun addView_ofWeight(linearLayout: LinearLayout, view: View, weight: Float) {
        linearLayout.addView(view, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weight))
    }
}