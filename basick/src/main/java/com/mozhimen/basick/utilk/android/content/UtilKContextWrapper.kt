package com.mozhimen.basick.utilk.android.content

import android.content.Context
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivityWrapper

/**
 * @ClassName UtilKContextWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/19
 * @Version 1.0
 */
object UtilKContextWrapper {
    @JvmStatic
    @OApiUse_BaseApplication
    fun isFinishingOrDestroyed(context: Context): Boolean =
        UtilKActivityWrapper.isFinishingOrDestroyed(context)
}