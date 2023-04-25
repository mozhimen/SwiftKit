package com.mozhimen.uicorektest.layoutk

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
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
        startContext<LayoutKBannerActivity>()
    }

    fun goLayoutKLoadRefresh(view: View) {
        startContext<LayoutKLoadRefreshActivity>()
    }

    fun goLayoutKNavBar(view: View) {
        startContext<LayoutKNavBarActivity>()
    }

    fun goLayoutKRefresh(view: View) {
        startContext<LayoutKRefreshActivity>()
    }

    fun goLayoutKSide(view: View) {
        startContext<LayoutKSideActivity>()
    }

    fun goLayoutKSlider(view: View) {
        startContext<LayoutKSliderActivity>()
    }

    fun goLayoutKTab(view: View) {
        startContext<LayoutKTabActivity>()
    }

    fun goLayoutKBtn(view: View) {
        startContext<LayoutKBtnActivity>()
    }

    fun goLayoutKChipGroup(view: View) {
        startContext<LayoutKChipGroupActivity>()
    }

    fun goLayoutKEditItem(view: View) {
        startContext<LayoutKEditItemActivity>()
    }

    fun goLayoutKEmpty(view: View) {
        startContext<LayoutKEmptyActivity>()
    }

    fun goLayoutKSpinner(view: View) {
        startContext<LayoutKSpinnerActivity>()
    }

    fun goLayoutKSquare(view: View) {
        startContext<LayoutKSquareActivity>()
    }
}