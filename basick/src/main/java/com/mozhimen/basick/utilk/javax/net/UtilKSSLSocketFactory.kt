package com.mozhimen.basick.utilk.javax.net

import javax.net.ssl.SSLSocketFactory

/**
 * @ClassName UtilKSSLFactory
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/9 16:40
 * @Version 1.0
 */
object UtilKSSLSocketFactory {
    @JvmStatic
    fun get_ofTLS(): SSLSocketFactory =
        UtilKSSLContext.getSocketFactory_ofTLS()

    @JvmStatic
    fun get_ofSSL(): SSLSocketFactory =
        UtilKSSLContext.getSocketFactory_ofSSL()
}