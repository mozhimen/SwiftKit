package com.mozhimen.componentktest.netk.http.customs

import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.permissionk.cons.CApplication
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.componentk.netk.http.NetKHttp

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
@APermissionRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
object ApiFactory {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"

    val netk = NetKHttp(_baseUrl)

    val api = netk.create(Apis::class.java)

//    private val _baseUrl get() = Config.restfulSP.baseUrl
//
//    val bizApi: BizApis by lazy {  NetKHttp(_baseUrl).create(BizApis::class.java) }
}