package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.verify.UtilKVerifyUrl

/**
 * @ClassName ExtsKVerifyUrl
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:29
 * @Version 1.0
 */
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