package com.mozhimen.componentktest.audiok

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.componentk.audiok.AudioK
import com.mozhimen.componentk.audiok.cons.CAudioKEvent
import com.mozhimen.componentk.audiok.mos.MAudioK
import com.mozhimen.componentk.audiok.mos.MAudioKProgress
import com.mozhimen.componentktest.databinding.ActivityAudiokBinding

class AudioKActivity : BaseKActivityVB<ActivityAudiokBinding>() {
    private val _audioList = arrayListOf(
        MAudioK("01", "http://192.168.2.6/construction-sites-images/voice/20221018//9b94d721ed244fa892b15112bc11a3ce.wav"),
        MAudioK("02", "http://192.168.2.9/construction-sites-images/voice/20221024/1237378768e7q8e7r8qwesafdasdfasdfaxss111.speex"),
        MAudioK("03", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3"),
    )

    override fun initData(savedInstanceState: Bundle?) {
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
}