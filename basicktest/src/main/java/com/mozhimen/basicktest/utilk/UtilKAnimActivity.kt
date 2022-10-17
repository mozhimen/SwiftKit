package com.mozhimen.basicktest.utilk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkAnimBinding

class UtilKAnimActivity : BaseKActivity<ActivityUtilkAnimBinding, BaseKViewModel>(R.layout.activity_utilk_anim) {
    override fun initData(savedInstanceState: Bundle?) {
        //UtilKAnim.bgBetweenColors(vb.utilkAnimView1, Color.WHITE, UtilKRes.getColor(R.color.blue_normal), duration = 3000)
        //UtilKAnim.bgAlphaFlash(vb.utilkAnimView2, 0, duration = 3000)
    }

    override fun onPause() {
        //vb.utilkAnimView1.stopAnim()
        //vb.utilkAnimView2.stopAnim()

        super.onPause()
    }
}