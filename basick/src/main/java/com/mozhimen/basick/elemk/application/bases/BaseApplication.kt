package com.mozhimen.basick.elemk.application.bases

import android.app.Application
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex
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
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        StackKCb.instance.init()
    }
}