package com.mozhimen.basick.utilk.os.thread

import android.app.Activity
import android.content.Context
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.utilk.content.UtilKContext
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import com.mozhimen.basick.utilk.content.activity.UtilKActivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName UtilKThread
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/25 0:03
 * @Version 1.0
 */
object UtilKThread {
    /**
     * 是否在主线程
     * @param context Application
     * @return Boolean
     */
    @JvmStatic
    fun isMainProcess(context: Context): Boolean {
        val runningAppProcesses = UtilKActivityManager.get(context).runningAppProcesses
        for (process in runningAppProcesses) {
            if (process.processName == UtilKContext.getPackageName(context)) return true
        }
        return false
    }

    /**
     * 是否是主线程
     * @return Boolean
     */
    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: () -> Unit) {
        if (isMainThread()) {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO){
                block.invoke()
            }
        } else {
            block.invoke()
        }
    }
}