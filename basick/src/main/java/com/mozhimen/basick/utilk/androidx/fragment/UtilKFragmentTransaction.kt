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
fun FragmentActivity.getFragmentTransaction(): FragmentTransaction =
    UtilKFragmentTransaction.get(this)

fun Fragment.getFragmentTransaction(): FragmentTransaction =
    UtilKFragmentTransaction.get(this)

///////////////////////////////////////////////////////////////////////////////

object UtilKFragmentTransaction {
    @JvmStatic
    fun get(fragmentActivity: FragmentActivity): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragmentActivity)

    @JvmStatic
    fun get(fragment: Fragment): FragmentTransaction =
        UtilKFragmentManager.beginTransaction(fragment)
}