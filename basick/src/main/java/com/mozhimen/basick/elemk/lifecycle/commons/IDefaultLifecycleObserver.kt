package com.mozhimen.basick.elemk.lifecycle.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.crashlytics.buildtools.reloc.com.google.errorprone.annotations.concurrent.LazyInit
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IDefaultLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/19 12:08
 * @Version 1.0
 */
@AOptLazyInit
interface IDefaultLifecycleObserver : DefaultLifecycleObserver, IUtilK {
    fun bindLifecycle(owner: LifecycleOwner)
}