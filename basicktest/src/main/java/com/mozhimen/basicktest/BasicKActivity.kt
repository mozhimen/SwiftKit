package com.mozhimen.basicktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.basek.BaseKDemoActivity
import com.mozhimen.basicktest.executork.ExecutorKActivity
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseKActivity<ActivityBasickBinding, BaseKViewModel>(R.layout.activity_basick) {

    fun goBaseKDemo(view: View) {
        start<BaseKDemoActivity>()
    }

    fun goExecutorK(view: View) {
        start<ExecutorKActivity>()
    }

    fun goStackK(view: View) {
        start<StackKActivity>()
    }

    fun goTaskK(view: View) {
        start<TaskKActivity>()
    }

    fun goUtilK(view: View) {
        start<UtilKActivity>()
    }
}