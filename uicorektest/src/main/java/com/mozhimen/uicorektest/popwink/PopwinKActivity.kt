package com.mozhimen.uicorektest.popwink

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.uicorek.popwink.PopwinKAudio
import com.mozhimen.uicorektest.databinding.ActivityPopwinkBinding


/**
 * @ClassName PopwinKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/17 16:45
 * @Version 1.0
 */
class PopwinKActivity : BaseKActivityVB<ActivityPopwinkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {

    }

    private val _popwinAudioK by lazy { PopwinKAudio(this) }
    fun onPopwinKAudio(view: View) {
        _popwinAudioK.showPopupWindow()
    }
}