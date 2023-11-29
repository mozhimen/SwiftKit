package com.mozhimen.basick.utilk.android.view

import android.view.ViewParent

/**
 * @ClassName UtilKViewParent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/29 11:30
 * @Version 1.0
 */
fun ViewParent.requestAllowInterceptTouchEvent(isIntercept: Boolean) {
    UtilKViewParent.requestAllowInterceptTouchEvent(this, isIntercept)
}

object UtilKViewParent {
    @JvmStatic
    fun requestAllowInterceptTouchEvent(viewParent: ViewParent, isIntercept: Boolean) {
        viewParent.requestDisallowInterceptTouchEvent(!isIntercept)
    }
}