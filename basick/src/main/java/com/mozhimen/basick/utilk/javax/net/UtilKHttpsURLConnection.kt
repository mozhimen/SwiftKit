package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseX509TrustManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager


object UtilKHttpsURLConnection {
    @JvmStatic
    @Throws(IOException::class, NoSuchAlgorithmException::class, KeyManagementException::class)
    fun httpsGet(url: String?, headers: Map<String?, String?>?, params: Map<String?, String?>?): String? {
        val ignoreHostnameVerifier = HostnameVerifier { s, sslsession ->
            println("WARNING: Hostname is not matched for cert.")
            true
        }
        //创建SSLContext
        val sslContext = SSLContext.getInstance("TLS")
        val trustManagers = arrayOf<TrustManager>(BaseX509TrustManager())
        //初始化
        sslContext.init(null, trustManagers, SecureRandom())
        //获取SSLSocketFactory对象
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier)
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory)
        var result = ""
        var httpsURLConnection: HttpsURLConnection? = null
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        var inputStreamReader: InputStreamReader? = null
        try {
            val httpUrl = URL(url)
            httpsURLConnection = httpUrl.openConnection() as HttpsURLConnection
            httpsURLConnection.requestMethod = "GET"
            if (headers != null) {
                for ((key, value) in headers) {
                    httpsURLConnection.setRequestProperty(key, value)
                }
            }
            httpsURLConnection.connectTimeout = 2000 // 设置超时时间
            httpsURLConnection.readTimeout = 2000
            //            httpsURLConnection.setDoOutput(true);
//            httpsURLConnection.setDoInput(true);
//            httpsURLConnection.setUseCaches(false);
            httpsURLConnection.hostnameVerifier = ignoreHostnameVerifier
            httpsURLConnection.sslSocketFactory = sslSocketFactory
            httpsURLConnection.connect()
            inputStream = if (httpsURLConnection.responseCode == HttpURLConnection.HTTP_OK) httpsURLConnection.inputStream else httpsURLConnection.errorStream
            inputStreamReader = InputStreamReader(inputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            result = stringBuilder.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace() // url格式错误
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStreamReader?.close()
            bufferedReader?.close()
            inputStream?.close()
            httpsURLConnection?.disconnect()
        }
        return result
    }
}