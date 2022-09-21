package com.mozhimen.app.uicorek.layoutk

import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityLayoutkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.datak.DataKKey
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.percent
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.extsk.start
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderListener
import com.mozhimen.uicorek.popwink.PopwinKBubbleText

class LayoutKActivity : BaseKActivity<ActivityLayoutkBinding, BaseKViewModel>(R.layout.activity_layoutk) {
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkSliderTxt.text = vb.layoutkSlider.rod.currentVal.toString()
        vb.layoutkSliderInside.setSliderListener(object : ISliderListener {
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
        vb.layoutkChips.bindKeys(
            arrayListOf(
                DataKKey("0", "赛博朋克2077"),
                DataKKey("1", "老头环"),
                DataKKey("2", "塞尔达"),
                DataKKey("3", "使命召唤19"),
                DataKKey("4", "全战三国"),
                DataKKey("5", "荒野大镖客"),
                DataKKey("6", "GTA6"),
                DataKKey("7", "文明6")
            )
        )
        vb.layoutkChips.setOnCheckedListener { _, i, dataKKey ->
            "index: $i dataKey: ${dataKKey.id} ${dataKKey.key}".showToast()
        }
        vb.layoutkChipsAdd.setOnClickListener {
            vb.layoutkChips.addKey(DataKKey("ss", "原神"))
        }
        vb.layoutkChipsRemove.setOnClickListener {
            vb.layoutkChips.removeKey(DataKKey("ss", "原神"))
        }
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

    fun goLayoutKEmpty(view: View) {
        start<LayoutKEmptyActivity>()
    }

    fun goLayoutKVideo(view: View) {
        start<LayoutKVideoActivity>()
    }
}