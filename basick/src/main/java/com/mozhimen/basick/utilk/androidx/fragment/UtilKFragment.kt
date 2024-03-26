package com.mozhimen.basick.utilk.androidx.fragment

import android.app.Activity
import android.os.Bundle
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
    fun getActivity(fragment: Fragment): Activity? =
        fragment.activity

    @JvmStatic
    fun getChildFragmentManager(fragment: Fragment): FragmentManager =
        fragment.childFragmentManager

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAlive(fragment: Fragment): Boolean =
        !isRemoving(fragment) && !isDetached(fragment) && getActivity(fragment) != null

    @JvmStatic
    fun isAdded(fragment: Fragment): Boolean =
        fragment.isAdded

    @JvmStatic
    fun isRemoving(fragment: Fragment): Boolean =
        fragment.isRemoving

    @JvmStatic
    fun isDetached(fragment: Fragment): Boolean =
        fragment.isDetached
}