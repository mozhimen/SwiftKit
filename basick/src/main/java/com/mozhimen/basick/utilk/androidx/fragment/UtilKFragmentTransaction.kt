package com.mozhimen.basick.utilk.androidx.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle

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

fun FragmentTransaction.addHideFragments(@IdRes containerViewId: Int, currentFragment: Fragment, tag: String, lastFragment: Fragment?) {
    UtilKFragmentTransaction.addHideFragments(this, containerViewId, currentFragment, tag, lastFragment)
}

fun FragmentTransaction.showHideFragments(currentFragment: Fragment, lastFragment: Fragment?) {
    UtilKFragmentTransaction.showHideFragments(this, currentFragment, lastFragment)
}

object UtilKFragmentTransaction {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragment)

    ///////////////////////////////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun addHideFragments(fragmentTransaction: FragmentTransaction, @IdRes containerViewId: Int, currentFragment: Fragment, tag: String, lastFragment: Fragment?) {
        fragmentTransaction.add(containerViewId, currentFragment, tag)
        fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        lastFragment?.let {
            fragmentTransaction.hide(it)
            fragmentTransaction.setMaxLifecycle(it, Lifecycle.State.STARTED)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    @JvmStatic
    fun showHideFragments(fragmentTransaction: FragmentTransaction, currentFragment: Fragment, lastFragment: Fragment?) {
        fragmentTransaction.show(currentFragment)
        fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        lastFragment?.let {
            fragmentTransaction.hide(it)
            fragmentTransaction.setMaxLifecycle(it, Lifecycle.State.STARTED)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}