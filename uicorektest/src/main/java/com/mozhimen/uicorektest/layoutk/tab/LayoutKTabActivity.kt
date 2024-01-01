package com.mozhimen.uicorektest.layoutk.tab

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.databinding.ActivityLayoutkTabBinding

/**
 * @ClassName LayoutKTabActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/7 1:29
 * @Version 1.0
 */
class LayoutKTabActivity : BaseActivityVB<ActivityLayoutkTabBinding>() {

    fun goLayoutKTabBottom(view: View) {
        startContext<LayoutKTabBottomActivity>()
    }

    fun goLayoutKTabBottomFragment(view: View) {
        startContext<LayoutKTabBottomFragmentActivity>()
    }

    fun goLayoutKTabBottomLayout(view: View) {
        startContext<LayoutKTabBottomLayoutActivity>()
    }

    fun goLayoutKTabTop(view: View) {
        startContext<LayoutKTabTopActivity>()
    }

    fun goLayoutKTabTopLayout(view: View) {
        startContext<LayoutKTabTopLayoutActivity>()
    }
}