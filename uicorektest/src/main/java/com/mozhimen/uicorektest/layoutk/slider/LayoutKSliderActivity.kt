package com.mozhimen.uicorektest.layoutk.slider

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.popwink.PopwinKBubbleText
import com.mozhimen.uicorektest.databinding.ActivityLayoutkSliderBinding

/**
 * @ClassName LayoutKSliderActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 23:35
 * @Version 1.0
 */
class LayoutKSliderActivity : BaseActivityVB<ActivityLayoutkSliderBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkSliderTxt.text = getTxt(vb.layoutkSlider.rod.currentPercent, vb.layoutkSlider.rod.currentVal, vb.layoutkSlider.rod.currentX)
        vb.layoutkSlider.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentPercent: Float, currentValue: Float, rod: MRod) {
                vb.layoutkSliderTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }

            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                genPopwinKBubbleText(
                    vb.layoutkSlider,
                    currentValue.toInt().toString(),
                    xOffset = /*(currentX - rod.centerX).toInt()*/(rod.currentX - vb.layoutkSlider.width / 2f).toInt(),
                    yOffset = -(8f).dp2px()
                )
                vb.layoutkSliderTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }
        })
        vb.layoutkSliderInsideTxt.text = getTxt(vb.layoutkSlider.rod.currentPercent, vb.layoutkSlider.rod.currentVal, vb.layoutkSlider.rod.currentX)
        vb.layoutkSliderInside.setRodDefaultPercent(0.2f)
        vb.layoutkSliderInside.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentPercent: Float, currentValue: Float, rod: MRod) {
                vb.layoutkSliderInsideTxt.text = getTxt(vb.layoutkSliderInside.rod.currentPercent, vb.layoutkSliderInside.rod.currentVal, vb.layoutkSliderInside.rod.currentX)
            }

            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                genPopwinKBubbleText(
                    vb.layoutkSliderInside,
                    currentValue.toInt().toString(),
                    xOffset = (rod.currentX - vb.layoutkSlider.width / 2f).toInt(),
                    yOffset = -(8f).dp2px()
                )
                vb.layoutkSliderInsideTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }
        })
    }

    fun getTxt(currentPercent: Float, currentValue: Float, currentX: Float): String {
        return "currentPercent $currentPercent currentValue $currentValue currentX $currentX"
    }

    private val _popwinKBubbleText: PopwinKBubbleText? = null
    fun genPopwinKBubbleText(view: View, tip: String, xOffset: Int = 0, yOffset: Int = 0, delayMillis: Long = 4000) {
        _popwinKBubbleText?.dismiss()
        val builder = PopwinKBubbleText.Builder(this)
        builder.apply {
            setTip(tip)
            setXOffset(xOffset)
            setYOffset(yOffset)
            setDismissDelay(delayMillis)
            create(view)
        }
    }
}