package com.mozhimen.basicsk.utilk

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
    const val PWD_DEFAULT = 0

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
    fun isIPValid(ip: String) = ip.matches(Regex(REGEX_IP))

    /**
     * 域名是否合法
     * @param domain String
     * @return Boolean
     */
    fun isDoMainValid(domain: String) = domain.matches(Regex(REGEX_DOMAIN))

    /**
     * 端口是否合法
     * @param port String
     * @return Boolean
     */
    fun isPortValid(port: String) = port.matches(Regex(REGEX_PORT))
}
