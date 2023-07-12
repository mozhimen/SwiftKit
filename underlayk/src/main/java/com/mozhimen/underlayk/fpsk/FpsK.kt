package com.mozhimen.underlayk.fpsk

import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.underlayk.fpsk.commons.IFpsK
import com.mozhimen.underlayk.fpsk.helpers.FpsKDelegate

/**
 * @ClassName FpsK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/3/31 17:12
 * @Version 1.0
 */
@ALintKOptIn_ApiInit_InApplication
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
class FpsK : IFpsK by FpsKDelegate() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ///////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = FpsK()
    }
}