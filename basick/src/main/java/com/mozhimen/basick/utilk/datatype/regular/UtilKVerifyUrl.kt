package com.mozhimen.basick.utilk.datatype.regular

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

    /**
     * ip是否合法
     * @param ip String
     * @return Boolean
     */
    @JvmStatic
    fun isIPValid(ip: String) =
        ip.matches(Regex("((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"))

    /**
     * 域名是否合法
     * @param domain String
     * @return Boolean
     */
    @JvmStatic
    fun isDoMainValid(domain: String) =
        domain.matches(Regex("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"))

    /**
     * 端口是否合法
     * @param port String
     * @return Boolean
     */
    @JvmStatic
    fun isPortValid(port: String) =
        port.matches(Regex("^[-+]?[\\d]{1,6}$"))

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
            "输入为空".showToast()
            return false
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "请输入正确的协议(http://或https://)".showToast()
            return false
        }
        val splitArray = url.split(":")
        if (splitArray.size != 3 && splitArray.size != 2) {
            "请输入正确的端口格式".showToast()
            return false
        }
        if (splitArray.getOrNull(0) == null) {
            "请输入正确的端口格式(http或https)接IP或域名".showToast()
            return false
        }
        if (splitArray[0] != "http" && splitArray[0] != "https") {
            "请输入正确的协议(http或https)".showToast()
            return false
        }
        if (splitArray.getOrNull(1) == null) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (!splitArray[1].replace("//", "").isIPValid() && !splitArray[1].replace("//", "").isDoMainValid()) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (splitArray.getOrNull(2) != null && !splitArray[2].isPortValid()) {
            "请输入正确的端口".showToast()
            return false
        }
        return true
    }
}
