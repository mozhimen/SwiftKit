package com.mozhimen.componentktest.netk.http.customs

import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.componentk.netk.http.NetKHttp

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
object ApiFactory {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"

    val netKHttp = NetKHttp(_baseUrl)

    //示例1
    val apis: Apis = netKHttp.create(Apis::class.java)

    //示例2
    val apis1: Apis = netKHttp.create()

    /////////////////////////////////////////////////////////////////

//    private val _baseUrl get() = Config.restfulSP.baseUrl
//
//    val bizApi: BizApis by lazy {  NetKHttp(_baseUrl).create(BizApis::class.java) }
}