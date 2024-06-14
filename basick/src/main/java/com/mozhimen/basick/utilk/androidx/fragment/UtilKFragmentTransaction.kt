package com.mozhimen.basick.utilk.androidx.fragment

import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
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
object UtilKFragmentTransaction {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragment)

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun setMaxLifecycle(fragmentTransaction: FragmentTransaction, fragment: Fragment, state: Lifecycle.State) {
        fragmentTransaction.setMaxLifecycle(fragment, state)
    }

    @JvmStatic
    fun commitAllowingStateLoss(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.commitAllowingStateLoss()
    }

    @JvmStatic
    fun commit(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.commit()
    }

    @JvmStatic
    fun add(fragmentTransaction: FragmentTransaction, @IdRes containerViewId: Int, fragment: Fragment, tag: String?) {
        fragmentTransaction.add(containerViewId, fragment, tag)
    }

    @JvmStatic
    fun show(fragmentTransaction: FragmentTransaction, fragment: Fragment) {
        fragmentTransaction.show(fragment)
    }

    @JvmStatic
    fun hide(fragmentTransaction: FragmentTransaction, fragment: Fragment) {
        fragmentTransaction.hide(fragment)
    }

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun replace_commit(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment): FragmentTransaction =
        get(fragmentActivity).apply {
            replace(containerViewId, fragment).commit()
        }

    @JvmStatic
    fun replace_commit(mainFragment: Fragment, @IdRes containerViewId: Int, fragment: Fragment): FragmentTransaction =
        get(mainFragment).apply {
            replace(containerViewId, fragment).commit()
        }

    @JvmStatic
    fun replace_commitAllowingStateLoss(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment): FragmentTransaction =
        get(fragmentActivity).apply {
            replace(containerViewId, fragment).commitAllowingStateLoss()
        }

    @JvmStatic
    fun replace_commitAllowingStateLoss(mainFragment: Fragment, @IdRes containerViewId: Int, fragment: Fragment): FragmentTransaction =
        get(mainFragment).apply {
            replace(containerViewId, fragment).commitAllowingStateLoss()
        }

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun remove_commit(fragmentActivity: FragmentActivity, fragment: Fragment): FragmentTransaction =
        get(fragmentActivity).apply {
            remove(fragment).commit()
        }

    @JvmStatic
    fun remove_commit(mainFragment: Fragment, fragment: Fragment) =
        get(mainFragment).apply {
            remove(fragment).commit()
        }

    @JvmStatic
    fun remove_commitAllowingStateLoss(fragmentActivity: FragmentActivity, fragment: Fragment): FragmentTransaction =
        get(fragmentActivity).apply {
            remove(fragment).commitAllowingStateLoss()
        }

    @JvmStatic
    fun remove_commitAllowingStateLoss(mainFragment: Fragment, fragment: Fragment) =
        get(mainFragment).apply {
            remove(fragment).commitAllowingStateLoss()
        }
}