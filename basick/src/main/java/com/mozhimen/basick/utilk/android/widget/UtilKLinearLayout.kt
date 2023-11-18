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
fun LinearLayout.addViewOfWeight(view: View) {
    UtilKLinearLayout.addViewOfWeight(this, view)
}

object UtilKLinearLayout {
    @JvmStatic
    fun addViewOfWeight(linearLayout: LinearLayout, view: View) {
        linearLayout.addView(view, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f))
    }
}