package com.mozhimen.basick.utilk.kotlin.collections

/**
 * @ClassName UtilKMap
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
fun <K, V> Map<K, V?>.filterNotNullValues(): Map<K, V> =
    UtilKMap.filterNotNullValues(this)

inline fun <X, Y, Z, H> Map<X, Y>.zipOnKeys(other: Map<X, Z>, f: (Y, Z) -> H): Map<X, H> =
    UtilKMap.zipOnKeys(this, other, f)

//////////////////////////////////////////////////////////////////////////////

object UtilKMap {
    @JvmStatic
    fun <K, V> filterNotNullValues(map: Map<K, V?>): Map<K, V> {
        val destination = mutableMapOf<K, V>()
        for ((key, value) in map) {
            if (value != null) {
                destination[key] = value
            }
        }
        return destination
    }

    @JvmStatic
    inline fun <X, Y, Z, H> zipOnKeys(map: Map<X, Y>, other: Map<X, Z>, f: (Y, Z) -> H): Map<X, H> =
        map.keys.intersect(other.keys)
            .map { key ->
                key to f(map[key]!!, other[key]!!)
            }
            .toMap()

    @JvmStatic
    fun <K, V> mergeWith(vararg maps: Map<K, V>): HashMap<K, V> {
        val mutableMap = HashMap<K, V>()
        maps.forEach {
            mutableMap.putAll(it)
        }
        return mutableMap
    }
}

fun main() {
    val maps = listOf(
        mutableMapOf(1 to "1", 2 to "2", 3 to "3"),
        mapOf(1 to "2", 4 to "4", 5 to "5")
    )
    val merged: Map<Int, String> = maps.fold(emptyMap()) { acc, next -> acc + next }
    println(merged)
}