package com.mozhimen.basick.utilk

import android.os.Looper

/**
 * @ClassName UtilKThread
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/25 0:03
 * @Version 1.0
 */
object UtilKThread {
    @JvmStatic
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    @JvmStatic
    fun isMainLooper(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}