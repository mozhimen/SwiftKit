package com.mozhimen.basick.utilk.android.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.android.content.UtilKIntent
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager
import com.mozhimen.basick.utilk.kotlin.UtilKString
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKLifecycle

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 13:44
 * @Version 1.0
 */
fun Activity.isFinishingOrDestroyed(): Boolean =
    UtilKActivity.isFinishingOrDestroyed(this)

fun AppCompatActivity.runOnBackThread(block: () -> Unit) {
    UtilKActivity.runOnBackThread(this, block)
}

object UtilKActivity {

    @JvmStatic
    fun requestWindowFeature(activity: Activity, featureId: Int) {
        activity.requestWindowFeature(featureId)
    }

    @JvmStatic
    fun getCurrentFocus(activity: Activity): View? =
        activity.currentFocus

    @RequiresApi(CVersionCode.V_30_11_R)
    @JvmStatic
    fun getDisplay(activity: Activity): Display =
        activity.display!!

    @JvmStatic
    fun getWindowManager(activity: Activity): WindowManager =
        activity.windowManager

    /**
     * 获取启动Activity
     * @param packageName String
     * @return String?
     */
    @JvmStatic
    fun getLauncherActivityName(context: Context, packageName: String): String {
        if (UtilKString.isHasSpace(packageName) || packageName.isEmpty()) return ""
        val resolveInfos = UtilKPackageManager.queryIntentActivities(context, UtilKIntent.getMainLauncher(packageName, null), 0)
        return if (resolveInfos.isEmpty()) "" else resolveInfos[0].activityInfo.name
    }

    /**
     * 判断context是否是Activity 这里注意一定要再Application中加入StackK并初始化
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
        return if (returnTopIfNull) StackKCb.instance.getStackTopActivity() else null
    }

    /**
     * 根据View获取Activity
     * @param view View
     * @return Activity?
     */
    @JvmStatic
    fun getActivityByView(view: View): Activity? =
        getActivityByContext(view.context)

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
            activity = StackKCb.instance.getStackTopActivity()
        }
        return activity
    }

    /**
     * 判断Activity是否被销毁
     * @param context Context
     * @return Boolean
     */
    @JvmStatic
    fun isDestroyed(context: Context): Boolean {
        val activity: Activity? = getActivityByContext(context)
        return if (activity != null) isDestroyed(activity) else true
    }

    /**
     * 判断Activity是否被销毁
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isDestroyed(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= CVersionCode.V_17_42_J1) {
            activity.isDestroyed || isFinishing(activity)
        } else isFinishing(activity)
    }

    /**
     * 判断Activity是否结束
     * @param activity Activity
     * @return Boolean
     */
    @JvmStatic
    fun isFinishing(activity: Activity): Boolean =
        activity.isFinishing

    @JvmStatic
    fun isFinishingOrDestroyed(activity: Activity): Boolean =
        isFinishing(activity) || isDestroyed(activity)

    @JvmStatic
    fun runOnBackThread(appCompatActivity: AppCompatActivity, block: () -> Unit) {
        UtilKLifecycle.runOnBackThread(appCompatActivity, block)
    }
}