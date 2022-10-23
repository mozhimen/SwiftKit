package com.mozhimen.componentk.netk.coroutine.helpers

import android.annotation.SuppressLint
import com.mozhimen.componentk.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @ClassName ClientBuilder
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 18:31
 * @Version 1.0
 */
object ClientBuilder {

    /*fun build(interceptors: MutableList<Interceptor>? = null): OkHttpClient {
        val builder: OkHttpClient.Builder = getUnsafeOkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG) {
            interceptors?.forEach {
                builder.addInterceptor(it)
            }
        }
        return builder.build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        //创建不验证证书链的信任管理器
        val trustAllCerts = arrayOf<TrustManager>(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )


        val sslContext = SSLContext.getInstance("SSL")//安装全信任信任管理器
        sslContext.init(null, trustAllCerts, SecureRandom())

        val builder = OkHttpClient.Builder()//使用我们的信任管理器创建一个ssl套接字工厂
        builder.hostnameVerifier(SSLSocket)
        builder.build()
    }*/
}