package com.mozhimen.uicorek.popwink.quick.commons

import android.view.View
import com.mozhimen.uicorek.popwink.quick.QuickPopup

/**
 * @ClassName OnQuickPopupClickListenerWrapper
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:12
 * @Version 1.0
 */
abstract class QuickPopupOnClickCallback : View.OnClickListener {
    var quickPopup: QuickPopup? = null

    override fun onClick(view: View?) {
        onClick(quickPopup, view)
    }

    abstract fun onClick(basePopup: QuickPopup?, view: View?)
}