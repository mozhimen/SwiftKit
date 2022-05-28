package com.mozhimen.app.basick

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.basick.basek.BaseKDemoActivity
import com.mozhimen.app.basick.executork.ExecutorKActivity
import com.mozhimen.app.basick.logk.LogKActivity
import com.mozhimen.app.basick.stackk.StackKActivity
import com.mozhimen.app.basick.utilk.UtilKActivity
import com.mozhimen.basick.extsk.start

class BasicKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basick)
    }

    fun goLogKActivity(view: View) {
        start<LogKActivity>()
    }

    fun goStackKActivity(view: View) {
        start<StackKActivity>()
    }

    fun goBaseKDemoActivity(view: View) {
        start<BaseKDemoActivity>()
    }

    fun goExecutorKActivity(view: View) {
        start<ExecutorKActivity>()
    }

    fun goUtilKActivity(view: View) {
        start<UtilKActivity>()
    }
}