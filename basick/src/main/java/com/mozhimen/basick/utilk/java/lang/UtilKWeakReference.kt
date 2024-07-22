package com.mozhimen.basick.utilk.java.lang

import java.lang.ref.WeakReference

/**
 * @ClassName UtilKWeakReference
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/22
 * @Version 1.0
 */
object UtilKWeakReference {
    @JvmStatic
    fun <T> get(obj: T): WeakReference<T> =
        WeakReference(obj)
}