package com.mozhimen.app.uicorek

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.databinding.ActivityUicorekBinding
import com.mozhimen.app.uicorek.bannerk.BannerKActivity
import com.mozhimen.app.uicorek.btnk.BtnKActivity
import com.mozhimen.app.uicorek.datak.DataKActivity
import com.mozhimen.app.uicorek.datak.DataKRecyclerActivity
import com.mozhimen.app.uicorek.dialogk.DialogKActivity
import com.mozhimen.app.uicorek.layoutk.LayoutKActivity
import com.mozhimen.app.uicorek.loadk.LoadKActivity
import com.mozhimen.app.uicorek.navk.NavKActivity
import com.mozhimen.app.uicorek.refreshk.RefreshKLayoutActivity
import com.mozhimen.app.uicorek.refreshk.RefreshKLayoutLottieActivity
import com.mozhimen.app.uicorek.tabk.*
import com.mozhimen.app.uicorek.viewk.ViewKActivity
import com.mozhimen.basick.extsk.start

class UICoreKActivity : Activity() {

    private val vb: ActivityUicorekBinding by lazy { ActivityUicorekBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

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
}