package com.mozhimen.basick.utilk.java.lang

import android.content.Context
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.commons.ISusp_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.app.UtilKActivityManager
import kotlinx.coroutines.CoroutineScope
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
        Looper.getMainLooper().thread == UtilKCurrentThread.get()

    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        if (isMainThread()) {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { block.invoke() }
        } else {
            block.invoke()
        }
    }

    @JvmStatic
    fun runOnMainThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        if (isMainThread()) {
            block.invoke()
        } else {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) { block.invoke() }
        }
    }

    @JvmStatic
    fun runOnMainScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            this.block()
        }
    }

    /**
     * 在子线程运行
     * @param block SuspendFunction0<Unit>
     */
    @JvmStatic
    suspend fun runOnBackThread(block: ISusp_Listener) {
        if (isMainThread()) {
            withContext(Dispatchers.IO) { block.invoke() }
        } else {
            block.invoke()
        }
    }
}