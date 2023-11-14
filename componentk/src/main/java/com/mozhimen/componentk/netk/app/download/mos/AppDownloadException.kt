package com.mozhimen.componentk.netk.app.download.mos

/**
 * @ClassName AppDownloadException
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 15:10
 * @Version 1.0
 */
fun Int.int2appDownloadException(): AppDownloadException =
    AppDownloadException(this)

class AppDownloadException(val code: Int, val msg: String = "") : Exception()