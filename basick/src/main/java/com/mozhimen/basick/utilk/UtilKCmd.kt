package com.mozhimen.basick.utilk

import android.annotation.SuppressLint
import android.app.Activity
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
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
    private const val KEY_SYSTEM_PROPERTIES = "android.os.SystemProperties"

    private const val CMD_FILL_LIGHT_OPEN = "echo 255 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"
    private const val CMD_FILL_LIGHT_CLOSE = "echo 0 > /sys/devices/platform/pwm-leds/leds/rgb:leds/brightness"

    /**
     * 重启
     */
    @SuppressLint("PrivateApi")
    fun reboot() {
        try {
            val c = Class.forName(KEY_SYSTEM_PROPERTIES)
            val set: Method = c.getMethod("set", String::class.java, String::class.java)
            set.invoke(c, "sys.powerctl", "reboot")
        } catch (var2: Exception) {
            var2.printStackTrace()
        }
    }

    /**
     * 自启动
     * @return Boolean
     */
    @SuppressLint("PrivateApi")
    fun autoRun(): Boolean {
        try {
            val c = Class.forName(KEY_SYSTEM_PROPERTIES)
            val get: Method = c.getMethod("get", String::class.java)
            val autorun = get.invoke(c, "persist.sensepass.autorun") as String
            if (!TextUtils.isEmpty(autorun)) {
                return java.lang.Boolean.parseBoolean(autorun)
            }
        } catch (var3: Exception) {
            var3.printStackTrace()
        }
        return true
    }

    /**
     * 开补光灯
     */
    fun openFillLight() {
        executeShellCmd(CMD_FILL_LIGHT_OPEN)
    }

    /**
     * 关补光灯
     */
    fun closeFillLight() {
        executeShellCmd(CMD_FILL_LIGHT_CLOSE)
    }

    /**
     * 设置屏幕亮度
     * @param paramFloat Float 0-1范围
     * @param activity Activity
     */
    fun setScreenBrightness(paramFloat: Float, activity: Activity) {
        val localWindow: Window = activity.window
        val params: WindowManager.LayoutParams = localWindow.attributes
        params.screenBrightness = paramFloat
        localWindow.attributes = params
    }

    private fun executeShellCmd(cmd: String) {
        var p: Process? = null
        try {
            p = Runtime.getRuntime().exec(arrayOf("sh", "-c", cmd))
        } catch (var8: IOException) {
            var8.printStackTrace()
        }
        var data = ""
        val `in` = BufferedReader(InputStreamReader(p!!.inputStream))
        val er = BufferedReader(InputStreamReader(p.errorStream))
        var line: String
        var error: String
        try {
            while (`in`.readLine().also { line = it } != null && !TextUtils.isEmpty(line)) {
                data = """
                $data$line
                
                """.trimIndent()
            }
            while (er.readLine().also { error = it } != null && !TextUtils.isEmpty(error)) {
                data = """
                $data$error
                
                """.trimIndent()
            }
        } catch (var9: IOException) {
            var9.printStackTrace()
        }
    }
}