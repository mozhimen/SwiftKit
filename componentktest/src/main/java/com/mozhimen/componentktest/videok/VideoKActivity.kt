package com.mozhimen.componentktest.videok

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.componentktest.databinding.ActivityVideokBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoKActivity : BaseActivityVB<ActivityVideokBinding>() {

    private val _path by lazy { UtilKStrPath.Absolute.Internal.getFilesDir() + "/videoPath/" }
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
            UtilKAsset.asset2file("layoutk_video.mp4", _path + "layoutk_video.mp4")
            UtilKAsset.asset2file("layoutk_video2.mp4", _path + "layoutk_video2.mp4")
            withContext(Dispatchers.Main) {
                vb.layoutkVideo2.initVideo(_currentUrl!!)
            }
        }
        vb.btnChangeVideo.setOnClickListener {
            vb.layoutkVideo2.changeVideo(_currentUrl!!)
        }
    }
}