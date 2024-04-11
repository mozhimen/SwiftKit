package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKKotlinVersion
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/9
 * @Version 1.0
 */
object UtilKKotlinVersion {
    @JvmStatic
    fun get(): KotlinVersion =
        KotlinVersion.CURRENT

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    @PublishedApi
    @SinceKotlin("1.2")
    internal fun isAtLeast(major: Int, minor: Int, patch: Int): Boolean =
        get().isAtLeast(major, minor, patch)
}