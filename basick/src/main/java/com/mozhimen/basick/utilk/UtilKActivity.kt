package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 13:44
 * @Version 1.0
 */
object UtilKActivity {

    fun isActivityDestroyed(context: Context): Boolean {
        val activity: Activity? = findActivity(context)
        return if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.isDestroyed || activity.isFinishing
            } else activity.isFinishing
        } else true
    }

    fun findActivity(context: Context): Activity? {
        //怎么判断context 是不是activity 类型的
        if (context is Activity) return context else if (context is ContextWrapper) {
            return findActivity(context.baseContext)
        }
        return null
    }
}