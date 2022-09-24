package com.mozhimen.componentktest.guidek.fragments

import android.util.Log
import com.mozhimen.basick.basek.BaseKFragment
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.FragmentGuidekNotificationsBinding
import com.mozhimen.guidek.annor.GuideKDestination

/**
 * @ClassName HomeFragment
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/9/3 13:15
 * @Version 1.0
 */
@GuideKDestination(pageUrl = "main/guidek/notifications", pageIndex = 1)
class NotificationsFragment :
    BaseKFragment<FragmentGuidekNotificationsBinding, BaseKViewModel>(R.layout.fragment_guidek_notifications) {

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }
}