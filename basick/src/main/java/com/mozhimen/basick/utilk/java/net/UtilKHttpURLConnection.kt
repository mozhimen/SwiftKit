package com.mozhimen.basick.utilk.java.net

import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * @ClassName UtilKHttpURLConnection
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/18 19:29
 * @Version 1.0
 */
object UtilKHttpURLConnection {
    @JvmStatic
    fun post(url: String, headers: Map<String, String>?, params: Map<String, String>?): String {
        var result = ""
        var httpURLConnection: HttpURLConnection? = null
        var jsonObject: JSONObject? = null
        var outputStreamWriter: OutputStreamWriter? = null
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        var inputStreamReader: InputStreamReader? = null
        try {
            val httpUrl = URL(url)
            httpURLConnection = httpUrl.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"

//            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8")// 设置通用请求类型
//            httpURLConnection.setRequestProperty("Charset", "UTF-8")
//            httpURLConnection.setRequestProperty("connection", "keep-alive")// 设置链接状态
            headers?.forEach { (t, u) ->
                httpURLConnection.setRequestProperty(t, u)
            }
            if (params != null) {
                jsonObject = JSONObject()
                params.forEach { (t, u) ->
                    jsonObject.put(t, u)
                }
            }

            httpURLConnection.connectTimeout = 60000// 设置超时时间
            httpURLConnection.readTimeout = 60000

            httpURLConnection.doOutput = true// post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            httpURLConnection.doInput = true// 设置是否从httpUrlConnection读入，默认情况下是true;

            httpURLConnection.useCaches = false// Post 请求不能使用缓存
//            httpURLConnection.instanceFollowRedirects = true// 设置本次连接是否自动处理重定向
            httpURLConnection.connect()

            if (jsonObject != null) {
                outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream, "UTF-8")
                outputStreamWriter.write(jsonObject.toString())
            }

            inputStream = if (httpURLConnection.responseCode == 200) httpURLConnection.getInputStream() else httpURLConnection.getErrorStream()
            inputStreamReader = InputStreamReader(inputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            result = stringBuilder.toString()
        } catch (e: MalformedURLException) {
            e.printStackTrace()// url格式错误
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStreamReader?.close()
            bufferedReader?.close()
            inputStream?.close()
            outputStreamWriter?.flush()
            outputStreamWriter?.close()
            httpURLConnection?.disconnect()// 关闭相应的流
        }
        return result
    }
}