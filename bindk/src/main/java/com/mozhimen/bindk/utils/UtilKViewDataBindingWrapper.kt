package com.mozhimen.bindk.utils

import android.app.Dialog
import android.view.View
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.impls.viewbinding.DelegateVBActivity
import com.mozhimen.bindk.impls.viewbinding.DelegateVBAny
import com.mozhimen.bindk.impls.viewbinding.DelegateVBDialog
import com.mozhimen.bindk.impls.viewbinding.DelegateVBFragment
import com.mozhimen.bindk.impls.viewdatabinding.DelegateVDBView

/**
 * @ClassName UtilKViewDataBindingWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/28 23:18
 * @Version 1.0
 */
inline fun <reified VDB : ViewDataBinding> ComponentActivity.viewDataBinding(): DelegateVBActivity<ComponentActivity, VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding<VDB>(this)

inline fun <reified VDB : ViewDataBinding> Fragment.viewDataBinding(): DelegateVBFragment<VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding<VDB>(this)

inline fun <D, reified VDB : ViewDataBinding> D.viewDataBinding(): DelegateVBDialog<D, VDB> where  D : Dialog, D : LifecycleOwner =
    UtilKViewDataBindingWrapper.viewDataBinding<D, VDB>(this)

inline fun <reified VDB : ViewDataBinding> LifecycleOwner.viewDataBinding(view: View): DelegateVDBView<VDB> =
    UtilKViewDataBindingWrapper.viewDataBinding(view, this)

//////////////////////////////////////////////////////////////////

object UtilKViewDataBindingWrapper {
    inline fun <reified VDB : ViewDataBinding> viewDataBinding(componentActivity: ComponentActivity): DelegateVBActivity<ComponentActivity, VDB> =
        DelegateVBActivity(VDB::class.java, componentActivity)

    inline fun <reified VDB : ViewDataBinding> viewDataBinding(fragment: Fragment): DelegateVBFragment<VDB> =
        DelegateVBFragment(VDB::class.java, fragment)

    inline fun <D, reified VDB : ViewDataBinding> viewDataBinding(dialog: D): DelegateVBDialog<D, VDB> where D : Dialog, D : LifecycleOwner =
        DelegateVBDialog(VDB::class.java, dialog)

    inline fun <reified VDB : ViewDataBinding> viewDataBinding(view: View, obj: LifecycleOwner): DelegateVDBView<VDB> =
        DelegateVDBView(VDB::class.java, view, obj)
}