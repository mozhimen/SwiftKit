package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkVideoBinding

class LayoutKVideoActivity : BaseKActivity<ActivityLayoutkVideoBinding, BaseKViewModel>(R.layout.activity_layoutk_video) {

    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkVideo2.startVideo("layoutk_video.mp4") {
            //vb.layoutkVideo2.destroyVideo()
        }
    }
}