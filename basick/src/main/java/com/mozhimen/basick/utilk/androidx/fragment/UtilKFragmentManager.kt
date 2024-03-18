package com.mozhimen.basick.utilk.androidx.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName UtilKFragmentManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
fun FragmentActivity.findFragmentById(@IdRes intResId: Int): Fragment? =
    UtilKFragmentManager.findFragmentById(this, intResId)

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    UtilKFragmentManager.findFragmentByTag(this, tag)

fun FragmentActivity.beginTransaction(): FragmentTransaction =
    UtilKFragmentManager.beginTransaction(this)

fun FragmentActivity.getVisibleFragment(@IdRes intResId: Int): Fragment? =
    UtilKFragmentManager.getVisibleFragment(this, intResId)

fun Fragment.beginTransaction(): FragmentTransaction =
    UtilKFragmentManager.beginTransaction(this)

object UtilKFragmentManager : IUtilK {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentManager =
        UtilKFragmentActivity.getSupportFragmentManager(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentManager =
        UtilKFragment.getChildFragmentManager(fragment)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun findFragmentByTag(fragmentActivity: FragmentActivity, tag: String): Fragment? =
        get(fragmentActivity).findFragmentByTag(tag)

    @JvmStatic
    fun findFragmentById(fragmentActivity: FragmentActivity, @IdRes intResId: Int): Fragment? =
        get(fragmentActivity).findFragmentById(intResId)

    @JvmStatic
    fun beginTransaction(fragmentActivity: FragmentActivity): FragmentTransaction =
        get(fragmentActivity).beginTransaction()

    @JvmStatic
    fun beginTransaction(fragment: Fragment): FragmentTransaction =
        get(fragment).beginTransaction()

    /////////////////////////////////////////////////////////////////////

    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun getVisibleFragment(fragmentActivity: FragmentActivity,@IdRes intResId: Int): Fragment? =
        get(fragmentActivity).findFragmentById(intResId)

    /**
     * 可以通过在 AndroidManifest.xml 中配置 <activity> 的 android:screenOrientation 属性，将 Activity 的方向固定，可以避免因屏幕旋转导致的重建，同时也不会回调 onConfigurationChanged。但是该属性在 多窗口系统 下会失效。
     * 通过配置 android:configChanges 可以控制在哪些系统配置改变的情况下 Activity 不重建。最常用的包括 orientation，screenSize，keyboardHidden。不过通过该方法，Activity 虽然不再重建，但是系统会回调 onConfigurationChanged，需要开发者自己处理配置的变换。
     *
     * 链接：https://www.jianshu.com/p/60f2ed95b124
     */
    @JvmStatic
    fun onSaveInstanceState(fragmentActivity: FragmentActivity, outState: Bundle, map: Map<String, Fragment?>) {
        map.forEach {
            if (it.value != null) {
                get(fragmentActivity).putFragment(outState, it.key, it.value!!)
                UtilKLogWrapper.d(TAG, "onSaveInstanceState: putFragment ${it.key} ${it.value}")
            }
        }
    }

    @JvmStatic
    fun onRestoreInstanceState(fragmentActivity: FragmentActivity, outState: Bundle?, vararg fragmentName: String): HashMap<String, Fragment?> {
        val map: HashMap<String, Fragment?> = HashMap()
        if (outState == null) return map
        fragmentName.forEach {
            map[it] = get(fragmentActivity).getFragment(outState, it)
        }
        return map.also { UtilKLogWrapper.d(TAG, "onRestoreInstanceState: getFragment $it") }
    }
}