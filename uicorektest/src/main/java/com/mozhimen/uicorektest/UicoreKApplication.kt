package com.mozhimen.uicorektest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiDeclare_InManifest
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication


/**
 * @ClassName UicoreKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 15:11
 * @Version 1.0
 */
class UicoreKApplication : BaseApplication() {
    @OptIn(ALintKOptIn_ApiInit_InApplication::class, ALintKOptIn_ApiDeclare_InManifest::class)
    override fun onCreate() {
        super.onCreate()

        //AdaptKAutoSize.instance.init(640, 400)
    }
}