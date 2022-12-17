package com.mozhimen.uicorektest.layoutk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.uicorektest.databinding.ActivityLayoutkBinding
import com.mozhimen.uicorektest.layoutk.banner.LayoutKBannerActivity
import com.mozhimen.uicorektest.layoutk.loadrefresh.LayoutKLoadRefreshActivity
import com.mozhimen.uicorektest.layoutk.navbar.LayoutKNavBarActivity
import com.mozhimen.uicorektest.layoutk.refresh.LayoutKRefreshActivity
import com.mozhimen.uicorektest.layoutk.side.LayoutKSideActivity
import com.mozhimen.uicorektest.layoutk.slider.LayoutKSliderActivity
import com.mozhimen.uicorektest.layoutk.tab.LayoutKTabActivity

class LayoutKActivity : BaseActivityVB<ActivityLayoutkBinding>() {
    fun goLayoutKBanner(view: View) {
        start<LayoutKBannerActivity>()
    }

    fun goLayoutKLoadRefresh(view: View) {
        start<LayoutKLoadRefreshActivity>()
    }

    fun goLayoutKNavBar(view: View) {
        start<LayoutKNavBarActivity>()
    }

    fun goLayoutKRefresh(view: View) {
        start<LayoutKRefreshActivity>()
    }

    fun goLayoutKSide(view: View) {
        start<LayoutKSideActivity>()
    }

    fun goLayoutKSlider(view: View) {
        start<LayoutKSliderActivity>()
    }

    fun goLayoutKTab(view: View) {
        start<LayoutKTabActivity>()
    }

    fun goLayoutKBtn(view: View) {
        start<LayoutKBtnActivity>()
    }

    fun goLayoutKChipGroup(view: View) {
        start<LayoutKChipGroupActivity>()
    }

    fun goLayoutKEmpty(view: View) {
        start<LayoutKEmptyActivity>()
    }

    fun goLayoutKInputItem(view: View) {
        start<LayoutKInputItemActivity>()
    }

    fun goLayoutKSquare(view: View) {
        start<LayoutKSquareActivity>()
    }

    fun goLayoutKTimeLine(view: View) {
        start<LayoutKTimeLineActivity>()
    }

    fun goLayoutKVideo(view: View) {
        start<LayoutKVideoActivity>()
    }
}