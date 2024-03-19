package com.mozhimen.basick.utilk.android.app

import android.app.UiModeManager
import android.content.Context
import com.mozhimen.basick.elemk.android.content.cons.CConfiguration
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKUiModeManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/31 11:17
 * @Version 1.0
 */
object UtilKUiModeManager {
    @JvmStatic
    fun get(context: Context): UiModeManager =
        UtilKContext.getUiModeManager(context)

    @JvmStatic
    fun getCurrentModeType(context: Context): Int =
        get(context).currentModeType

    @JvmStatic
    fun isCurrentModeTypeDesk(context: Context): Boolean =
        getCurrentModeType(context) == CConfiguration.UiMode.TYPE_DESK
}