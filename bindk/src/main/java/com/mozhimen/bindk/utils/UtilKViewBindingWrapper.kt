package com.mozhimen.bindk.utils

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.mozhimen.bindk.helpers.ActivityViewBindingDelegate
import com.mozhimen.bindk.helpers.DialogViewBindingDelegate
import com.mozhimen.bindk.helpers.FragmentViewBindingDelegate

/**
 * @ClassName UtilKViewDataBindingWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/28 23:18
 * @Version 1.0
 */
inline fun <reified VB : ViewBinding> AppCompatActivity.viewBinding(): ActivityViewBindingDelegate<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

inline fun <reified VB : ViewBinding> FragmentActivity.viewBinding(): ActivityViewBindingDelegate<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

inline fun <reified VB : ViewBinding> Activity.viewBinding(): ActivityViewBindingDelegate<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

inline fun <reified VB : ViewBinding> Dialog.viewBinding(): DialogViewBindingDelegate<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>()

inline fun <reified VB : ViewBinding> Fragment.viewBinding(): FragmentViewBindingDelegate<VB> =
    UtilKViewBindingWrapper.viewBinding<VB>(this)

//////////////////////////////////////////////////////////////////

object UtilKViewBindingWrapper {
    inline fun <reified VB : ViewBinding> viewBinding(appCompatActivity: AppCompatActivity): ActivityViewBindingDelegate<VB> =
        ActivityViewBindingDelegate(VB::class.java, appCompatActivity)

    inline fun <reified VB : ViewBinding> viewBinding(fragmentActivity: FragmentActivity): ActivityViewBindingDelegate<VB> =
        ActivityViewBindingDelegate(VB::class.java, fragmentActivity)

    inline fun <reified VB : ViewBinding> viewBinding(activity: Activity): ActivityViewBindingDelegate<VB> =
        ActivityViewBindingDelegate(VB::class.java, activity)

    inline fun <reified VB : ViewBinding> viewBinding(fragment: Fragment): FragmentViewBindingDelegate<VB> =
        FragmentViewBindingDelegate(VB::class.java, fragment)

    inline fun <reified VB : ViewBinding> viewBinding(): DialogViewBindingDelegate<VB> =
        DialogViewBindingDelegate(VB::class.java)
}