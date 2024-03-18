package com.mozhimen.basick.elemk.javax.net.bases

import android.annotation.SuppressLint
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * @ClassName BaseHostnameVerifier
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/28 16:34
 * @Version 1.0
 */
class BaseHostnameVerifier: HostnameVerifier , IUtilK {
    @SuppressLint("BadHostnameVerifier")
    override fun verify(hostname: String, session: SSLSession): Boolean {
        UtilKLogWrapper.d(TAG, "verify: WARNING: Hostname is not matched for cert.")
        return true
    }
}