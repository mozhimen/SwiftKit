package com.mozhimen.basick.utilk.net

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @ClassName UtilKNetDeal
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/7 23:42
 * @Version 1.0
 */
object UtilKNetDeal {
    /**
     * 获取SSL
     * @return SSLContext?
     */
    @JvmStatic
    fun getSLLContext(): SSLContext? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOfNulls(0)
                    }
                }), SecureRandom()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sslContext
    }
}