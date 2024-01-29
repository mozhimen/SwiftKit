package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.graphics.Rect
import android.view.Display
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.content.UtilKContext


/**
 * @ClassName UtilKWindowManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 10:33
 * @Version 1.0
 */
object UtilKWindowManager {

    @JvmStatic
    fun get(context: Context): WindowManager =
        UtilKContext.getWindowManager(context)

    @RequiresApi(CVersCode.V_30_11_R)
    @JvmStatic
    fun getCurrentWindowMetrics(context: Context): WindowMetrics =
        get(context).currentWindowMetrics

    @JvmStatic
    fun getDefaultDisplay(context: Context): Display =
        get(context).defaultDisplay
}