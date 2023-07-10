package com.mozhimen.uicorek.popwink.builder.commons

import android.view.View
import com.mozhimen.uicorek.popwink.builder.PopwinKBuilderDelegate

/**
 * @ClassName OnQuickPopupClickListenerWrapper
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/28 22:12
 * @Version 1.0
 */
abstract class PopwinKBuilderOnClickCallback : View.OnClickListener {
    var popwinKBuilderDelegate: PopwinKBuilderDelegate? = null

    override fun onClick(view: View?) {
        onClick(popwinKBuilderDelegate, view)
    }

    abstract fun onClick(basePopup: PopwinKBuilderDelegate?, view: View?)
}