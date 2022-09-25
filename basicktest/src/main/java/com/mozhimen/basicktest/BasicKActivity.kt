package com.mozhimen.basicktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.basick.basek.BaseKDemoActivity
import com.mozhimen.basicktest.basick.eventk.EventKActivity
import com.mozhimen.basicktest.basick.executork.ExecutorKActivity
import com.mozhimen.basicktest.basick.stackk.StackKActivity
import com.mozhimen.basicktest.basick.utilk.UtilKActivity
import com.mozhimen.basicktest.databinding.ActivityBasickBinding

class BasicKActivity : BaseKActivity<ActivityBasickBinding, BaseKViewModel>(R.layout.activity_basick) {

    override fun initData(savedInstanceState: Bundle?) {
        //黏性事件测试
        //DataBusK.with<String>("stickyData").setStickyData("stickyData from ComponentKActivity")
    }

    fun goStackK(view: View) {
        start<StackKActivity>()
    }

    fun goBaseKDemo(view: View) {
        start<BaseKDemoActivity>()
    }

    fun goExecutorK(view: View) {
        start<ExecutorKActivity>()
    }

    fun goUtilK(view: View) {
        start<UtilKActivity>()
    }

    fun goEventK(view: View) {
        start<EventKActivity>()
    }
}