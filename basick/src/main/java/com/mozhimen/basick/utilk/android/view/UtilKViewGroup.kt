package com.mozhimen.basick.utilk.android.view

import android.view.View
import android.view.ViewGroup
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKScroll
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/22 22:30
 * @Version 1.0
 */
fun ViewGroup.addView_ofMatchParent(view: View) {
    UtilKViewGroup.addView_ofMatchParent(this, view)
}

//////////////////////////////////////////////////////////////////////////////////////

object UtilKViewGroup : IUtilK {

    @JvmStatic
    fun addView(viewGroup: ViewGroup, child: View, params: ViewGroup.LayoutParams) {
        viewGroup.addView(child, params)
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addView_ofMatchParent(viewGroup: ViewGroup, view: View) {
        if (view.parent == null)
            viewGroup.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        else
            UtilKLogWrapper.e(TAG, "addViewMatchParent: ")
    }
}