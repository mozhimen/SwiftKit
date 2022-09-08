package com.mozhimen.app.uicorek.layoutk

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityLayoutkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorek.layoutk.slider.Slidr

class LayoutKActivity : BaseKActivity<ActivityLayoutkBinding, BaseKViewModel>(R.layout.activity_layoutk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val layoutKSlider1 = vb.layoutkSlider1
        layoutKSlider1.max = 500f
        layoutKSlider1.addStep(Slidr.Step("test", 250f, Color.parseColor("#007E90"), Color.RED))
        layoutKSlider1.setTextMax("max\nvalue")
        layoutKSlider1.setTextMin("min\nvalue")
        layoutKSlider1.currentValue = 300f
        layoutKSlider1.setListener { _, _ -> }

        val layoutKSlider2 = vb.layoutkSlider2
        layoutKSlider2.max = 5000f
        layoutKSlider2.currentValue = 5000f
        layoutKSlider2.addStep(Slidr.Step("test", 1500f, Color.parseColor("#007E90"), Color.parseColor("#111111")))

        val layoutKSliderRegions = vb.layoutkSliderRegions
        layoutKSliderRegions.max = 3000f
        layoutKSliderRegions.setRegionTextFormatter { region, value -> "region $region : $value" }
        layoutKSliderRegions.addStep(Slidr.Step("test", 1500f, Color.parseColor("#007E90"), Color.parseColor("#111111")))
    }

    fun goLayoutKEmpty(view: View) {
        start<LayoutKEmptyActivity>()
    }

    fun goLayoutKVideo(view: View) {
        start<LayoutKVideoActivity>()
    }
}