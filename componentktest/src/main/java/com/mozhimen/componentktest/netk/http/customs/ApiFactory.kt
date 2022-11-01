package com.mozhimen.componentktest.netk.http.customs

import com.mozhimen.componentk.netk.http.NetKHttp

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"

    val netk = NetKHttp(_baseUrl)

    val api = netk.create(Apis::class.java)
}