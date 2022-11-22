package com.mozhimen.uicorektest.layoutk.navbar

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basick.utilk.exts.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkNavbarBinding

class LayoutKNavBarActivity : BaseActivityVB<ActivityLayoutkNavbarBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkNavbar3.addLeftImage(UtilKRes.getDrawable(R.mipmap.layoutk_navbar_back)!!, 40f.dp2px(), 2f.dp2px()) {
            setOnClickListener {
                finish()
            }
        }
        vb.layoutkNavbar3.addLeftBtnKIconFont(
            UtilKRes.getString(R.string.icon_mine),
            40f.dp2px(),
            30f.sp2px(),
            Color.BLACK,
            2f.dp2px(),
            "fonts/iconfont.ttf"
        ) {
            setOnClickListener {
                "你点击了图标".showToast()
            }
        }
    }
}