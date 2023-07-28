package com.mozhimen.basick.elemk.androidx.activity

import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle

/**
 * @ClassName ResultLauncherProxy
 * @Description TODO
 * @Author 83524
 * @Date 2023/4/16 22:40
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class ActivityResultLauncherProxy<T>() : BaseWakeBefDestroyLifecycleObserver() {
    private var _activityResultLauncher: ActivityResultLauncher<T>? = null

    fun getResultLauncher(): ActivityResultLauncher<T>? =
        _activityResultLauncher

    override fun onDestroy(owner: LifecycleOwner) {
        _activityResultLauncher?.unregister()
        super.onDestroy(owner)
    }
}