package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseHostnameVerifier
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import com.mozhimen.basick.utilk.kotlin.strUrl2uRL
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSocketFactory


object UtilKHttpsURLConnection {
    @JvmStatic
    fun get(strUrl: String, connectTimeout: Int = 1000, readTimeout: Int = 1000): HttpsURLConnection {
        val uRL = strUrl.strUrl2uRL()
        val httpURLConnection = uRL.openConnection() as HttpsURLConnection
        return httpURLConnection.apply {
            this.hostnameVerifier = BaseHostnameVerifier()
            this.sslSocketFactory = UtilKSSLSocketFactory.get_ofTLS()//获取SSLSocketFactory对象
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