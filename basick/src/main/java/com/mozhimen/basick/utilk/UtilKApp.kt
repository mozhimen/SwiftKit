package com.mozhimen.basick.utilk

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Process

/**
 * @ClassName UtilKActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/26 16:42
 * @Version 1.0
 */
object UtilKApp {
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

    fun isMainProcess(application: Application): Boolean {
        val myPid = Process.myPid()
        val activityManager = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        for (process in runningAppProcesses) {
            if (process.processName == application.packageName) {
                return true
            }
        }
        return false
    }
}