package com.mozhimen.basicktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.OptInApiInit_InApplication
import com.mozhimen.basick.lintk.optin.OptInApiMultiDex_InApplication
import com.mozhimen.basick.stackk.process.StackKProcess

@OptIn(OptInApiMultiDex_InApplication::class)
class BasicKApplication : BaseApplication() {
    @OptIn(OptInApiInit_InApplication::class)
    override fun onCreate() {
        super.onCreate()

        StackKProcess.instance.init()
    }
}