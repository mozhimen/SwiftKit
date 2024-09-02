package com.mozhimen.stackk.bases

import androidx.annotation.CallSuper
import androidx.multidex.MultiDexApplication
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.OApiMultiDex_InApplication
import com.mozhimen.stackk.cb.StackKCb
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK

/**
 * @ClassName BaseApplication
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OApiMultiDex_InApplication
open class BaseApplication : MultiDexApplication(), IUtilK {

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }

    @OptIn(OApiInit_InApplication::class)
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        val time = System.currentTimeMillis()
        StackKCb.instance.init()
        UtilKLogWrapper.d(TAG, "onCreate: StackKCb.instance.init() time ${System.currentTimeMillis() - time}")
    }
}