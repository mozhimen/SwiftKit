package com.mozhimen.bindk.impls.viewbinding

import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.bases.viewbinding.BaseDelegateVB

/**
 * @ClassName FragmentViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:43
 * @Version 1.0
 */
class DelegateVBAny<VB : ViewBinding>(
    clazz: Class<VB>,
    obj: LifecycleOwner
) : BaseDelegateVB<LifecycleOwner, VB>(clazz, obj)