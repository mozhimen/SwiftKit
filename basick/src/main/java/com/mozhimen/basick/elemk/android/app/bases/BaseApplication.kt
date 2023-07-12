package com.mozhimen.basick.elemk.android.app.bases

import android.app.Application
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
open class BaseApplication : Application(), IUtilK {
    @OptIn(ALintKOptIn_ApiInit_InApplication::class)
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        StackKCb.instance.init()
    }
}