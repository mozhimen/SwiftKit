package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseHostnameVerifier
import com.mozhimen.basick.utilk.java.io.inputStream2strOfReadMultiLines
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
    fun get(strUrl: String,connectTimeout: Int=1000,readTimeout: Int = 1000):HttpsURLConnection{
        val uRL = URL(strUrl)
        val httpURLConnection = uRL.openConnection() as HttpsURLConnection
        return httpURLConnection.apply {
            this.hostnameVerifier =  BaseHostnameVerifier()
            this.sslSocketFactory = UtilKSSLContext.generateTLS().socketFactory//获取SSLSocketFactory对象
            this.connectTimeout = connectTimeout // 设置超时时间
            this.readTimeout = readTimeout
        }
    }
    @JvmStatic
    fun applyDefault(hostnameVerifier: HostnameVerifier, sslSocketFactory:SSLSocketFactory){
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier)
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory)
    }

    @JvmStatic
    @Throws(IOException::class, NoSuchAlgorithmException::class, KeyManagementException::class)
    fun requestGet(strUrl: String, headers: Map<String, String>?, connectTimeout:Int=1000, readTimeout:Int=1000): String {
        var httpsURLConnection: HttpsURLConnection? = null
        var inputStream: InputStream? = null
        try {
            httpsURLConnection = get(strUrl,connectTimeout, readTimeout)
            httpsURLConnection.requestMethod = "GET"
            if (headers != null) {
                for ((key, value) in headers) {
                    httpsURLConnection.setRequestProperty(key, value)
                }
            }
/*            //            httpsURLConnection.setDoOutput(true);
//            httpsURLConnection.setDoInput(true);
//            httpsURLConnection.setUseCaches(false);*/
            httpsURLConnection.connect()

            inputStream = if (httpsURLConnection.responseCode == HttpURLConnection.HTTP_OK)
                httpsURLConnection.inputStream
            else httpsURLConnection.errorStream
            return inputStream.inputStream2strOfReadMultiLines()

        } catch (e: MalformedURLException) {
            e.printStackTrace() // url格式错误
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            httpsURLConnection?.disconnect()
        }
        return ""
    }
}