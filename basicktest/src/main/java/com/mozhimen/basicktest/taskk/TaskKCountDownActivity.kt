package com.mozhimen.basicktest.taskk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.taskk.temps.ITaskKCountDownListener
import com.mozhimen.basick.taskk.temps.TaskKCountDown
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityTaskkCountDownBinding
import kotlin.math.roundToInt

class TaskKCountDownActivity : BaseActivityVB<ActivityTaskkCountDownBinding>() {

    private val _taskKCountDown: TaskKCountDown by lazy { TaskKCountDown().apply { bindLifecycle(this@TaskKCountDownActivity) } }

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