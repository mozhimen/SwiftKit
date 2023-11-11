package com.mozhimen.basick.utilk.javax.net

import com.mozhimen.basick.elemk.javax.net.bases.BaseX509TrustManager
import com.mozhimen.basick.utilk.bases.IUtilK
import java.security.SecureRandom
import javax.net.SocketFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager

/**
 * @ClassName UtilKSSLContext
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 16:19
 * @Version 1.0
 */
object UtilKSSLContext : IUtilK {
    @JvmStatic
    fun getTLS(): SSLContext =
        SSLContext.getInstance("TLS")

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun generateTLS(): SSLContext {
        val sslContext = getTLS()
        sslContext.init(null, arrayOf<TrustManager>(BaseX509TrustManager()), SecureRandom())
        return sslContext
    }

    @JvmStatic
    fun getTLSSocketFactory(): SSLSocketFactory =
        generateTLS().socketFactory
}