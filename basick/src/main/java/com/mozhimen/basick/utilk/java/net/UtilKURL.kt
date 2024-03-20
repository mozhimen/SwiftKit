package com.mozhimen.basick.utilk.java.net

import java.io.InputStream
import java.net.URL

/**
 * @ClassName UtilKURL
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/20
 * @Version 1.0
 */
object UtilKURL {
    @JvmStatic
    fun get(strUrl: String): URL =
        URL(strUrl)

    @JvmStatic
    fun openStream(strUrl: String): InputStream =
        get(strUrl).openStream()
}