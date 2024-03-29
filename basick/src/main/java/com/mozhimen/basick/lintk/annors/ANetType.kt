package com.mozhimen.basick.lintk.annors

import androidx.annotation.StringDef
import com.mozhimen.basick.elemk.android.net.cons.CNetType

/**
 * @ClassName ANetType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 13:42
 * @Version 1.0
 */
@Target(AnnotationTarget.TYPE)
@StringDef(
    CNetType.MOBILE,
    CNetType.MOBILE_2G,
    CNetType.MOBILE_3G,
    CNetType.MOBILE_4G,
    CNetType.WIFI,
    CNetType.VPN,
    CNetType.UNKNOWN,
    CNetType.NONE
)
annotation class ANetType
