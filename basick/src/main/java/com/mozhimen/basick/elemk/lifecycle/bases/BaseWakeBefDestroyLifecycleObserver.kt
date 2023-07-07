package com.mozhimen.basick.elemk.lifecycle.bases

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.lintk.optin.annors.ADescription
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.utilk.bases.BaseUtilK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName BaseDelegateLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/21 21:22
 * @Version 1.0
 */
@AOptLazyInit
open class BaseWakeBefDestroyLifecycleObserver : IDefaultLifecycleObserver, BaseUtilK() {

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@BaseWakeBefDestroyLifecycleObserver)
            owner.lifecycle.addObserver(this@BaseWakeBefDestroyLifecycleObserver)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this@BaseWakeBefDestroyLifecycleObserver)
    }
}