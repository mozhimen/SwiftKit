package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorektest.databinding.ActivityLayoutkVideoBinding

class LayoutKVideoActivity : BaseKActivityVB<ActivityLayoutkVideoBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkVideo2.startVideo("layoutk_video.mp4") {
            //vb.layoutkVideo2.destroyVideo()
        }
    }
}