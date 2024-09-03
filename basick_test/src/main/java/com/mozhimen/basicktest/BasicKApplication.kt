package com.mozhimen.basicktest

import com.mozhimen.kotlin.elemk.android.app.bases.BaseApplication
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.OApiMultiDex_InApplication
import com.mozhimen.stackk.process.StackKProcess

@OptIn(OApiMultiDex_InApplication::class)
class BasicKApplication : BaseApplication() {
    @OptIn(OApiInit_InApplication::class)
    override fun onCreate() {
        super.onCreate()

        com.mozhimen.stackk.process.StackKProcess.instance.init()
    }
}