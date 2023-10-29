package com.mozhimen.basick.utilk.kotlin.text

import android.util.Log
import androidx.annotation.MainThread
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.getSplitFirst

/**
 * @ClassName Verifier
 * @Description 密码校验
 * @Author mozhimen
 * @Date 2021/4/21 13:59
 * @Version 1.0
 */
fun String.isStrUrlIp(): Boolean =
    UtilKMatchStrUrl.isStrUrlIp(this)

fun String.isStrUrlDomain(): Boolean =
    UtilKMatchStrUrl.isStrUrlDomain(this)

fun String.isStrUrlPort(): Boolean =
    UtilKMatchStrUrl.isStrUrlPort(this)

fun String.checkStrUrl(): Boolean =
    UtilKMatchStrUrl.checkStrUrl(this)

object UtilKMatchStrUrl : BaseUtilK() {
    /**
     * ip是否合法
     */
    @JvmStatic
    fun isStrUrlIp(ip: String) =
        ip.matches(Regex("((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"))

    /**
     * 域名是否合法
     */
    @JvmStatic
    fun isStrUrlDomain(domain: String) =
        domain.matches(Regex("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"))

    /**
     * 端口是否合法
     */
    @JvmStatic
    fun isStrUrlPort(port: String) =
        port.matches(Regex("^[-+]?[\\d]{1,6}$"))

    /**
     * 判断url是否合法
     */
    @JvmStatic
    @MainThread
    fun checkStrUrl(url: String): Boolean {
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
        if (!second.isStrUrlIp() && !second.isStrUrlDomain()) {
            "请输入正确的IP或域名".showToast()
            return false
        }
        if (splitArray.getOrNull(2) != null) {
            var third = splitArray[2]
            if (third.contains("/")) {
                third = third.getSplitFirst("/")
            }
            if (!third.isStrUrlPort()) {
                "请输入正确的端口".showToast()
                return false
            }
        }
        return true
    }
}
