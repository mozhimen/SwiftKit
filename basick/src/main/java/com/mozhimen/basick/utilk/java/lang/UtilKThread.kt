package com.mozhimen.basick.utilk.java.lang

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.android.os.UtilKLooper
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
    @JvmStatic
    fun get_ofCur(): Thread =
        Thread.currentThread()

    @JvmStatic
    fun get_ofMain(): Thread =
        UtilKLooper.getThread_ofMain()

    /////////////////////////////////////////////////////////

    @JvmStatic
    fun getName_ofCur(): String =
        get_ofCur().name

    @JvmStatic
    fun getStackTrace_ofCur(): Array<StackTraceElement> =
        get_ofCur().stackTrace

    /////////////////////////////////////////////////////////

    /**
     * 是否是主线程
     */
    @JvmStatic
    fun isMainThread(): Boolean =
        get_ofMain() == get_ofCur()

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun runOnMainThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        if (isMainThread())
            block.invoke()
        else
            lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) { block.invoke() }
    }

    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        if (isMainThread())
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) { block.invoke() }
        else
            block.invoke()
    }


    @JvmStatic
    suspend fun runOnMainThread(block: ISuspend_Listener) {
        if (isMainThread())
            block.invoke()
        else
            withContext(Dispatchers.Main) { block.invoke() }
    }

    @JvmStatic
    suspend fun runOnBackThread(block: ISuspend_Listener) {
        if (isMainThread())
            withContext(Dispatchers.IO) { block.invoke() }
        else
            block.invoke()
    }
}