package com.mozhimen.componentk.navigatek.helpers

/**
 * @ClassName DestinationUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/25 15:58
 * @Version 1.0
 */
fun Class<*>.getDestinationId(): Int {
    return DestinationUtil.getId(this)
}

object DestinationUtil {
    @JvmStatic
    fun getId(clazz: Class<*>): Int {
        return kotlin.math.abs(clazz.name.hashCode())
    }
}