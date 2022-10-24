package com.mozhimen.componentktest.netk.customs

import com.mozhimen.componentk.netk.NetKCoroutineFactory

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactorys {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"

    val netkCoroutineFactory by lazy {
        NetKCoroutineFactory(_baseUrl)
    }
}