package com.mozhimen.basicktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiMultiDex_InApplication
import com.mozhimen.basick.stackk.process.StackKProcess

@OptIn(ALintKOptIn_ApiMultiDex_InApplication::class)
class BasicKApplication : BaseApplication(false) {
    @OptIn(ALintKOptIn_ApiInit_InApplication::class)
    override fun onCreate() {
        super.onCreate()

        StackKProcess.instance.init()
    }
}