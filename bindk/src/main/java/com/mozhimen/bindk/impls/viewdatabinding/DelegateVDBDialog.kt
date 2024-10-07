package com.mozhimen.bindk.impls.viewdatabinding

import android.app.Dialog
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
class DelegateVDBDialog<D, VDB : ViewDataBinding>(
    clazz: Class<VDB>,
    dialog: D
) : BaseDelegateVDB<D, VDB>(clazz, dialog) where D : Dialog, D : LifecycleOwner