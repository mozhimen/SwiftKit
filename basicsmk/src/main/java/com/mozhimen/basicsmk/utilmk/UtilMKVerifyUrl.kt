package com.mozhimen.basicsmk.utilmk

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
object UtilMKVerifyUrl {
    private const val DEFAULT = 0

    /**
     * IP 验证
     */
    const val REGEX_IP =
        "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)"

    /**
     * 域名验证
     */
    const val REGEX_DOMAIN =
        "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$"

    /**
     * 端口号验证
     */
    const val REGEX_PORT = "^[-\\+]?[\\d]{1,6}$"

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

    /**
     * 作用: 正则校验
     */
    fun String.RegexValid(degree: String): Boolean {
        return when (degree) {
            REGEX_IP -> isIPValid(this)
            REGEX_DOMAIN -> isDoMainValid(this)
            REGEX_PORT -> isPortValid(this)
            else -> false
        }
    }

    fun isIPValid(ip: String) = ip.matches(Regex(REGEX_IP))

    fun isDoMainValid(domain: String) = domain.matches(Regex(REGEX_DOMAIN))

    fun isPortValid(port: String) = port.matches(Regex(REGEX_PORT))
}
