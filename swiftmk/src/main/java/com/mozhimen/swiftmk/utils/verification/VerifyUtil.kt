package com.mozhimen.swiftmk.utils.verification

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
object VerifyUtil {
    private const val DEFAULT = 0

    /**
     * 作用: 校验密码
     * 用法1: "...".isPasswordValid()
     *       "...".isPasswordValid(DEFAULT)
     */
    fun String.isPasswordValid(degree: Int = DEFAULT): Boolean {
        return when (degree) {
            DEFAULT -> this.length > 5
            else -> false
        }
    }
}
