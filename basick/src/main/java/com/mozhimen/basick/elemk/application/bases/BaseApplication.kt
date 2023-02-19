package com.mozhimen.basick.elemk.application.bases

import android.app.Application
import androidx.annotation.CallSuper
import androidx.multidex.MultiDex
import com.mozhimen.basick.stackk.StackKMgr

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 13:00
 * @Version 1.0
 */
open class BaseApplication : Application() {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        StackKMgr.instance
    }
}