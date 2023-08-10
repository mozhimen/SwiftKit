package com.mozhimen.uicorektest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.OptInApiDeclare_InManifest
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.lintk.optin.OptInApiMultiDex_InApplication


/**
 * @ClassName UicoreKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 15:11
 * @Version 1.0
 */
@OptIn(OptInApiMultiDex_InApplication::class)
class UicoreKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        //AdaptKAutoSize.instance.init(640, 400)
    }
}