package com.mozhimen.uicorektest.layoutk

import android.graphics.Color
import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.annors.ADigitPlace
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.taskk.temps.TaskKPoll
import com.mozhimen.uicorek.layoutk.roll.annors.AAnimatorMode
import com.mozhimen.uicorektest.databinding.ActivityLayoutkRollBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LayoutKRollActivity : BaseActivityVB<ActivityLayoutkRollBinding>() {
    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    private val _taskKPoll by lazy { TaskKPoll() }
    private val list = "我喜欢唱跳RAP篮球!"

    @OptIn(OptInApiCall_BindLifecycle::class, OptInApiInit_ByLazy::class)
    override fun initView(savedInstanceState: Bundle?) {
        vb.rollTextDigit.setTextViewStyle(20f, Color.WHITE)
        vb.rollTextDigit.post {
            _taskKPoll.start(1000L, 10, task = { index ->
                withContext(Dispatchers.Main) {
                    vb.rollTextDigit.setCurrentValue(index, ADigitPlace.PLACE_UNIT, AAnimatorMode.DOWN)
                    vb.rollTextTxt.setCurrentValue(list[10 - index].toString(), 300, AAnimatorMode.UP)
                }
            })
        }
    }
}