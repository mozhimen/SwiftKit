package com.mozhimen.uicorektest.layoutk.slider

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.percent
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.popwink.PopwinKBubbleText
import com.mozhimen.uicorektest.databinding.ActivityLayoutkSliderBinding

/**
 * @ClassName LayoutKSliderActivity
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 23:35
 * @Version 1.0
 */
class LayoutKSliderActivity : BaseKActivityVB<ActivityLayoutkSliderBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkSliderTxt.text = vb.layoutkSlider.rod.currentVal.toString()
        vb.layoutkSliderInside.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentValue: Float) {
                vb.layoutkSliderTxt.text = currentValue.toString()
            }

            override fun onScrollEnd(currentValue: Float) {
                val sliderInside = vb.layoutkSliderInside
                val centerX = sliderInside.left + (sliderInside.width) / 2f - sliderInside.slider.leftX
                val currentX = sliderInside.left +
                        currentValue.percent(sliderInside.rod.minVal to sliderInside.rod.maxVal) * (sliderInside.width - 2 * sliderInside.slider.leftX)
                genPopwinKBubbleText(
                    sliderInside,
                    currentValue.toInt().toString(),
                    xOffset = (currentX - centerX).toInt(),
                    yOffset = -(8f).dp2px()
                )
            }
        })
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