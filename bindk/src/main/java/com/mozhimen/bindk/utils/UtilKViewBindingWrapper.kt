package com.mozhimen.bindk.utils

import android.app.Dialog
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.impls.viewbinding.DelegateVBActivity
import com.mozhimen.bindk.impls.viewbinding.DelegateVBAny
import com.mozhimen.bindk.impls.viewbinding.DelegateVBDialog
import com.mozhimen.bindk.impls.viewbinding.DelegateVBFragment

/**
 * @ClassName UtilKViewDataBindingWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/28 23:18
 * @Version 1.0
 */
inline fun <reified VB : ViewBinding> ComponentActivity.viewBinding(): DelegateVBActivity<ComponentActivity, VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

inline fun <reified VB : ViewBinding> Fragment.viewBinding(): DelegateVBFragment<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

inline fun <D, reified VB : ViewBinding> D.viewBinding(): DelegateVBDialog<D, VB> where  D : Dialog, D : LifecycleOwner =
    UtilKViewBindingWrapper.viewBinding<D, VB>(this)

inline fun <reified VB : ViewBinding> LifecycleOwner.viewBinding(): DelegateVBAny<VB> =
    UtilKViewBindingWrapper.viewBinding(this)

//////////////////////////////////////////////////////////////////

object UtilKViewBindingWrapper {
    inline fun <reified VB : ViewBinding> viewBinding(componentActivity: ComponentActivity): DelegateVBActivity<ComponentActivity, VB> =
        DelegateVBActivity(VB::class.java, componentActivity)

    inline fun <reified VB : ViewBinding> viewBinding(fragment: Fragment): DelegateVBFragment<VB> =
        DelegateVBFragment(VB::class.java, fragment)

    inline fun <D, reified VB : ViewBinding> viewBinding(dialog: D): DelegateVBDialog<D, VB> where D : Dialog, D : LifecycleOwner =
        DelegateVBDialog(VB::class.java, dialog)

    inline fun <reified VB : ViewBinding> viewBinding(obj: LifecycleOwner): DelegateVBAny<VB> =
        DelegateVBAny(VB::class.java, obj)
}