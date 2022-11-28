package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.UtilKAsset
import com.mozhimen.uicorektest.databinding.ActivityLayoutkVideoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LayoutKVideoActivity : BaseActivityVB<ActivityLayoutkVideoBinding>() {

    private val _path by lazy { this.filesDir.absolutePath + "/videoPath/" }
    private var _currentUrl: String? = null
        get() {
            return when (field) {
                null -> _path + "layoutk_video.mp4"
                _path + "layoutk_video.mp4" -> _path + "layoutk_video2.mp4"
                else -> _path + "layoutk_video.mp4"
            }.also { field = it }
        }

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch(Dispatchers.IO) {
            UtilKAsset.asset2File("layoutk_video.mp4", _path + "layoutk_video.mp4")
            UtilKAsset.asset2File("layoutk_video2.mp4", _path + "layoutk_video2.mp4")
            withContext(Dispatchers.Main) {
                vb.layoutkVideo2.initVideo(_currentUrl!!)
            }
        }
        vb.btnChangeVideo.setOnClickListener {
            vb.layoutkVideo2.changeVideo(_currentUrl!!)
        }
    }
}