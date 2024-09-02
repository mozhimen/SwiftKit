package com.mozhimen.basick.utilk.android.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKScroll
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/22 22:30
 * @Version 1.0
 */
fun ViewGroup.addViewSafe_ofMatchParent(view: View) {
    UtilKViewGroup.addViewSafe_ofMatchParent(this, view)
}

fun ViewGroup.addViewSafe(view: View, width: Int, height: Int) {
    UtilKViewGroup.addViewSafe(this, view, width, height)
}

//////////////////////////////////////////////////////////////////////////////////////

object UtilKViewGroup : IUtilK {
    @JvmStatic
    fun applyInflate(viewGroup: ViewGroup, @LayoutRes intResLayout: Int): View =
        UtilKLayoutInflater.from_inflate(viewGroup, intResLayout)

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addView(viewGroup: ViewGroup, child: View, params: ViewGroup.LayoutParams) {
        viewGroup.addView(child, params)
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addViewSafe_ofMatchParent(viewGroup: ViewGroup, view: View) {
        addViewSafe(viewGroup, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    @JvmStatic
    fun addViewSafe_ofWrapContent(viewGroup: ViewGroup, view: View) {
        addViewSafe(viewGroup, view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @JvmStatic
    fun addViewSafe(viewGroup: ViewGroup, view: View, width: Int, height: Int) {
        addViewSafe(viewGroup, view, ViewGroup.LayoutParams(width, height))
    }

    @JvmStatic
    fun addViewSafe(viewGroup: ViewGroup, view: View, layoutParams: ViewGroup.LayoutParams) {
        if (view.parent == null)
            viewGroup.addView(view, layoutParams)
        else if (view.parent is ViewGroup) {
            (view.parent as ViewGroup).removeView(view)
            viewGroup.addView(view, layoutParams)
        } else {
            UtilKLogWrapper.e(TAG, "addViewSafe: fail")
        }
    }
}