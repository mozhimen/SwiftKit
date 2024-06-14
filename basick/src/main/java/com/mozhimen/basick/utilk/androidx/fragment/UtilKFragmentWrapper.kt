package com.mozhimen.basick.utilk.androidx.fragment

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.commons.ISuspendExt_Listener
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKLifecycleOwner
import com.mozhimen.basick.utilk.commons.IUtilK
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @ClassName UtilKFragmentWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
fun Fragment.runOnViewLifecycleState(state: Lifecycle.State, coroutineContext: CoroutineContext = EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
    UtilKFragmentWrapper.runOnViewLifecycleState(this, state, coroutineContext, block)
}

fun FragmentActivity.addHideFragments(currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
    UtilKFragmentWrapper.addHideFragments(this, currentFragment, currentTag, lastFragment, lastTag, containerViewId)
}

fun FragmentActivity.showHideFragments(currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
    UtilKFragmentWrapper.showHideFragments(this, currentFragment, currentTag, lastFragment, lastTag, containerViewId)
}

fun FragmentActivity.replaceFragment_commit(@IdRes containerViewId: Int, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
    UtilKFragmentWrapper.replaceFragment_commit(this, containerViewId, fragment)

fun Fragment.replaceFragment_commit(@IdRes containerViewId: Int, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
    UtilKFragmentWrapper.replaceFragment_commit(this, containerViewId, fragment)

fun FragmentActivity.removeFragment_commit(fragment: Fragment): Pair<FragmentTransaction, Fragment> =
    UtilKFragmentWrapper.removeFragment_commit(this, fragment)

fun Fragment.removeFragment_commit(fragment: Fragment): Pair<FragmentTransaction, Fragment> =
    UtilKFragmentWrapper.removeFragment_commit(this, fragment)

////////////////////////////////////////////////////////////////////

object UtilKFragmentWrapper : IUtilK {
    @JvmStatic
    fun runOnViewLifecycleState(fragment: Fragment, state: Lifecycle.State, coroutineContext: CoroutineContext = EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
        UtilKLifecycleOwner.runOnLifecycleState(fragment.viewLifecycleOwner, fragment.lifecycleScope, state, coroutineContext, block)
    }

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun replaceFragment_commit(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
        UtilKFragmentTransaction.replace_commit(fragmentActivity, containerViewId, fragment) to fragment

    @JvmStatic
    fun replaceFragment_commit(mainFragment: Fragment, @IdRes containerViewId: Int, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
        UtilKFragmentTransaction.replace_commit(mainFragment, containerViewId, fragment) to fragment

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun removeFragment_commit(fragmentActivity: FragmentActivity, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
        UtilKFragmentTransaction.remove_commit(fragmentActivity, fragment) to fragment

    @JvmStatic
    fun removeFragment_commit(mainFragment: Fragment, fragment: Fragment): Pair<FragmentTransaction, Fragment> =
        UtilKFragmentTransaction.remove_commit(mainFragment, fragment) to fragment

    /////////////////////////////////////////////////////////////////////

    /**
     * 可以通过在 AndroidManifest.xml 中配置 <activity> 的 android:screenOrientation 属性，将 Activity 的方向固定，可以避免因屏幕旋转导致的重建，同时也不会回调 onConfigurationChanged。但是该属性在 多窗口系统 下会失效。
     * 通过配置 android:configChanges 可以控制在哪些系统配置改变的情况下 Activity 不重建。最常用的包括 orientation，screenSize，keyboardHidden。不过通过该方法，Activity 虽然不再重建，但是系统会回调 onConfigurationChanged，需要开发者自己处理配置的变换。
     *
     * 链接：https://www.jianshu.com/p/60f2ed95b124
     *
     *
     */
    @JvmStatic
    fun onFragmentSaveInstanceState(fragmentActivity: FragmentActivity, outState: Bundle, map: Map<String, Fragment?>) {
        map.forEach {
            if (it.value != null) {
                UtilKFragmentManager.putFragment(fragmentActivity, outState, it.key, it.value!!)
                UtilKLogWrapper.d(TAG, "onSaveInstanceState: putFragment ${it.key} ${it.value}")
            }
        }
    }

    @JvmStatic
    fun onFragmentRestoreInstanceState(fragmentActivity: FragmentActivity, outState: Bundle?, vararg fragmentName: String): HashMap<String, Fragment?> {
        val map: HashMap<String, Fragment?> = HashMap()
        if (outState == null) return map
        fragmentName.forEach {
            map[it] = UtilKFragmentManager.getFragment(fragmentActivity, outState, it)
        }
        return map.also { UtilKLogWrapper.d(TAG, "onRestoreInstanceState: getFragment $it") }
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
        val fragmentTransaction = UtilKFragmentTransaction.get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) == null && !currentFragment.isAdded) {
            UtilKFragmentTransaction.add(fragmentTransaction, containerViewId, currentFragment, currentTag)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        } else {
            UtilKFragmentTransaction.show(fragmentTransaction, currentFragment)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            UtilKFragmentTransaction.hide(fragmentTransaction, lastFragment)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, lastFragment, Lifecycle.State.STARTED)
        }
        UtilKFragmentTransaction.commitAllowingStateLoss(fragmentTransaction)
    }

    @JvmStatic
    fun showHideFragments(fragmentActivity: FragmentActivity, currentFragment: Fragment, currentTag: String, lastFragment: Fragment?, lastTag: String?, @IdRes containerViewId: Int) {
        val fragmentTransaction = UtilKFragmentTransaction.get(fragmentActivity)
        if (fragmentActivity.findFragmentByTag(currentTag) != null && currentFragment.isAdded) {
            UtilKFragmentTransaction.show(fragmentTransaction, currentFragment)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        } else {
            UtilKFragmentTransaction.add(fragmentTransaction, containerViewId, currentFragment, currentTag)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, currentFragment, Lifecycle.State.RESUMED)
        }
        if (lastFragment != null && lastTag != null && fragmentActivity.findFragmentByTag(lastTag) != null && lastFragment.isAdded) {
            UtilKFragmentTransaction.hide(fragmentTransaction, lastFragment)
            UtilKFragmentTransaction.setMaxLifecycle(fragmentTransaction, lastFragment, Lifecycle.State.STARTED)
        }
        UtilKFragmentTransaction.commitAllowingStateLoss(fragmentTransaction)
    }
}