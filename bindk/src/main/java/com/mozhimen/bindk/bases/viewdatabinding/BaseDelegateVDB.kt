package com.mozhimen.bindk.bases.viewdatabinding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.bindk.bases.viewbinding.BaseDelegateVB
import com.mozhimen.bindk.utils.UtilKViewDataBinding
import com.mozhimen.kotlin.utilk.commons.IUtilK

/**
 * @ClassName BaseViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/6 20:16
 * @Version 1.0
 */
abstract class BaseDelegateVDB<T : LifecycleOwner, VDB : ViewDataBinding>(
    clazz: Class<VDB>,
    obj: T?
) : BaseDelegateVB<T, VDB>(clazz, obj), IUtilK {

    open fun getViewDataBindingLifecycleOwner(): LifecycleOwner? =
        _obj

    //////////////////////////////////////////////////////////////////////////////

    override fun getViewBinding(thisRef: T): VDB {
        return UtilKViewDataBinding.get<VDB>(_clazz, getLayoutInflater(thisRef)).apply {
            lifecycleOwner = getViewDataBindingLifecycleOwner()
        }
    }

    override fun recycle() {
        _viewBinding?.unbind()
        super.recycle()
    }
}