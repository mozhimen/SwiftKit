package com.mozhimen.componentktest.mediak

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import com.mozhimen.basick.animk.builder.AnimKBuilder
import com.mozhimen.basick.animk.builder.temps.AnimKTranslationType
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.normalize
import com.mozhimen.componentk.mediak.audio.MediaKAudio
import com.mozhimen.componentk.mediak.audio.cons.CMediaKAudioCons
import com.mozhimen.componentk.mediak.audio.mos.MAudioKInfo
import com.mozhimen.componentk.mediak.audio.mos.MAudioKProgress
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityMediakAudioBinding
import com.mozhimen.uicorek.layoutk.slider.LayoutKSlider
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.popwink.bases.BasePopwinKLifecycle
import kotlin.math.roundToInt

@AManifestKRequire(CPermission.WAKE_LOCK)
@APermissionCheck(CPermission.WAKE_LOCK)
class MediaKAudioActivity : BaseActivityVB<ActivityMediakAudioBinding>() {
    private val _audioList = arrayListOf(
        MAudioKInfo("9b94d721ed244fa892b15112bc11a3ce", "http://192.168.2.6/construction-sites-images/voice/20221018//9b94d721ed244fa892b15112bc11a3ce.wav", 0),
        MAudioKInfo("1237378768e7q8e7r8qwesafdasdfasdfaxss111", "http://192.168.2.9/construction-sites-images/voice/20221024/1237378768e7q8e7r8qwesafdasdfasdfaxss111.speex", 0),
        MAudioKInfo("3777061809", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3", 0),
    )
    private val _popwinAudio: PopwinKAudio by lazy { PopwinKAudio(this) }
    private var _currentVolume: Int = MediaKAudio.instance.getVolumeCurrent()
        get() = MediaKAudio.instance.getVolumeCurrent()
        set(value) {
            val volume = value.normalize(MediaKAudio.instance.getVolumeMin()..MediaKAudio.instance.getVolumeMax())
            if (UtilKBuildVersion.isAfterV_28_9_P()) {
                MediaKAudio.instance.setVolume(volume).also {
                    vb.audiokSliderVolumeTxt.text = volume.toString()
                    field = volume
                }
            }
        }
    private val _intervalVolume: Float by lazy { MediaKAudio.instance.getVolumeMax().toFloat() - MediaKAudio.instance.getVolumeMin().toFloat() }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData: volume $_currentVolume")
        Log.d(TAG, "initData: volume min ${MediaKAudio.instance.getVolumeMin()}")
        Log.d(TAG, "initData: volume max ${MediaKAudio.instance.getVolumeMax()}")
        vb.audiokSliderVolumeTxt.text = MediaKAudio.instance.getVolumeCurrent().toString()
        vb.audiokSliderVolume.setRodDefaultPercent(_currentVolume / _intervalVolume)
        vb.audiokSliderVolume.setSliderListener(object : ISliderScrollListener {
            override fun onScrollEnd(currentPercent: Float, currentValue: Float, rod: MRod) {
                _currentVolume = (currentPercent * _intervalVolume).roundToInt()
            }
        })

        MediaKAudio.instance.addAudiosToPlayList(_audioList)
        PostKEventLiveData.instance.with<MAudioKInfo?>(CMediaKAudioCons.Event.AUDIO_START).observe(this) {
            if (it != null) {
                Log.d(TAG, "initData: audio_start")
                _popwinAudio.setTitle(it.name)
                if (!_popwinAudio.isShowing) {
                    _popwinAudio.showPopupWindow()
                }
            }
        }
        PostKEventLiveData.instance.with<Pair<MAudioKInfo, Boolean>?>(CMediaKAudioCons.Event.AUDIO_POPUP).observe(this) {
            if (it != null) {
                Log.d(TAG, "initData: audio_popup")
                if (!it.second) {
                    _popwinAudio.dismiss()
                }
            }
        }
    }

    class PopwinKAudio(context: Context) : BasePopwinKLifecycle(context) {
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
            PostKEventLiveData.instance.with<MAudioKProgress?>(CMediaKAudioCons.Event.PROGRESS_UPDATE).observe(this) {
                if (it != null) {
                    Log.d(TAG, "initData: progress_update" + " progress status ${it.status} currentPos ${it.currentPos} duration ${it.duration} audioInfo ${it.audioInfo}")
                    _slider.updateRodPercent(it.currentPos.toFloat() / it.duration.toFloat())
                }
            }
        }

        override fun onCreateShowAnimation(): Animation {
            return AnimKBuilder.asAnimation().add(AnimKTranslationType.FROM_TOP_SHOW).build()
        }

        override fun onCreateDismissAnimation(): Animation {
            return AnimKBuilder.asAnimation().add(AnimKTranslationType.TO_TOP_HIDE).build()
        }
    }
}