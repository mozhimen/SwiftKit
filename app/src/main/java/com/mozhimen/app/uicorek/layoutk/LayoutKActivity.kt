package com.mozhimen.app.uicorek.layoutk

import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityLayoutkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderListener

class LayoutKActivity : BaseKActivity<ActivityLayoutkBinding, BaseKViewModel>(R.layout.activity_layoutk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        //vb.layoutkSliderTxt.text = vb.layoutkSlider.getCurrentVal().toString()
        vb.layoutkSlider.setSliderListener(object : ISliderListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentValue: Float) {
                vb.layoutkSliderTxt.text = currentValue.toString()
            }

            override fun onScrollEnd(currentValue: Float) {
            }
        })
        /*val layoutKSlider1 = vb.layoutkSlider1
        layoutKSlider1.setRodMaxVal(500f)
        layoutKSlider1.addSection(Section("test", 250f, Color.parseColor("#007E90"), Color.RED))
        layoutKSlider1.setLabelTextMax("max\nvalue")
        layoutKSlider1.setLabelTextMin("min\nvalue")
        layoutKSlider1.setRodCurrentVal(300f)
        layoutKSlider1.setSliderListener(object : ISliderListener {
            override fun onScroll(layoutKSlider: LayoutKSlider, currentValue: Float) {

            }
        })

        val layoutKSlider2 = vb.layoutkSlider2
        layoutKSlider2.setRodMaxVal(5000f)
        layoutKSlider2.setRodCurrentVal(5000f)
        layoutKSlider2.addSection(Section("test", 1500f, Color.parseColor("#007E90"), Color.parseColor("#111111")))

        val layoutKSliderRegions = vb.layoutkSliderRegions
        layoutKSliderRegions.setRodMaxVal(3000f)
        layoutKSliderRegions.setTextRodFormatter(object : ITextRodFormatter {
            override fun format(region: Int, value: Float): String {
                return "region $region : $value"
            }
        })
        layoutKSliderRegions.addSection(Section("test", 1500f, Color.parseColor("#007E90"), Color.parseColor("#111111")))*/
    }

    fun goLayoutKEmpty(view: View) {
        start<LayoutKEmptyActivity>()
    }

    fun goLayoutKVideo(view: View) {
        start<LayoutKVideoActivity>()
    }
}