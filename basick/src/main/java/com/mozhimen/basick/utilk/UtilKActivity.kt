package com.mozhimen.basick.utilk

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 13:44
 * @Version 1.0
 */
object UtilKActivity {

    /**
     * 判断Activity是否被销毁
     * @param context Context
     * @return Boolean
     */
    fun isActivityDestroyed(context: Context): Boolean {
        val activity: Activity? = findActivity(context)
        return if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                activity.isDestroyed || activity.isFinishing
            } else activity.isFinishing
        } else true
    }

    /**
     * 判断context是否是Activity
     * @param context Context
     * @return Activity?
     */
    fun findActivity(context: Context): Activity? {
        //怎么判断context 是不是activity 类型的
        if (context is Activity) return context else if (context is ContextWrapper) {
            return findActivity(context.baseContext)
        }
        return null
    }

    /**
     * 根据View获取Activity
     * @param view View
     * @return Activity?
     */
    @JvmStatic
    fun getActivityByView(view: View): Activity? {
        var context = view.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }
}