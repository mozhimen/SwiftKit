package com.mozhimen.componentktest.audiok

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.componentk.audiok.AudioK
import com.mozhimen.componentk.audiok.cons.CAudioKEvent
import com.mozhimen.componentk.audiok.mos.MAudioK
import com.mozhimen.componentk.audiok.mos.MAudioKProgress
import com.mozhimen.componentktest.databinding.ActivityAudiokBinding
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.popwink.PopwinKBubbleText

class AudioKActivity : BaseKActivityVB<ActivityAudiokBinding>() {
    private val _audioList = arrayListOf(
        MAudioK("01", "http://192.168.2.6/construction-sites-images/voice/20221018//9b94d721ed244fa892b15112bc11a3ce.wav"),
        MAudioK("02", "http://192.168.2.9/construction-sites-images/voice/20221024/1237378768e7q8e7r8qwesafdasdfasdfaxss111.speex"),
        MAudioK("03", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3"),
    )

    override fun initData(savedInstanceState: Bundle?) {
        /*vb.audiokSliderVolumeTxt.text = vb.audiokSlider.rod.currentVal.toString()
        vb.audiokSlider.setSliderListener(object : ISliderScrollListener {
            override fun onScrollStart() {

            }

            override fun onScrolling(currentValue: Float) {
                vb.audiokSliderVolumeTxt.text = currentValue.toString()
            }

            override fun onScrollEnd(currentValue: Float) {

            }

        })*/
        AudioK.instance.addAudiosToPlayList(_audioList)
        UtilKDataBus.with<MAudioK?>(CAudioKEvent.audio_start).observe(this) {
            if (it != null) {
                "id: ${it.id}".showToast()
            }
        }
        UtilKDataBus.with<MAudioKProgress?>(CAudioKEvent.progress_update).observe(this) {
            if (it != null) {
                Log.d(
                    TAG, "initData: " + "progress status ${it.status} currentPos ${it.currentPos} duration ${it.duration} audioInfo ${it.audioInfo}"
                )
            }
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
}