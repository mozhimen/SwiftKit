package com.mozhimen.basick.utilk.kotlin.text

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import androidx.collection.LongSparseArray
import androidx.collection.SimpleArrayMap
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.lang.reflect.Array


/**
 * @ClassName UtilKAny
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/14 15:41
 * @Version 1.0
 */
object UtilKAny {
    @JvmStatic
    fun isNullOrEmpty(obj: Any?): Boolean {
        if (obj == null) return true
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is CharSequence && obj.toString().isEmpty()) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        if (UtilKBuildVersion.isAfterV_18_43_J2()) {
            if (obj is SparseLongArray && obj.size() == 0) {
                return true
            }
        }
        if (obj is LongSparseArray<*> && obj.size() == 0) {
            return true
        }
        if (UtilKBuildVersion.isAfterV_16_41_J()) {
            if (obj is android.util.LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        return false
    }
}