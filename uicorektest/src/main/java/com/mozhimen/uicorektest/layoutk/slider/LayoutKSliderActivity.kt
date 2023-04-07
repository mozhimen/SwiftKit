package com.mozhimen.uicorektest.layoutk.slider

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.dp2px
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.popwink.PopwinKTextKBubbleBuilder
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
        VB.layoutkSliderTxt.text = getTxt(VB.layoutkSlider.rod.currentPercent, VB.layoutkSlider.rod.currentVal, VB.layoutkSlider.rod.currentX)
        VB.layoutkSlider.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentPercent: Float, currentValue: Float, rod: MRod) {
                VB.layoutkSliderTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }

            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                genPopwinKBubbleText(
                    VB.layoutkSlider,
                    currentValue.toInt().toString(),
                    xOffset = /*(currentX - rod.centerX).toInt()*/(rod.currentX - VB.layoutkSlider.width / 2f).toInt(),
                    yOffset = -(8f).dp2px().toInt()
                )
                VB.layoutkSliderTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }
        })
        VB.layoutkSliderInsideTxt.text = getTxt(VB.layoutkSlider.rod.currentPercent, VB.layoutkSlider.rod.currentVal, VB.layoutkSlider.rod.currentX)
        VB.layoutkSliderInside.setRodDefaultPercent(0.2f)
        VB.layoutkSliderInside.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentPercent: Float, currentValue: Float, rod: MRod) {
                VB.layoutkSliderInsideTxt.text = getTxt(VB.layoutkSliderInside.rod.currentPercent, VB.layoutkSliderInside.rod.currentVal, VB.layoutkSliderInside.rod.currentX)
            }

            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                genPopwinKBubbleText(
                    VB.layoutkSliderInside,
                    currentValue.toInt().toString(),
                    xOffset = (rod.currentX - VB.layoutkSlider.width / 2f).toInt(),
                    yOffset = -(8f).dp2px().toInt()
                )
                VB.layoutkSliderInsideTxt.text = getTxt(currentPercent, currentValue, rod.currentX)
            }
        })
    }

    fun getTxt(currentPercent: Float, currentValue: Float, currentX: Float): String {
        return "currentPercent $currentPercent currentValue $currentValue currentX $currentX"
    }

    private val _popwinKTextKBubbleBuilder: PopwinKTextKBubbleBuilder? = null
    fun genPopwinKBubbleText(view: View, tip: String, xOffset: Int = 0, yOffset: Int = 0, delayMillis: Long = 4000) {
        _popwinKTextKBubbleBuilder?.dismiss()
        val builder = PopwinKTextKBubbleBuilder.Builder(this)
        builder.apply {
            setTip(tip)
            setXOffset(xOffset)
            setYOffset(yOffset)
            setDismissDelay(delayMillis)
            create(view)
        }
    }
}