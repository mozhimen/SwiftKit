package com.mozhimen.basick.utilk.java.security

import java.security.MessageDigest

/**
 * @ClassName UtilKMessageDigest
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:01
 * @Version 1.0
 */
object UtilKMessageDigest {
    fun get_ofMD5(): MessageDigest =
        MessageDigest.getInstance("MD5")
}