package com.mozhimen.basick.utilk

/**
 * @ClassName UtilKCollections
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:24
 * @Version 1.0
 */
object UtilKCollections {

    @JvmStatic
    fun map2String(map: Map<*, *>): String {
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

    @JvmStatic
    fun list2String(list: List<*>): String {
        if (list.isEmpty()) return "list is empty"
        val stringBuilder = StringBuilder()
        stringBuilder.append("\n").append("{\n ")
        for (obj in list) {
            if (obj is List<*>) stringBuilder.append(list2String(obj))
            else stringBuilder.append(obj.toString()).append(" ,\n ")
        }
        stringBuilder.append("}")
        return stringBuilder.toString()
    }
}