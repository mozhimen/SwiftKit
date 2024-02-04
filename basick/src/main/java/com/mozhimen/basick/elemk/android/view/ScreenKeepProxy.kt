package com.mozhimen.basick.elemk.android.view

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread

/**
 * @ClassName ScreenKeepProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/11 17:43
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class ScreenKeepProxy<A>(private val _activity: A) : BaseWakeBefDestroyLifecycleObserver() where A : Activity, A : LifecycleOwner {

    init {
        _activity.runOnMainThread(::applyScreenOn)
    }

    ////////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        UtilKScreen.applyKeepScreen(_activity, false)
        super.onDestroy(owner)
    }

    ////////////////////////////////////////////////////////////////////////

    private fun applyScreenOn() {
        UtilKScreen.applyKeepScreen(_activity, true)
    }
}