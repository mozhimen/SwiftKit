package com.mozhimen.basick.utilk.content.activity

import android.app.ActivityManager
import android.content.Context
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKActivityManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 23:23
 * @Version 1.0
 */
object UtilKActivityManager {
    @JvmStatic
    fun get(context: Context): ActivityManager =
        UtilKContext.getActivityManager(context)
}