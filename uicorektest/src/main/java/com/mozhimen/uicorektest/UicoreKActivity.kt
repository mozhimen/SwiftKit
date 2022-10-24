package com.mozhimen.uicorektest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorektest.bannerk.BannerKActivity
import com.mozhimen.uicorektest.btnk.BtnKActivity
import com.mozhimen.uicorektest.databinding.ActivityUicorekBinding
import com.mozhimen.uicorektest.datak.DataKActivity
import com.mozhimen.uicorektest.datak.DataKRecyclerActivity
import com.mozhimen.uicorektest.dialogk.DialogKActivity
import com.mozhimen.uicorektest.layoutk.LayoutKActivity
import com.mozhimen.uicorektest.loadk.LoadKActivity
import com.mozhimen.uicorektest.navk.NavKActivity
import com.mozhimen.uicorektest.refreshk.RefreshKLayoutActivity
import com.mozhimen.uicorektest.refreshk.RefreshKLayoutLottieActivity
import com.mozhimen.uicorektest.sidek.SideKActivity
import com.mozhimen.uicorektest.tabk.*
import com.mozhimen.uicorektest.textk.TextKActivity
import com.mozhimen.uicorektest.viewk.ViewKActivity

class UicoreKActivity : BaseKActivityVB<ActivityUicorekBinding>() {

    fun gotoTabKBottom(view: View) {
        start<TabKBottomActivity>()
    }

    fun gotoTabKBottomLayout(view: View) {
        start<TabKBottomLayoutActivity>()
    }

    fun gotoTabKBottomFragment(view: View) {
        start<TabKBottomFragmentActivity>()
    }

    fun gotoTabKTop(view: View) {
        start<TabKTopActivity>()
    }

    fun gotoTabKTopLayout(view: View) {
        start<TabKTopLayoutActivity>()
    }

    fun gotoRefreshKLayout(view: View) {
        start<RefreshKLayoutActivity>()
    }

    fun gotoRefreshKLayoutLottie(view: View) {
        start<RefreshKLayoutLottieActivity>()
    }

    fun gotoBannerK(view: View) {
        start<BannerKActivity>()
    }

    fun gotoDataK(view: View) {
        start<DataKActivity>()
    }

    fun gotoDataKRecycler(view: View) {
        start<DataKRecyclerActivity>()
    }

    fun gotoLoadK(view: View) {
        start<LoadKActivity>()
    }

    fun gotoSideK(view: View) {
        start<SideKActivity>()
    }

    fun gotoLayoutK(view: View) {
        start<LayoutKActivity>()
    }

    fun goViewK(view: View) {
        start<ViewKActivity>()
    }

    fun goBtnK(view: View) {
        start<BtnKActivity>()
    }

    fun goDialogK(view: View) {
        start<DialogKActivity>()
    }

    fun goNavK(view: View) {
        start<NavKActivity>()
    }

    fun goTextK(view: View) {
        start<TextKActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}