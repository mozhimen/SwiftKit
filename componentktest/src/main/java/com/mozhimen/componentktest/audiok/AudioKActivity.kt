package com.mozhimen.componentktest.audiok

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.TranslationType
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.basick.utilk.UtilKNumber
import com.mozhimen.componentk.audiok.AudioK
import com.mozhimen.componentk.audiok.cons.CAudioKEvent
import com.mozhimen.componentk.audiok.mos.MAudioK
import com.mozhimen.componentk.audiok.mos.MAudioKProgress
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityAudiokBinding
import com.mozhimen.uicorek.layoutk.slider.LayoutKSlider
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.popwink.PopwinKLifecycle
import kotlin.math.roundToInt

class AudioKActivity : BaseActivityVB<ActivityAudiokBinding>() {
    private val _audioList = arrayListOf(
        MAudioK("9b94d721ed244fa892b15112bc11a3ce","http://192.168.2.6/construction-sites-images/voice/20221018//9b94d721ed244fa892b15112bc11a3ce.wav",0),
        MAudioK("1237378768e7q8e7r8qwesafdasdfasdfaxss111", "http://192.168.2.9/construction-sites-images/voice/20221024/1237378768e7q8e7r8qwesafdasdfasdfaxss111.speex",0),
        MAudioK("3777061809", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3",0),
    )
    private val _popwinAudio: PopwinAudio by lazy { PopwinAudio(this) }
    private var _currentVolume: Int = AudioK.instance.getVolume()
        get() = AudioK.instance.getVolume()
        set(value) {
            val volume = UtilKNumber.normalize(value, AudioK.instance.getVolumeMin()..AudioK.instance.getVolumeMax())
            AudioK.instance.setVolume(volume).also {
                vb.audiokSliderVolumeTxt.text = volume.toString()
                field = volume
            }
        }
    private val _intervalVolume: Float by lazy { AudioK.instance.getVolumeMax().toFloat() - AudioK.instance.getVolumeMin().toFloat() }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: volume $_currentVolume")
        Log.d(TAG, "initData: volume min ${AudioK.instance.getVolumeMin()}")
        Log.d(TAG, "initData: volume max ${AudioK.instance.getVolumeMax()}")
        vb.audiokSliderVolumeTxt.text = AudioK.instance.getVolume().toString()
        vb.audiokSliderVolume.setRodDefaultPercent(_currentVolume / _intervalVolume)
        vb.audiokSliderVolume.setSliderListener(object : ISliderScrollListener {
            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                _currentVolume = (currentPercent * _intervalVolume).roundToInt()
            }
        })

        AudioK.instance.addAudiosToPlayList(_audioList)
        UtilKDataBus.with<MAudioK?>(CAudioKEvent.audio_start).observe(this) {
            if (it != null) {
                Log.d(TAG, "initData: audio_start")
                _popwinAudio.setTitle(it.name)
                if (!_popwinAudio.isShowing) {
                    _popwinAudio.showPopupWindow()
                }
            }
        }
        UtilKDataBus.with<Pair<MAudioK, Boolean>?>(CAudioKEvent.audio_popup).observe(this) {
            if (it != null) {
                Log.d(TAG, "initData: audio_popup")
                if (!it.second) {
                    _popwinAudio.dismiss()
                }
            }
        }
    }

    class PopwinAudio(context: Context) : PopwinKLifecycle(context) {
        private val TAG = "PopwinAudio>>>>>"
        private lateinit var _txtName: TextView
        private lateinit var _slider: LayoutKSlider
        private lateinit var _btnClose: ImageView

        private var _title = ""

        fun setTitle(title: String) {
            _title = title
            if (this::_txtName.isInitialized) {
                _txtName.text = title
            }
        }

        init {
            setContentView(R.layout.layout_popwin_audio)
            setBackgroundColor(Color.TRANSPARENT)
            setOutSideDismiss(false)
            isOutSideTouchable = true
        }

        override fun onViewCreated(contentView: View) {
            super.onViewCreated(contentView)

            _txtName = findViewById(R.id.popwink_audio_txt_name)
            _txtName.text = _title
            _slider = findViewById(R.id.popwink_audio_slider)
            _btnClose = findViewById<ImageView?>(R.id.popwink_audio_btn_close).apply {
                setOnClickListener {
                    dismiss()
                }
            }
            UtilKDataBus.with<MAudioKProgress?>(CAudioKEvent.progress_update).observe(this) {
                if (it != null) {
                    Log.d(TAG, "initData: progress_update" + " progress status ${it.status} currentPos ${it.currentPos} duration ${it.duration} audioInfo ${it.audioInfo}")
                    _slider.updateRodPercent(it.currentPos.toFloat() / it.duration.toFloat())
                }
            }
        }

        override fun onCreateShowAnimation(): Animation {
            return AnimKBuilder.asAnimation().add(TranslationType.FROM_TOP_SHOW).build()
        }

        override fun onCreateDismissAnimation(): Animation {
            return AnimKBuilder.asAnimation().add(TranslationType.TO_TOP_HIDE).build()
        }
    }
}