package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.datatype.regular.UtilKVerifyUrl

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
fun String.checkIP(): Boolean = UtilKVerifyUrl.checkIP(this)

/**
 * 域名是否合法
 * @receiver String
 * @return Boolean
 */
fun String.checkDoMain(): Boolean = UtilKVerifyUrl.checkDoMain(this)

/**
 * 端口是否合法
 * @receiver String
 * @return Boolean
 */
fun String.checkPort(): Boolean = UtilKVerifyUrl.checkPort(this)

/**
 * url是都合法
 * @receiver String
 * @return Boolean
 */
fun String.checkUrl(): Boolean = UtilKVerifyUrl.checkUrl(this)