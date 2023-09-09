package com.mozhimen.basick.utilk.androidx.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @ClassName UtilKFragment
 * @Description TODO
 * @Author Daisy
 * @Date 2023/9/3 20:46
 * @Version 1.0
 */
object UtilKFragment {
    @JvmStatic
    fun getChildFragmentManager(fragment: Fragment): FragmentManager =
        fragment.childFragmentManager

    @JvmStatic
    fun isAlive(fragment: Fragment): Boolean =
        !fragment.isRemoving && !fragment.isDetached && fragment.activity != null
}