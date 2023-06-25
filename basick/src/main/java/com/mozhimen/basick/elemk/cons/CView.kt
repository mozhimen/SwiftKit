package com.mozhimen.basick.elemk.cons

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi

/**
 * @ClassName CView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 23:24
 * @Version 1.0
 */
object CView {
    const val FLAG_HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    const val FLAG_LAYOUT_HIDE_NAVIGATION = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    const val FLAG_IMMERSIVE_STICKY =View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    const val FLAG_LAYOUT_STABLE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    const val FLAG_LAYOUT_FULLSCREEN = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    const val FLAG_FULLSCREEN = View.SYSTEM_UI_FLAG_FULLSCREEN
    @RequiresApi(CVersionCode.V_23_6_M)
    const val FLAG_LIGHT_STATUS_BAR = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    const val FLAG_LOW_PROFILE = View.SYSTEM_UI_FLAG_LOW_PROFILE
}