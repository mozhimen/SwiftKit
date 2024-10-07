package com.mozhimen.bindk.impls.viewdatabinding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.bindk.bases.viewdatabinding.BaseDelegateVDB

/**
 * @ClassName DialogViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:38
 * @Version 1.0
 */
class DelegateVDBView<VDB : ViewDataBinding>(
    clazz: Class<VDB>,
    private val _view: View,
    lifecycleOwner: LifecycleOwner
) : BaseDelegateVDB<LifecycleOwner, VDB>(clazz, lifecycleOwner) {
    override fun getViewBinding(thisRef: LifecycleOwner): VDB {
        return DataBindingUtil.bind(_view)!!//super.getViewBinding(thisRef)
    }
}