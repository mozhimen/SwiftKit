package com.mozhimen.basick.utilk.kotlin.collections

import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import java.util.stream.Collectors

/**
 * @ClassName UtilKList
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:55
 * @Version 1.0
 */
fun <T> List<T>.joinList2str(defaultValue: String = "", splitStr: String = ","): String =
        UtilKList.joinList2str(this, defaultValue, splitStr)

object UtilKList {
    /**
     * 聚合list
     * @param list List<T>
     * @param defaultValue String
     * @param splitChar String
     * @return String
     */
    @JvmStatic
    fun <T> joinList2str(list: List<T>, defaultValue: String = "", splitChar: String = ","): String =
            if (UtilKBuildVersion.isAfterV_24_7_N()) {
                val ret = list.stream().map { elem: T? -> elem?.toString() ?: "" }
                        .collect(Collectors.joining(splitChar))
                ret.ifEmpty { defaultValue }
            } else {
                val stringBuilder = StringBuilder()
                for (obj in list) stringBuilder.append(obj?.toString() ?: "").append(splitChar)
                if (stringBuilder.isNotEmpty()) stringBuilder.deleteAt(stringBuilder.length - 1).toString() else defaultValue
            }

    /**
     * list2Str
     * @param list List<*>
     * @return String
     */
    @JvmStatic
    fun list2str(list: List<*>): String {
        if (list.isEmpty()) return "list is empty"
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n").append("{\n ")
        for (obj in list) {
            if (obj is List<*>) stringBuilder.append(list2str(obj))
            else stringBuilder.append(obj.toString()).append(" ,\n ")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}