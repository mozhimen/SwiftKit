package com.mozhimen.basick.utilk.java.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseHostnameVerifier
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.javax.net.UtilKSSLSocketFactory
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection


/**
 * @ClassName UtilKHttpURLConnection
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/18 19:29
 * @Version 1.0
 */
object UtilKHttpURLConnection : IUtilK {
    @JvmStatic
    fun get_ofTLS(strUrl: String, connectTimeout: Int = 1000, readTimeout: Int = 1000): HttpURLConnection {
        val uRL = UtilKURL.get(strUrl)
        val httpURLConnection = uRL.openConnection() as HttpURLConnection
        if (httpURLConnection is HttpsURLConnection) {
            httpURLConnection.hostnameVerifier = BaseHostnameVerifier()
            httpURLConnection.sslSocketFactory = UtilKSSLSocketFactory.get_ofTLS()//获取SSLSocketFactory对象
        }
        return httpURLConnection.apply {
            this.connectTimeout = connectTimeout // 设置超时时间
            this.readTimeout = readTimeout
        }
    }
}