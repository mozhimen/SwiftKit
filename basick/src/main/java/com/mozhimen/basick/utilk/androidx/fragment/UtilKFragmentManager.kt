package com.mozhimen.basick.utilk.androidx.fragment

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
object UtilKFragmentManager {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentManager =
        UtilKFragmentActivity.getSupportFragmentManager(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentManager =
        UtilKFragment.getChildFragmentManager(fragment)

    /////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun beginTransaction(fragmentActivity: FragmentActivity) :FragmentTransaction =
        get(fragmentActivity).beginTransaction()

    @JvmStatic
    fun beginTransaction(fragment: Fragment) :FragmentTransaction =
        get(fragment).beginTransaction()
}