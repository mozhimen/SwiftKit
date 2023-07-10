package com.mozhimen.basick.elemk.androidx.lifecycle.bases

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName BaseLifecycleObserverForView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/24 17:47
 * @Version 1.0
 */
@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
open class BaseWakeBefPauseLifecycleObserver : IDefaultLifecycleObserver {
    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@BaseWakeBefPauseLifecycleObserver)
            owner.lifecycle.addObserver(this@BaseWakeBefPauseLifecycleObserver)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this@BaseWakeBefPauseLifecycleObserver)
    }
}