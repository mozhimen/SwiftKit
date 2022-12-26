package com.mozhimen.basick.utilk.regular

import android.util.Log
import androidx.annotation.MainThread
import com.mozhimen.basick.utilk.exts.isDoMainValid
import com.mozhimen.basick.utilk.exts.isIPValid
import com.mozhimen.basick.utilk.exts.isPortValid
import com.mozhimen.basick.utilk.exts.showToast
import java.net.URI
import java.net.URISyntaxException

/**
 * @ClassName Verifier
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/21 13:59
 * @Version 1.0
 */
/**
 * 密码校验
 */
object UtilKVerifyUrl {
    private val TAG = "UtilKVerifyUrl>>>>>"
    private const val REGEX_IP =
        "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"//IP验证

    private const val REGEX_DOMAIN =
        "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"//域名验证

    private const val REGEX_PORT = "^[-+]?[\\d]{1,6}$"//端口号验证

    /**
     * ip是否合法
     * @param ip String
     * @return Boolean
     */
    @JvmStatic
    fun isIPValid(ip: String) = ip.matches(Regex(REGEX_IP))

    /**
     * 域名是否合法
     * @param domain String
     * @return Boolean
     */
    @JvmStatic
    fun isDoMainValid(domain: String) = domain.matches(Regex(REGEX_DOMAIN))

    /**
     * 端口是否合法
     * @param port String
     * @return Boolean
     */
    @JvmStatic
    fun isPortValid(port: String) = port.matches(Regex(REGEX_PORT))

    /**
     * 判断url是否可连
     * @param url String
     * @return Boolean
     */
    @JvmStatic
    fun isUrlAvailable(url: String): Boolean {
        val uri: URI?
        try {
            uri = URI(url)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return false
        }
        if (uri.host == null) {
            return false
        } else if (!uri.scheme.equals("http") && !uri.scheme.equals("https")) {
            return false
        }
        return true
    }

    /**
     * 判断url是否合法
     * @param url String
     * @return Boolean
     */
    @JvmStatic
    @MainThread
    fun isUrlValid(url: String): Boolean {
        Log.d(TAG, "isUrlValid: url $url")
        if (url.isEmpty()) {
            Log.d(TAG, "isUrlValid: 0")
            "输入为空".showToast()
            return false
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            Log.d(TAG, "isUrlValid: 1")
            "请输入正确的协议(http://或https://)".showToast()
            return false
        }
        val splitArray = url.split(":")
        if (splitArray.size != 3 && splitArray.size != 2) {
            Log.d(TAG, "isUrlValid: 2")
            "请输入正确的端口格式".showToast()
            return false
        }
        if (splitArray.getOrNull(0) == null) {
            Log.d(TAG, "isUrlValid: 3")
            "请输入正确的端口格式(http或https)接IP或域名".showToast()
            return false
        }
        if (splitArray[0] != "http" && splitArray[0] != "https") {
            Log.d(TAG, "isUrlValid: 4")
            "请输入正确的协议(http或https)".showToast()
            return false
        }
        if (splitArray.getOrNull(1) == null) {
            Log.d(TAG, "isUrlValid: 5")
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (!splitArray[1].replace("//", "").isIPValid() && !splitArray[1].replace("//", "").isDoMainValid()) {
            Log.d(TAG, "isUrlValid: 6")
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (splitArray[2].getOrNull(2) != null && !splitArray[2].isPortValid()) {
            Log.d(TAG, "isUrlValid: 7")
            "请输入正确的端口".showToast()
            return false
        }
        return true
    }
}
