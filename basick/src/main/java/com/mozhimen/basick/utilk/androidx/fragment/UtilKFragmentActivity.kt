package com.mozhimen.basick.utilk.androidx.fragment

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * @ClassName UtilKFragmentActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
fun FragmentActivity.commitReplaceFragment(@IdRes containerViewId: Int, fragment: Fragment) {
    UtilKFragmentActivity.commitReplaceFragment(this, containerViewId, fragment)
}

fun FragmentActivity.commitReplaceFragmentExSame(@IdRes containerViewId: Int, fragment: Fragment) {
    UtilKFragmentActivity.commitReplaceFragmentExSame(this, containerViewId, fragment)
}


object UtilKFragmentActivity {
    @JvmStatic
    fun getSupportFragmentManager(fragmentActivity: FragmentActivity): FragmentManager =
        fragmentActivity.supportFragmentManager

    @JvmStatic
    fun commitReplaceFragmentExSame(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment) {
        val lastFragment = getSupportFragmentManager(fragmentActivity).findFragmentById(containerViewId)
        if (lastFragment != null && fragment::class == lastFragment::class) return
        commitReplaceFragment(fragmentActivity, containerViewId, fragment)
    }

    @JvmStatic
    fun commitReplaceFragment(fragmentActivity: FragmentActivity, @IdRes containerViewId: Int, fragment: Fragment) {
        getSupportFragmentManager(fragmentActivity).beginTransaction().replace(containerViewId, fragment).commit()
    }
}