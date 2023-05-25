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
import kotlinx.coroutines.withContext

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
        for (process in UtilKActivityManager.get(context).runningAppProcesses) {
            if (process.processName == UtilKContext.getPackageName(context)) return true
        }
        return false
    }

    /**
     * 是否是主线程
     * @return Boolean
     */
    @JvmStatic
    fun isMainThread(): Boolean =
        Looper.getMainLooper().thread == Thread.currentThread()

    /**
     * 在子线程运行
     * @param lifecycleOwner LifecycleOwner
     * @param block Function0<Unit>
     */
    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: () -> Unit) {
        if (isMainThread()) {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { block.invoke() }
        } else {
            block.invoke()
        }
    }

    /**
     * 在子线程运行
     * @param block SuspendFunction0<Unit>
     */
    @JvmStatic
    suspend fun runOnBackThread(block: suspend () -> Unit) {
        if (isMainThread()) {
            withContext(Dispatchers.IO) { block.invoke() }
        } else {
            block.invoke()
        }
    }
}