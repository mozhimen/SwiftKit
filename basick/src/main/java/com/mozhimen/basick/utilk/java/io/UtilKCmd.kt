package com.mozhimen.basick.utilk.java.io

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Method

/**
 * @ClassName UtilKCmd
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 12:31
 * @Version 1.0
 */
object UtilKCmd {
    private val TAG = "UtilKCmd>>>>>"
    private const val KEY_SYSTEM_PROPERTIES = "android.os.SystemProperties"



    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun setSystemProperties(key: String, value: String) {
        try {
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
            val setMethod: Method = clazz.getMethod("set", String::class.java, String::class.java)
            setMethod.invoke(clazz, key, value)
        } catch (e: Exception) {
            Log.e(TAG, "setSystemProperties Exception ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 获取首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getSystemProperties(key: String, defaultValue: String): String =
        try {
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
            val getMethod: Method = clazz.getMethod("get", String::class.java)
            (getMethod.invoke(clazz, key) as String).ifEmpty { defaultValue }
        } catch (e: Exception) {
            Log.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    /**
     * 获取首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getSystemPropertiesBool(key: String, defaultValue: Boolean): Boolean =
        try {
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
            val getMethod: Method = clazz.getMethod("get", String::class.java)
            val resStr = getMethod.invoke(clazz, key) as String
            if (resStr.isNotEmpty()) {
                java.lang.Boolean.parseBoolean(resStr)
            } else defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    /**
     * 发射命令
     * @param cmd String
     */
    @JvmStatic
    fun executeShellCmd(cmd: String) {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(arrayOf("sh", "-c", cmd))
        } catch (var8: IOException) {
            var8.printStackTrace()
        }
        var data = ""
        val inputStream = BufferedReader(InputStreamReader(process!!.inputStream))
        val errorStream = BufferedReader(InputStreamReader(process.errorStream))
        var line: String
        var error: String
        try {
            while (inputStream.readLine().also { line = it } != null && !TextUtils.isEmpty(line)) {
                data = """
                $data$line
                
                """.trimIndent()
            }
            while (errorStream.readLine().also { error = it } != null && !TextUtils.isEmpty(error)) {
                data = """
                $data$error
                
                """.trimIndent()
            }
        } catch (e: IOException) {
            Log.e(TAG, "executeShellCmd: IOException ${e.message}")
            e.printStackTrace()
        }
    }
}