package com.mozhimen.basicktest.utilk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKAudio
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkAudioBinding

class UtilKAudioActivity : BaseKActivity<ActivityUtilkAudioBinding, BaseKViewModel>(R.layout.activity_utilk_audio) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.utilkAudioBtn.setOnClickListener {
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
            UtilKAudio.getInstance(this).play(R.raw.utilk_audio_test)
        }
    }
}