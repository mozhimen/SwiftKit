package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.kotlin.collections.UtilKList

/**
 * @ClassName UtilKArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:54
 * @Version 1.0
 */
fun <T> Array<T>.joinArray2str(defaultValue: String = "", splitChar: String = ","): String =
        UtilKArray.joinArray2str(this, defaultValue, splitChar)

object UtilKArray {
    /**
     * 聚合array
     */
    @JvmStatic
    fun <T> joinArray2str(array: Array<T>, defaultValue: String = "", splitChar: String = ","): String =
            UtilKList.joinList2str(array.toList(), defaultValue, splitChar)

}