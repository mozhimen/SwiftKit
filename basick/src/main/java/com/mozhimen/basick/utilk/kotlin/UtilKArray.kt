package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.kotlin.collections.UtilKCollections

/**
 * @ClassName UtilKArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:54
 * @Version 1.0
 */
fun <T> Array<T>.array2str(defaultValue: String = "", splitChar: String = ","): String =
    UtilKArray.array2str(this, defaultValue, splitChar)

fun <T> Array<T>.array2indexedMap(): Map<Int, T> =
    UtilKArray.array2indexedMap(this)

//////////////////////////////////////////////////////////////////

object UtilKArray {
    //聚合array
    @JvmStatic
    fun <T> array2str(array: Array<T>, defaultValue: String = "", splitChar: String = ","): String =
        UtilKCollections.list2str(array.toList(), defaultValue, splitChar)

    fun <T> array2indexedMap(array: Array<T>): Map<Int, T> =
        array.mapIndexed { index, e -> index to e }.toMap()
}