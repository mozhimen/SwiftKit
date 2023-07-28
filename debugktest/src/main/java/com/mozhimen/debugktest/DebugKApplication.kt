package com.mozhimen.debugktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.OptInApiMultiDex_InApplication

/**
 * @ClassName DebugKApplication
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/16 14:51
 * @Version 1.0
 */
@OptIn(OptInApiMultiDex_InApplication::class)
class DebugKApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}