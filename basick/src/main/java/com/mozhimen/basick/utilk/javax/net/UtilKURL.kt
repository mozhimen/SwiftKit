package com.mozhimen.basick.utilk.javax.net

import java.net.URL

/**
 * @ClassName UtilKURL
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/9
 * @Version 1.0
 */
object UtilKURL {
    @JvmStatic
    fun get(strUrl: String): URL =
        URL(strUrl)
}