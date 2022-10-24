package com.mozhimen.basicktest.prefabk

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.prefabk.PrefabKAudio
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityPrefabkAudioBinding

class PrefabKAudioActivity : BaseKActivityVB<ActivityPrefabkAudioBinding>() {
    private val prefabKAudio by lazy { PrefabKAudio(this) }
    override fun initData(savedInstanceState: Bundle?) {
        vb.prefabkAudioBtnPlayAsset.setOnClickListener {
            Log.d(TAG, "initData: this is first audio")
            prefabKAudio.play(R.raw.prefabk_audio_wav)
            Log.d(TAG, "initData: this is second audio")
            prefabKAudio.play(R.raw.prefabk_audio_mp3)
        }
        vb.prefabkAudioBtnPlayUrl.setOnClickListener {

        }
    }
}