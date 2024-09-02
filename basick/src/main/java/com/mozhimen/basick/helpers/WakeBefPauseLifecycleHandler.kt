package com.mozhimen.basick.helpers

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utils.runOnMainThread
import com.mozhimen.kotlin.elemk.android.os.bases.BaseWeakRefMainHandler
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.utilk.android.os.removeCallbacksAndMessages_ofNull

/**
 * @ClassName EventKHandler
 * @Description 在onPause之前都醒(保持运行)的Handler, 一般用于View
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
class WakeBefPauseLifecycleHandler<O : LifecycleOwner>(private val _lifecycleOwner: O) : BaseWeakRefMainHandler<O>(_lifecycleOwner), IDefaultLifecycleObserver {

    init {
        bindLifecycle(_lifecycleOwner)
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.runOnMainThread {
            owner.lifecycle.removeObserver(this@WakeBefPauseLifecycleHandler)
            owner.lifecycle.addObserver(this@WakeBefPauseLifecycleHandler)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        this.removeCallbacksAndMessages_ofNull()
        this.clearRef()
        owner.lifecycle.removeObserver(this@WakeBefPauseLifecycleHandler)
    }
}