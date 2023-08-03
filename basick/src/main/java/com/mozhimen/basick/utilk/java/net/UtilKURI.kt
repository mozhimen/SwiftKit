package com.mozhimen.basick.utilk.java.net

import com.mozhimen.basick.elemk.java.net.cons.CURI
import com.mozhimen.basick.utilk.bases.IUtilK
import java.net.URI

/**
 * @ClassName UtilKURI
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 18:17
 * @Version 1.0
 */
object UtilKURI : IUtilK {

    @JvmStatic
    fun isSchemeValid(uRI: URI): Boolean =
            uRI.scheme.equals(CURI.Scheme.http) || uRI.scheme.equals(CURI.Scheme.https) || uRI.scheme.equals(CURI.Scheme.tcp) || uRI.scheme.equals(CURI.Scheme.udp)
}