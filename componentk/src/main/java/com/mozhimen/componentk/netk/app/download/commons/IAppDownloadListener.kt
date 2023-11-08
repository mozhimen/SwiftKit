package com.mozhimen.componentk.netk.app.download.commons

/**
 * @ClassName IAppDownloadListener
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/7 14:11
 * @Version 1.0
 */
interface IAppDownloadListener {
    fun onSuccess()

    fun onFail(code: Int)
}