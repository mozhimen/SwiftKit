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

//////////////////////////////////////////////////////////////////////////

fun FragmentActivity.removeFragment(fragment: Fragment) {
    UtilKFragmentTransaction.removeFragment(this, fragment)
}

//////////////////////////////////////////////////////////////////////////

fun FragmentActivity.addHideFragments(currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
    UtilKFragmentTransaction.addHideFragments(this, currentFragment, currentTag, lastFragment, lastTag, containerViewId)
}

fun FragmentActivity.showHideFragments(currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
    UtilKFragmentTransaction.showHideFragments(this, currentFragment, currentTag, lastFragment, lastTag, containerViewId)
}

//////////////////////////////////////////////////////////////////////////

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
    fun addHideFragments(fragmentActivity: FragmentActivity, currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
        val fragmentTransaction = get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) == null && !currentFragment.isAdded) {
            fragmentTransaction.add(containerViewId, currentFragment, currentTag)
            fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        } else {
            fragmentTransaction.show(currentFragment)
            fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.setMaxLifecycle(lastFragment, Lifecycle.State.STARTED)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    @JvmStatic
    fun showHideFragments(fragmentActivity: FragmentActivity, currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
        val fragmentTransaction = get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) != null && currentFragment.isAdded) {
            fragmentTransaction.show(currentFragment)
            fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        } else {
            fragmentTransaction.add(containerViewId, currentFragment, currentTag)
            fragmentTransaction.setMaxLifecycle(currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            fragmentTransaction.hide(lastFragment)
            fragmentTransaction.setMaxLifecycle(lastFragment, Lifecycle.State.STARTED)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}