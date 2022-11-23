package com.mozhimen.uicorek.popwink.base.cons

import android.text.TextUtils
import android.view.View

/**
 * @ClassName CUI
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/24 0:11
 * @Version 1.0
 */
object CUI {
    const val POPUP_DECORVIEW = "android.widget.PopupWindow\$PopupDecorView"
    const val POPUP_VIEWCONTAINER = "android.widget.PopupWindow\$PopupViewContainer"
    const val POPUP_BACKGROUNDVIEW = "android.widget.PopupWindow\$PopupBackgroundView"

    @JvmStatic
    fun isPopupDecorView(view: View): Boolean {
        return TextUtils.equals(view.javaClass.name, POPUP_DECORVIEW)
    }

    @JvmStatic
    fun isPopupViewContainer(view: View): Boolean {
        return TextUtils.equals(view.javaClass.name, POPUP_VIEWCONTAINER)
    }

    @JvmStatic
    fun isPopupBackgroundView(view: View): Boolean {
        return TextUtils.equals(view.javaClass.name, POPUP_BACKGROUNDVIEW)
    }
}