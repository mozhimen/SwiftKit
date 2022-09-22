package com.mozhimen.app.basick.utilk

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityUtilkAnimBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKAnim
import com.mozhimen.basick.utilk.UtilKRes

class UtilKAnimActivity : BaseKActivity<ActivityUtilkAnimBinding, BaseKViewModel>(R.layout.activity_utilk_anim) {
    override fun initData(savedInstanceState: Bundle?) {
        UtilKAnim.bgBetweenColors(vb.utilkAnimView1, Color.WHITE, UtilKRes.getColor(R.color.blue_normal), duration = 3000)
        UtilKAnim.bgAlphaFlash(vb.utilkAnimView2, 0f, duration = 3000)
    }
}