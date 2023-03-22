package com.mozhimen.basick.utilk.res

import android.content.Context
import android.hardware.display.DisplayManager
import com.mozhimen.basick.utilk.content.UtilKContext


/**
 * @ClassName UtilKDisplayManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/21 11:36
 * @Version 1.0
 */
object UtilKDisplayManager {
    @JvmStatic
    fun get(context: Context): DisplayManager =
        UtilKContext.getDisplayManager(context)
}