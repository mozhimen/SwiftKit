package com.mozhimen.basick.elemk.android.app.bases

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiInit_InApplication
import com.mozhimen.basick.lintk.optin.annors.ALintKOptIn_ApiMultiDex_InApplication
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
@ALintKOptIn_ApiMultiDex_InApplication
open class BaseApplication : Application(), IUtilK {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    @OptIn(ALintKOptIn_ApiInit_InApplication::class)
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        StackKCb.instance.init()
    }
}