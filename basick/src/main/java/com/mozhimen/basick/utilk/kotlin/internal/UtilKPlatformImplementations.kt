package com.mozhimen.basick.utilk.kotlin.internal

/**
 * @ClassName UtilKPlatformImplementations
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 17:57
 * @Version 1.0
 */
object UtilKPlatformImplementations {
    @PublishedApi
    @SinceKotlin("1.2")
    internal fun apiVersionIsAtLeast(major: Int, minor: Int, patch: Int) =
        KotlinVersion.CURRENT.isAtLeast(major, minor, patch)
}