package com.mozhimen.basick.utilk.android.os

import android.os.Looper
import androidx.annotation.WorkerThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.java.lang.UtilKCurrentThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @ClassName UtilKLooper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 23:13
 * @Version 1.0
 */
object UtilKLooper {
    @JvmStatic
    fun getMainLooper():Looper =
        Looper.getMainLooper()

    @JvmStatic
    fun getMyLooper():Looper? =
        Looper.myLooper()

    @JvmStatic
    fun getMainThread():Thread =
        getMainLooper().thread

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 是否是MainLooper
     */
    @JvmStatic
    fun isMainLooper(): Boolean =
        Looper.myLooper() == Looper.getMainLooper()

    /**
     * 是否是主线程
     */
    @JvmStatic
    fun isMainThread(): Boolean =
        getMainThread() == UtilKCurrentThread.get()

    ////////////////////////////////////////////////////////////////////////////

    /**
     * 循环
     */
    @JvmStatic
    fun prepareAndLoop(block: I_Listener) {
        var myLooper = Looper.myLooper()
        if (myLooper == null) {
            Looper.prepare()
            myLooper = Looper.myLooper()
        }
        block.invoke()
        if (myLooper != null) {
            Looper.loop()
            myLooper.quit()
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun runOnMainScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            this.block()
        }
    }

    @JvmStatic
    fun runOnBackScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            this.block()
        }
    }

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

    ////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    suspend fun runOnBackThread(block: ISuspend_Listener) {
        if (isMainThread())
            withContext(Dispatchers.IO) { block.invoke() }
        else
            block.invoke()
    }
}