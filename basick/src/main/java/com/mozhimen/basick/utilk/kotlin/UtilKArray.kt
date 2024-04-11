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

//////////////////////////////////////////////////////////////////

object UtilKArray {
    //聚合array
    @JvmStatic
    fun <T> array2str(array: Array<T>, defaultValue: String = "", splitChar: String = ","): String =
        UtilKCollections.list2str(array.toList(), defaultValue, splitChar)
}