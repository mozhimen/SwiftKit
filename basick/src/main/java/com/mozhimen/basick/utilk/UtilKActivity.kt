package com.mozhimen.basick.utilk

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import com.mozhimen.basick.stackk.StackK

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
    @JvmStatic
    fun isActivityDestroyed(context: Context): Boolean {
        val activity: Activity? = getActivityByContext(context)
        return if (activity != null) isActivityDestroyed(activity) else true
    }

    /**
     * 判断Activity是否被销毁
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isActivityDestroyed(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.isDestroyed || activity.isFinishing
        } else activity.isFinishing
    }

    /**
     * 判断context是否是Activity
     * @param context Context
     * @param returnTopIfNull Boolean
     * @return Activity?
     */
    @JvmStatic
    fun getActivityByContext(context: Context, returnTopIfNull: Boolean = false): Activity? {
        var tempContext = context
        if (tempContext is Activity) {
            return tempContext
        }
        var tryCount = 0
        while (tempContext is ContextWrapper) {
            if (tempContext is Activity) {
                return tempContext
            }
            if (tryCount > 20) {
                break
            }
            tempContext = tempContext.baseContext
            tryCount++
        }
        return if (returnTopIfNull) StackK.getStackTopActivity() else null
    }

    /**
     * 根据View获取Activity
     * @param view View
     * @return Activity?
     */
    @JvmStatic
    fun getActivityByView(view: View): Activity? {
        return getActivityByContext(view.context)
    }

    /**
     * 寻找Activity从Obj
     * @param obj Any
     * @param returnTopIfNull Boolean
     * @return Activity?
     */
    @JvmStatic
    fun getActivityByObj(obj: Any, returnTopIfNull: Boolean = false): Activity? {
        var activity: Activity? = null
        when (obj) {
            is Context -> {
                activity = getActivityByContext(obj, true)
            }
            is Fragment -> {
                activity = obj.activity
            }
            is Dialog -> {
                activity = getActivityByContext(obj.context, true)
            }
        }
        if (activity == null && returnTopIfNull) {
            activity = StackK.getStackTopActivity()
        }
        return activity
    }
}