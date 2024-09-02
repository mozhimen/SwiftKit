package com.mozhimen.basick.helpers

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.utils.runOnMainThread
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.wrapper.UtilKScreen

/**
 * @ClassName ScreenKeepProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class ScreenKeepProxy<A>(private val _activity: A) : BaseWakeBefDestroyLifecycleObserver() where A : Activity, A : LifecycleOwner {

    init {
        _activity.runOnMainThread{applyScreenOn()}
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