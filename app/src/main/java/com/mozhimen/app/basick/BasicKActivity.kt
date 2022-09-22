package com.mozhimen.app.basick

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.basick.basek.BaseKDemoActivity
import com.mozhimen.app.basick.eventk.EventKActivity
import com.mozhimen.app.basick.executork.ExecutorKActivity
import com.mozhimen.app.underlayk.logk.LogKActivity
import com.mozhimen.app.basick.stackk.StackKActivity
import com.mozhimen.app.basick.utilk.UtilKActivity
import com.mozhimen.basick.extsk.start

class BasicKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basick)

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