package com.mozhimen.basicsk.extsk

import com.mozhimen.basicsk.utilk.UtilKVerifyUrl

/**
 * @ClassName ExtsKVerifyUrl
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:29
 * @Version 1.0
 */
/**
 * 作用: 校验密码
 * 用法1: "...".isPasswordValid()
 *       "...".isPasswordValid(DEFAULT)
 * @receiver String
 * @param degree Int
 * @return Boolean
 */
fun String.isPasswordValid(degree: Int = UtilKVerifyUrl.PWD_DEFAULT): Boolean {
    return when (degree) {
        UtilKVerifyUrl.PWD_DEFAULT -> this.length > 5
        else -> false
    }
}

/**
 * IP是否合法
 * @receiver String
 * @return Boolean
 */
fun String.isIPValid(): Boolean = UtilKVerifyUrl.isIPValid(this)

/**
 * 域名是否合法
 * @receiver String
 * @return Boolean
 */
fun String.isDoMainValid(): Boolean = UtilKVerifyUrl.isDoMainValid(this)

/**
 * 端口是否合法
 * @receiver String
 * @return Boolean
 */
fun String.isPortValid(): Boolean = UtilKVerifyUrl.isPortValid(this)