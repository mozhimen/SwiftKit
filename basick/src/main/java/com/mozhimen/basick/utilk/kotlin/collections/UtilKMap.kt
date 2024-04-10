package com.mozhimen.basick.utilk.kotlin.collections

/**
 * @ClassName UtilKMap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 16:15
 * @Version 1.0
 */
fun <K, V> Map<K, V>.map2str(): String =
    UtilKMap.map2str(this)

////////////////////////////////////////////////////////////////////////

object UtilKMap {
    @JvmStatic
    fun <K, V> map2str(map: Map<K, V>, defaultValue: String = "", splitChar: String = ",",splitCharKV: String = ":"): String {
        if (map.isEmpty()) return defaultValue
        val stringBuilder = StringBuilder()
        val iterator: Iterator<*> = map.entries.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next() as Map.Entry<*, *>
            stringBuilder.append("$key $splitCharKV $value").append(splitChar)
            stringBuilder.append("\n")
        }
        if (stringBuilder.isNotEmpty())
            stringBuilder.deleteAt(stringBuilder.length - 1).toString()
        return stringBuilder.toString()
    }
}