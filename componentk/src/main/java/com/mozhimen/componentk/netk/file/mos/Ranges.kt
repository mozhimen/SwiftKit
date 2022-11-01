package com.mozhimen.componentk.netk.file.mos


/**
 * @ClassName Ranges
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 19:03
 * @Version 1.0
 */
data class Ranges(
    val start: LongArray,
    val end: LongArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ranges

        if (!start.contentEquals(other.start)) return false
        if (!end.contentEquals(other.end)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.contentHashCode()
        result = 31 * result + end.contentHashCode()
        return result
    }
}