package com.mozhimen.basick.utilk.kotlin.text

import android.util.Log
import androidx.annotation.MainThread
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.getSplitFirst
import java.net.URI
import java.net.URISyntaxException

/**
 * @ClassName Verifier
 * @Description 密码校验
 * @Author mozhimen
 * @Date 2021/4/21 13:59
 * @Version 1.0
 */
fun String.isIP(): Boolean =
    UtilKVerifyUrl.isIP(this)

fun String.isDoMain(): Boolean =
    UtilKVerifyUrl.isDoMain(this)

fun String.isPort(): Boolean =
    UtilKVerifyUrl.isPort(this)

fun String.isUrl(): Boolean =
    UtilKVerifyUrl.checkUrl(this)

object UtilKVerifyUrl : BaseUtilK() {
    /**
     * ip是否合法
     * @param ip String
     * @return Boolean
     */
    @JvmStatic
    fun isIP(ip: String) =
        ip.matches(Regex("((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"))

    /**
     * 域名是否合法
     * @param domain String
     * @return Boolean
     */
    @JvmStatic
    fun isDoMain(domain: String) =
        domain.matches(Regex("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"))

    /**
     * 端口是否合法
     * @param port String
     * @return Boolean
     */
    @JvmStatic
    fun isPort(port: String) =
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
            e.message?.et(TAG)
            return false
        }
        if (uri.host == null) {
            return false
        } else if (!uri.scheme.equals("http") && !uri.scheme.equals("https") && !uri.scheme.equals("tcp") && !uri.scheme.equals("udp")) {
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
    fun checkUrl(url: String): Boolean {
        Log.d(TAG, "isUrlValid: url $url")
        if (url.isEmpty()) {
            "输入为空".showToast()
            return false
        }
        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("tcp://")) {
            "请输入正确的协议头".showToast()
            return false
        }
        val splitArray = url.split(":")
        if (splitArray.size < 2) {
            "请输入正确的端口格式".showToast()
            return false
        }
        //first:http
        if (splitArray.getOrNull(0) == null) {
            "请输入正确的端口格式(http/https/tcp)接IP或域名".showToast()
            return false
        }
        val first = splitArray[0]
        if (first != "http" && first != "https" && first != "tcp") {
            "请输入正确的协议(http/https/tcp)".showToast()
            return false
        }
        //second:ip
        if (splitArray.getOrNull(1) == null) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        var second = splitArray[1].replace("//", "")
        if (second.contains("/")) {
            second = second.getSplitFirst("/")
        }
        if (!second.isIP() && !second.isDoMain()) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (splitArray.getOrNull(2) != null) {
            var third = splitArray[2]
            if (third.contains("/")) {
                third = third.getSplitFirst("/")
            }
            if (!third.isPort()) {
                "请输入正确的端口".showToast()
                return false
            }
        }
        return true
    }
}
