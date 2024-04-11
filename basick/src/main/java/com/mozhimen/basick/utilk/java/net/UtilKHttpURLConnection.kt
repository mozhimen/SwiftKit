package com.mozhimen.basick.utilk.java.net

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.WorkerThread
import com.mozhimen.basick.elemk.javax.net.bases.BaseHostnameVerifier
import com.mozhimen.basick.lintk.optins.application.OApplication_USES_CLEAR_TEXT_TRAFFIC
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFileWrapper
import com.mozhimen.basick.utilk.java.io.UtilKInputStream
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.inputStream2str_use_ofBufferedReader
import com.mozhimen.basick.utilk.javax.net.UtilKSSLContext
import com.mozhimen.basick.utilk.javax.net.UtilKSSLSocketFactory
import com.mozhimen.basick.utilk.kotlin.UtilKStrUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
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
    fun get(strUrl: String, connectTimeout: Int = 1000, readTimeout: Int = 1000): HttpURLConnection {
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

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OApplication_USES_CLEAR_TEXT_TRAFFIC
    suspend fun getStrIPOnBack(): String =
        withContext(Dispatchers.IO) { getStrIP() }

    /**
     * 获取外网ip地址（非本地局域网地址）的方法
     */
    @JvmStatic
    @WorkerThread
    @OApplication_USES_CLEAR_TEXT_TRAFFIC
    fun getStrIP(): String {
        var ipAddress = ""
        try {
            val result = request_ofGet("https://whois.pconline.com.cn/ipJson.jsp?json=true", null, 30000, 30000)
            ipAddress = JSONObject(result).getString("ip")
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "getStrIP 获取IP地址时出现异常, 异常信息是：$e")
        }
        return ipAddress.also { UtilKLogWrapper.d(TAG, "getStrIP $ipAddress") }
    }

    @JvmStatic
    fun getFile_ofStrUrl(strUrl: String, fileDest: File, isAppend: Boolean = false): File? {
        require(strUrl.isNotEmpty()) { "${UtilKStrUrl.TAG} httpUrl must be not empty" }
        UtilKFileWrapper.deleteFile(fileDest)

        var httpURLConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            if (httpURLConnection is HttpsURLConnection) {
                httpURLConnection.sslSocketFactory = UtilKSSLSocketFactory.get_ofTLS()
            }
            httpURLConnection.apply {
                connectTimeout = 60 * 1000
                readTimeout = 60 * 1000
                connect()
            }
            UtilKInputStream.read_write_use(httpURLConnection.inputStream, fileDest.file2fileOutputStream(isAppend), 1024)
            return fileDest
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
        }
        return null
    }

    @JvmStatic
    fun request_ofGet(strUrl: String, headers: Map<String, String>?, connectTimeout: Int = 1000, readTimeout: Int = 1000): String {
        var httpURLConnection: HttpURLConnection? = null
        try {
            httpURLConnection = get(strUrl, connectTimeout, readTimeout)
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.useCaches = false
            headers?.forEach { (key, value) ->//配置参数
                httpURLConnection.setRequestProperty(key, value)
            }
            httpURLConnection.connect()//链接

            val inputStream =
                if (httpURLConnection.responseCode == 200)
                    httpURLConnection.inputStream
                else httpURLConnection.errorStream

            return inputStream.inputStream2str_use_ofBufferedReader()
        } catch (e: MalformedURLException) {
            e.printStackTrace() // url格式错误
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
        }
        return ""
    }

    @JvmStatic
    fun request_ofPost(strUrl: String, headers: Map<String, String>?, params: Map<String, String>?, connectTimeout: Int = 1000, readTimeout: Int = 1000): String {
        var httpURLConnection: HttpURLConnection? = null
        var jsonObject: JSONObject? = null
        var outputStreamWriter: OutputStreamWriter? = null
        try {
            httpURLConnection = get(strUrl, connectTimeout, readTimeout)
            httpURLConnection.requestMethod = "POST"

            /*//            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8")// 设置通用请求类型
            //            httpURLConnection.setRequestProperty("Charset", "UTF-8")
            //            httpURLConnection.setRequestProperty("connection", "keep-alive")// 设置链接状态*/
            headers?.forEach { (key, value) ->
                httpURLConnection.setRequestProperty(key, value)
            }
            if (params != null) {
                jsonObject = JSONObject()
                params.forEach { (key, value) ->
                    jsonObject.put(key, value)
                }
            }

            httpURLConnection.doOutput = true// post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.doInput = true// 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.useCaches = false// Post 请求不能使用缓存
//            httpURLConnection.instanceFollowRedirects = true// 设置本次连接是否自动处理重定向
            httpURLConnection.connect()

            if (jsonObject != null) {
                outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream, "UTF-8")
                outputStreamWriter.write(jsonObject.toString())
            }
            UtilKLogWrapper.d(jsonObject.toString())

            val inputStream = if (httpURLConnection.responseCode == 200)
                httpURLConnection.inputStream
            else httpURLConnection.errorStream
            return inputStream.inputStream2str_use_ofBufferedReader()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStreamWriter?.flushClose()
            httpURLConnection?.disconnect()// 关闭相应的流
        }
        return ""
    }
}