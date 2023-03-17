package com.mozhimen.uicorektest.layoutk.navbar

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basick.utilk.exts.sp2px
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkNavbarBinding

class LayoutKNavBarActivity : BaseActivityVB<ActivityLayoutkNavbarBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkNavbar3.addLeftImage(UtilKRes.getDrawable(R.mipmap.layoutk_navbar_back)!!, 40f.dp2px().toInt(), 2f.dp2px().toInt()) {
            setOnClickListener {
                finish()
            }
        }
        vb.layoutkNavbar3.addLeftBtnKIconFont(
            UtilKRes.getString(R.string.icon_mine),
            40f.dp2px().toInt(),
            30f.sp2px().toInt(),
            Color.BLACK,
            2f.dp2px().toInt(),
            "fonts/iconfont.ttf"
        ) {
            setOnClickListener {
                "你点击了图标".showToast()
            }
        }
    }
}