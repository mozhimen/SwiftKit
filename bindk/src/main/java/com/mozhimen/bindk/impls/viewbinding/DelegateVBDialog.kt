package com.mozhimen.bindk.impls.viewbinding

import android.app.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.bases.viewbinding.BaseDelegateVB

/**
 * @ClassName DialogViewBindingDelegate
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:38
 * @Version 1.0
 */
class DelegateVBDialog<D, VB : ViewBinding>(
    clazz: Class<VB>,
    dialog: D
) : BaseDelegateVB<D, VB>(clazz, dialog) where D : Dialog, D : LifecycleOwner