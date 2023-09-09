package com.mozhimen.basick.utilk.androidx.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

/**
 * @ClassName UtilKFragmentTransaction
 * @Description TODO
 * @Author Daisy
 * @Date 2023/9/3 20:39
 * @Version 1.0
 */
fun FragmentActivity.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
    UtilKFragmentTransaction.replaceFragment(this, containerViewId, fragment)
}

fun Fragment.replaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
    UtilKFragmentTransaction.replaceFragment(this, containerViewId, fragment)
}

fun FragmentActivity.removeFragment(fragment: Fragment) {
    UtilKFragmentTransaction.removeFragment(this, fragment)
}

object UtilKFragmentTransaction {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragment)

    @JvmStatic
    fun replaceFragment(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment) {
        get(fragmentActivity).replace(containerViewId, fragment).commit()
    }

    @JvmStatic
    fun replaceFragment(mainFragment: Fragment, @IdRes containerViewId: Int, fragment: Fragment) {
        get(mainFragment).replace(containerViewId, fragment).commit()
    }

    @JvmStatic
    fun removeFragment(fragmentActivity: FragmentActivity, fragment: Fragment) {
        get(fragmentActivity).remove(fragment).commit()
    }
}