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
}