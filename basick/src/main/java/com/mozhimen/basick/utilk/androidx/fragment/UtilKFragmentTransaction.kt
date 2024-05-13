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
    fun replace_commit(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment) {
        get(fragmentActivity).replace(containerViewId, fragment).commit()
    }

    @JvmStatic
    fun replace_commit(mainFragment: Fragment, @IdRes containerViewId: Int, fragment: Fragment) {
        get(mainFragment).replace(containerViewId, fragment).commit()
    }

    @JvmStatic
    fun remove_commit(fragmentActivity: FragmentActivity, fragment: Fragment) {
        get(fragmentActivity).remove(fragment).commit()
    }

    @JvmStatic
    fun setMaxLifecycle(fragmentTransaction: FragmentTransaction, fragment: Fragment, state: Lifecycle.State) {
        fragmentTransaction.setMaxLifecycle(fragment, state)
    }

    @JvmStatic
    fun commitAllowingStateLoss(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.commitAllowingStateLoss()
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

    /**
     *         //设置容器
     *         var nextFragment: Fragment? = findFragmentByTag(navigateKDes.id)
     *         if (null == nextFragment) {
     *             nextFragment = _fragmentMap[navigateKDes.id] ?: run {
     *                 navigateKDes.onInvokeFragment.invoke().also { _fragmentMap[navigateKDes.id] = it }
     *             }
     *             addHideFragments(nextFragment, navigateKDes.id, lastFragment, _navigateKDes?.id, R.id.main_fragment_container)
     *         } else {
     *             showHideFragments(nextFragment, navigateKDes.id, lastFragment, _navigateKDes?.id, R.id.main_fragment_container)
     *         }
     *         _navigateKDes = navigateKDes
     */
    @JvmStatic
    fun addHideFragments(fragmentActivity: FragmentActivity, currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
        val fragmentTransaction = get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) == null && !currentFragment.isAdded) {
            add(fragmentTransaction, containerViewId, currentFragment, currentTag)
            setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        } else {
            show(fragmentTransaction, currentFragment)
            setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            hide(fragmentTransaction, lastFragment)
            setMaxLifecycle(fragmentTransaction, lastFragment, Lifecycle.State.STARTED)
        }
        commitAllowingStateLoss(fragmentTransaction)
    }

    @JvmStatic
    fun showHideFragments(fragmentActivity: FragmentActivity, currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
        val fragmentTransaction = get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) != null && currentFragment.isAdded) {
            show(fragmentTransaction, currentFragment)
            setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        } else {
            add(fragmentTransaction, containerViewId, currentFragment, currentTag)
            setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            hide(fragmentTransaction, lastFragment)
            setMaxLifecycle(fragmentTransaction, lastFragment, Lifecycle.State.STARTED)
        }
        commitAllowingStateLoss(fragmentTransaction)
    }
}