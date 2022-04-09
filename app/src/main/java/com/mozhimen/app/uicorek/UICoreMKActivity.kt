package com.mozhimen.app.uicorek

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mozhimen.app.databinding.ActivityUicorekBinding
import com.mozhimen.app.uicorek.bannerk.BannerKActivity
import com.mozhimen.app.uicorek.btnk.BtnKActivity
import com.mozhimen.app.uicorek.datak.DataKActivity
import com.mozhimen.app.uicorek.refreshk.RefreshKLayoutActivity
import com.mozhimen.app.uicorek.tabk.TabKBottomActivity
import com.mozhimen.app.uicorek.tabk.TabKBottomFragmentActivity
import com.mozhimen.app.uicorek.tabk.TabKBottomLayoutActivity
import com.mozhimen.app.uicorek.tabk.TabKTopLayoutActivity
import com.mozhimen.app.uicorek.viewk.ViewKActivity

class UICoreKActivity : Activity() {

    private val vb: ActivityUicorekBinding by lazy { ActivityUicorekBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun gotoTabKBottom(view: View) {
        startActivity(Intent(this, TabKBottomActivity::class.java))
    }

    fun gotoTabKBottomLayout(view: View) {
        startActivity(Intent(this, TabKBottomLayoutActivity::class.java))
    }

    fun gotoTabKBottomFragment(view: View) {
        startActivity(Intent(this, TabKBottomFragmentActivity::class.java))
    }

    fun gotoTabKTopLayout(view: View) {
        startActivity(Intent(this, TabKTopLayoutActivity::class.java))
    }

    fun gotoRefreshKLayout(view: View) {
        startActivity(Intent(this, RefreshKLayoutActivity::class.java))
    }

    fun gotoBannerK(view: View) {
        startActivity(Intent(this, BannerKActivity::class.java))
    }

    fun gotoDataK(view: View) {
        startActivity(Intent(this, DataKActivity::class.java))
    }

    fun goViewK(view: View) {
        startActivity( Intent(this, ViewKActivity::class.java))
    }

    fun goBtnK(view: View) {
        startActivity(Intent(this, BtnKActivity::class.java))
    }
}