package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.uicorektest.databinding.ActivityLayoutkVideoBinding

class LayoutKVideoActivity : BaseActivityVB<ActivityLayoutkVideoBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkVideo2.startVideo("layoutk_video.mp4") {
            //vb.layoutkVideo2.destroyVideo()
        }
    }
}