package com.mozhimen.app.uicoremk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mozhimen.app.uicoremk.bannermk.BannerMKActivity
import com.mozhimen.app.uicoremk.btnmk.BtnMKActivity
import com.mozhimen.app.databinding.ActivityMainBinding
import com.mozhimen.app.uicoremk.datamk.DataMKActivity
import com.mozhimen.app.uicoremk.refreshmk.RefreshMKLayoutActivity
import com.mozhimen.app.tabmk.TabMKBottomActivity
import com.mozhimen.app.tabmk.TabMKBottomFragmentActivity
import com.mozhimen.app.tabmk.TabMKBottomLayoutActivity
import com.mozhimen.app.tabmk.TabMKTopLayoutActivity
import com.mozhimen.app.viewmk.ViewMKActivity

class UICoreMKActivity : Activity() {

    private val vb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

    fun gotoTabMKBottom(view: View) {
        startActivity(Intent(this, TabMKBottomActivity::class.java))
    }

    fun gotoTabMKBottomLayout(view: View) {
        startActivity(Intent(this, TabMKBottomLayoutActivity::class.java))
    }

    fun gotoTabMKBottomFragment(view: View) {
        startActivity(Intent(this, TabMKBottomFragmentActivity::class.java))
    }

    fun gotoTabMKTopLayout(view: View) {
        startActivity(Intent(this, TabMKTopLayoutActivity::class.java))
    }

    fun gotoRefreshMKLayout(view: View) {
        startActivity(Intent(this, RefreshMKLayoutActivity::class.java))
    }

    fun gotoBannerMK(view: View) {
        startActivity(Intent(this, BannerMKActivity::class.java))
    }

    fun gotoDataMK(view: View) {
        startActivity(Intent(this, DataMKActivity::class.java))
    }

    fun goViewMK(view: View) {
        startActivity( Intent(this, ViewMKActivity::class.java))
    }

    fun goBtnMK(view: View) {
        startActivity(Intent(this, BtnMKActivity::class.java))
    }
}