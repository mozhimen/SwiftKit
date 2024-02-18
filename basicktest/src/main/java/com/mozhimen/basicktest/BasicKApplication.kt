package com.mozhimen.basicktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.OApiMultiDex_InApplication
import com.mozhimen.basick.stackk.process.StackKProcess

@OptIn(OApiMultiDex_InApplication::class)
class BasicKApplication : BaseApplication() {
    @OptIn(OApiInit_InApplication::class)
    override fun onCreate() {
        super.onCreate()

        StackKProcess.instance.init()
    }
}