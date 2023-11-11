package com.mozhimen.basick.utilk.javax.net

import javax.net.SocketFactory
import javax.net.ssl.SSLSocketFactory

/**
 * @ClassName UtilKSSLFactory
 * @Description TODO
 * @Author Mozhimen
 * @Date 2023/11/9 16:40
 * @Version 1.0
 */
object UtilKSSLSocketFactory {
    fun getTLS(): SSLSocketFactory =
        UtilKSSLContext.getTLSSocketFactory()
}