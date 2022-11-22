package com.mozhimen.uicorektest.layoutk.tab

import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
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
        start<LayoutKTabBottomActivity>()
    }

    fun goLayoutKTabBottomFragment(view: View) {
        start<LayoutKTabBottomFragmentActivity>()
    }

    fun goLayoutKTabBottomLayout(view: View) {
        start<LayoutKTabBottomLayoutActivity>()
    }

    fun goLayoutKTabTop(view: View) {
        start<LayoutKTabTopActivity>()
    }

    fun goLayoutKTabTopLayout(view: View) {
        start<LayoutKTabTopLayoutActivity>()
    }
}