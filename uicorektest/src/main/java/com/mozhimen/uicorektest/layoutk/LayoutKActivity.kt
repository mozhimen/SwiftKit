package com.mozhimen.uicorektest.layoutk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
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

    fun goLayoutKBlur(view: View) {

    }

    fun goLayoutKBtn(view: View) {
        startContext<LayoutKBtnActivity>()
    }

    fun goLayoutKEdit(view: View) {
        startContext<LayoutKEditActivity>()
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

    fun goLayoutKRoll(view: View) {
        startContext<LayoutKRollActivity>()
    }

    fun goLayoutKScrollable(view: View) {

    }

    fun goLayoutSearch(view: View) {

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

    fun goLayoutKTouch(view: View) {

    }

    fun goLayoutKUnTouch(view: View) {

    }

    fun goLayoutAmount(view: View) {

    }

    fun goLayoutKChipGroup(view: View) {
        startContext<LayoutKChipGroupActivity>()
    }

    fun goLayoutKEmpty(view: View) {
        startContext<LayoutKEmptyActivity>()
    }

    fun goLayoutKLabelGroup(view: View) {

    }

    fun goLayoutKLoading(view: View) {
        startContext<LayoutKLoadingActivity>()
    }

    fun goLayoutKSpinner(view: View) {
        startContext<LayoutKSpinnerActivity>()
    }

    fun goLayoutKSquare(view: View) {
        startContext<LayoutKSquareActivity>()
    }
}