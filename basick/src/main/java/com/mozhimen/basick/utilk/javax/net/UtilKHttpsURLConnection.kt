package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseHostnameVerifier
import com.mozhimen.basick.utilk.kotlin.strUrl2uRL
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory


object UtilKHttpsURLConnection {
    @JvmStatic
    fun get_ofSSL(strUrl: String, connectTimeout: Int = 1000, readTimeout: Int = 1000): HttpsURLConnection {
        val uRL = strUrl.strUrl2uRL()
        val httpURLConnection = uRL.openConnection() as HttpsURLConnection
        return httpURLConnection.apply {
            this.hostnameVerifier = BaseHostnameVerifier()
            this.sslSocketFactory = UtilKSSLSocketFactory.get_ofSSL()//获取SSLSocketFactory对象
            this.connectTimeout = connectTimeout // 设置超时时间
            this.readTimeout = readTimeout
        }
    }

    @JvmStatic
    fun applyDefaultHostnameVerifier_SSLSocketFactory(hostnameVerifier: HostnameVerifier, sslSocketFactory: SSLSocketFactory) {
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier)
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory)
    }
}