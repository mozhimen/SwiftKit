package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.java.net.cons.CHttpURLConnection
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.HttpsURLConnection

/**
 * @ClassName UtilKHttpsURLConnectionWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/9
 * @Version 1.0
 */
object UtilKHttpsURLConnectionWrapper {
    @JvmStatic
    @Throws(IOException::class, NoSuchAlgorithmException::class, KeyManagementException::class)
    fun requestGet(strUrl: String, headers: Map<String, String>?, connectTimeout: Int = 1000, readTimeout: Int = 1000): String {
        var httpsURLConnection: HttpsURLConnection? = null
        try {
            httpsURLConnection = UtilKHttpsURLConnection.get(strUrl, connectTimeout, readTimeout)
            httpsURLConnection.requestMethod = "GET"
            if (headers != null) {
                for ((key, value) in headers) {
                    httpsURLConnection.setRequestProperty(key, value)
                }
            }
            /*httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setUseCaches(false);*/
            httpsURLConnection.connect()

            val inputStream: InputStream = if (httpsURLConnection.responseCode == CHttpURLConnection.HTTP_OK)
                httpsURLConnection.inputStream
            else httpsURLConnection.errorStream
            return inputStream.inputStream2str_use_ofBufferedReader()
        } catch (e: MalformedURLException) {
            e.printStackTrace() // url格式错误
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            httpsURLConnection?.disconnect()
        }
        return ""
    }
}