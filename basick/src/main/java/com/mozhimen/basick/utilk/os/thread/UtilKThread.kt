package com.mozhimen.basick.utilk.os.thread

import android.os.Looper

/**
 * @ClassName UtilKThread
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/25 0:03
 * @Version 1.0
 */
object UtilKThread {
    /**
     * 是否是主线程
     * @return Boolean
     */
    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    /**
     * 是否是MainLooper
     * @return Boolean
     */
    @JvmStatic
    fun isMainLooper(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}