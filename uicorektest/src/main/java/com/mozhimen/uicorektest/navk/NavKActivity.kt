package com.mozhimen.uicorektest.navk

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityNavkBinding

class NavKActivity : BaseKActivity<ActivityNavkBinding, BaseKViewModel>(R.layout.activity_navk) {

    override fun initData(savedInstanceState: Bundle?) {
        vb.navkNavbar3.addLeftImage(UtilKRes.getDrawable(R.mipmap.navk_back)!!, 40f.dp2px(), 2f.dp2px()) {
            setOnClickListener {
                finish()
            }
        }
        vb.navkNavbar3.addLeftBtnKIconFont(
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