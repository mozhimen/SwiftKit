package com.mozhimen.componentktest.videok

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.os.UtilKPath
import com.mozhimen.basick.utilk.res.UtilKAssets
import com.mozhimen.componentktest.databinding.ActivityVideokBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoKActivity : BaseActivityVB<ActivityVideokBinding>() {

    private val _path by lazy { UtilKPath.Absolute.Internal.getFilesDir() + "/videoPath/" }
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
            UtilKAssets.asset2File("layoutk_video.mp4", _path + "layoutk_video.mp4")
            UtilKAssets.asset2File("layoutk_video2.mp4", _path + "layoutk_video2.mp4")
            withContext(Dispatchers.Main) {
                vb.layoutkVideo2.initVideo(_currentUrl!!)
            }
        }
        vb.btnChangeVideo.setOnClickListener {
            vb.layoutkVideo2.changeVideo(_currentUrl!!)
        }
    }
}