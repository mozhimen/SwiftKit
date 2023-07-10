package com.mozhimen.basicktest.taskk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.taskk.temps.ITaskKCountDownListener
import com.mozhimen.basick.taskk.temps.TaskKCountDown
import com.mozhimen.basicktest.databinding.ActivityTaskkCountDownBinding
import kotlin.math.roundToInt

class TaskKCountDownActivity : BaseActivityVB<ActivityTaskkCountDownBinding>() {

    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    private val _taskKCountDown: TaskKCountDown by lazy { TaskKCountDown().apply { bindLifecycle(this@TaskKCountDownActivity) } }

    @OptIn(AOptInInitByLazy::class, AOptInNeedCallBindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _taskKCountDown.start(10000, object : ITaskKCountDownListener {
            override fun onTick(millisUntilFinished: Long) {
                vb.taskkCountDownTxt.text = (millisUntilFinished.toFloat() / 1000f).roundToInt().toString()
            }

            override fun onFinish() {
                vb.taskkCountDownTxt.text = "结束啦"
            }
        })
    }
}