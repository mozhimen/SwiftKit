package com.mozhimen.basick.utilk.androidx.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * @ClassName UtilKFragmentManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
fun FragmentActivity.findFragmentById(@IdRes id: Int): Fragment? =
    UtilKFragmentManager.findFragmentById(this, id)

fun FragmentActivity.findFragmentByTag(tag: String): Fragment? =
    UtilKFragmentManager.findFragmentByTag(this, tag)

fun FragmentActivity.beginTransaction(): FragmentTransaction =
    UtilKFragmentManager.beginTransaction(this)

fun Fragment.beginTransaction(): FragmentTransaction =
    UtilKFragmentManager.beginTransaction(this)

object UtilKFragmentManager {
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
    fun findFragmentById(fragmentActivity: FragmentActivity, @IdRes id: Int): Fragment? =
        get(fragmentActivity).findFragmentById(id)

    @JvmStatic
    fun beginTransaction(fragmentActivity: FragmentActivity): FragmentTransaction =
        get(fragmentActivity).beginTransaction()

    @JvmStatic
    fun beginTransaction(fragment: Fragment): FragmentTransaction =
        get(fragment).beginTransaction()
}