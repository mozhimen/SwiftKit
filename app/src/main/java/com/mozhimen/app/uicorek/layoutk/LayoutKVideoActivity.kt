package com.mozhimen.app.uicorek.layoutk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityLayoutkVideoBinding

class LayoutKVideoActivity : AppCompatActivity() {
    private val vb by lazy { ActivityLayoutkVideoBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.layoutkVideo2.startVideo("layoutk_video.mp4") {
            //vb.layoutkVideo2.destroyVideo()
        }
    }
}