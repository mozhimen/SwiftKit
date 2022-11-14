package com.mozhimen.basicktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.basek.BaseKDemoStartActivity
import com.mozhimen.basicktest.taskk.TaskKThreadPoolActivity
import com.mozhimen.basicktest.stackk.StackKActivity
import com.mozhimen.basicktest.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding
import com.mozhimen.basicktest.prefabk.PrefabKActivity
import com.mozhimen.basicktest.taskk.TaskKActivity

class BasicKActivity : BaseKActivityVB<ActivityBasickBinding>() {

    fun goBaseKDemo(view: View) {
        start<BaseKDemoStartActivity>()
    }

    fun goPrefabK(view: View) {
        start<PrefabKActivity>()
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

    override fun initData(savedInstanceState: Bundle?) {

    }
}