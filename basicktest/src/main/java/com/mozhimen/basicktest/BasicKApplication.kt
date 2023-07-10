package com.mozhimen.basicktest

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.stackk.process.StackKProcess

class BasicKApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        StackKProcess.instance.init()
    }
}