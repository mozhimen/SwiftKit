package com.mozhimen.basick.utilk.androidx.fragment

import android.os.Bundle
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

/////////////////////////////////////////////////////////////////////

object UtilKFragmentManager : IUtilK {
    @JvmStatic
    fun get_ofSupport(fragmentActivity: FragmentActivity): FragmentManager =
        UtilKFragmentActivity.getSupportFragmentManager(fragmentActivity)

    @JvmStatic
    fun get_ofChild(fragment: Fragment): FragmentManager =
        UtilKFragment.getChildFragmentManager(fragment)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun findFragmentByTag(fragmentActivity: FragmentActivity, tag: String): Fragment? =
        get_ofSupport(fragmentActivity).findFragmentByTag(tag)

    @JvmStatic
    fun findFragmentById(fragmentActivity: FragmentActivity, @IdRes intResId: Int): Fragment? =
        get_ofSupport(fragmentActivity).findFragmentById(intResId)

    @JvmStatic
    fun beginTransaction(fragmentActivity: FragmentActivity): FragmentTransaction =
        get_ofSupport(fragmentActivity).beginTransaction()

    @JvmStatic
    fun beginTransaction(fragment: Fragment): FragmentTransaction =
        get_ofChild(fragment).beginTransaction()

    @JvmStatic
    fun putFragment(fragmentActivity: FragmentActivity, bundle: Bundle, key: String, fragment: Fragment) {
        get_ofSupport(fragmentActivity).putFragment(bundle, key, fragment)
    }

    @JvmStatic
    fun getFragment(fragmentActivity: FragmentActivity, bundle: Bundle, key: String): Fragment? =
        get_ofSupport(fragmentActivity).getFragment(bundle, key)
}