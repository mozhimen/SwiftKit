package com.mozhimen.basick.utilk.androidx.fragment

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * @ClassName UtilKFragmentActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/22 22:19
 * @Version 1.0
 */
object UtilKFragmentActivity {
    @JvmStatic
    fun getSupportFragmentManager(fragmentActivity: FragmentActivity): FragmentManager =
        fragmentActivity.supportFragmentManager
}