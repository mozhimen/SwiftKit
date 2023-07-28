package com.mozhimen.basick.elemk.android.os

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.android.os.bases.BaseWeakRefMainHandler
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.os.removeAllCbsAndMsgs
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread

/**
 * @ClassName EventKHandler
 * @Description 在onPause之前都醒(保持运行)的Handler, 一般用于View
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 11:34
 * @Version 1.0
 */
@OptIn(OptInApiInit_ByLazy::class, OptInApiCall_BindLifecycle::class)
class WakeBefPauseLifecycleHandler<O : LifecycleOwner>(private val _lifecycleOwner: O) : BaseWeakRefMainHandler<O>(_lifecycleOwner), IDefaultLifecycleObserver {

    init {
        bindLifecycle(_lifecycleOwner)
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.runOnMainThread {
            _lifecycleOwner.lifecycle.removeObserver(this@WakeBefPauseLifecycleHandler)
            _lifecycleOwner.lifecycle.addObserver(this@WakeBefPauseLifecycleHandler)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        this.removeAllCbsAndMsgs()
        this.clearRef()
        owner.lifecycle.removeObserver(this@WakeBefPauseLifecycleHandler)
    }
}