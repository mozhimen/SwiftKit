package com.mozhimen.basick.utilk.kotlin.collections

/**
 * @ClassName UtilKMap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 16:15
 * @Version 1.0
 */
fun Map<*, *>.map2str(): String =
    UtilKMap.map2str(this)

object UtilKMap {
    /**
     * map2Str
     * @param map Map<*, *>
     * @return String
     */
    @JvmStatic
    fun map2str(map: Map<*, *>): String {
        if (map.isEmpty()) return "map is empty"
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n").append("{").append("\n").append("\t")
        val iterator: Iterator<*> = map.entries.iterator()
        while (iterator.hasNext()) {
            val (key, value) = iterator.next() as Map.Entry<*, *>
            stringBuilder.append(String.format("\t%1\$s : %2\$s", key.toString(), value.toString()))
            stringBuilder.append("\n")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}