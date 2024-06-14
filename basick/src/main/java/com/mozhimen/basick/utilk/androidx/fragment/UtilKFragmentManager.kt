package com.mozhimen.basick.utilk.androidx.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import androidx.annotation.IdRes
import androidx.annotation.NonNull
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

    @JvmStatic
    fun putFragment(fragmentActivity: FragmentActivity, bundle: Bundle, key: String, fragment: Fragment) {
        get(fragmentActivity).putFragment(bundle, key, fragment)
    }

    @JvmStatic
    fun getFragment(fragmentActivity: FragmentActivity, bundle: Bundle, key: String): Fragment? =
        get(fragmentActivity).getFragment(bundle, key)
}