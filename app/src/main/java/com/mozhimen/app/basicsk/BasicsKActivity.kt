package com.mozhimen.app.basicsk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.basicsk.basek.BaseKDemoActivity
import com.mozhimen.app.basicsk.databusk.DataBusKActivity
import com.mozhimen.app.basicsk.executork.ExecutorKActivity
import com.mozhimen.app.basicsk.logk.LogKActivity
import com.mozhimen.app.basicsk.permissionk.PermissionKActivity
import com.mozhimen.app.basicsk.stackk.StackKActivity
import com.mozhimen.app.basicsk.utilk.UtilKActivity
import com.mozhimen.basicsk.extsk.start

class BasicsKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basicsk)

        //黏性事件测试
        //DataBusK.with<String>("stickyData").setStickyData("stickyData from ComponentKActivity")
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

    fun goDataBusKActivity(view: View) {
        start<DataBusKActivity>()
    }

    fun goPermissionKActivity(view: View) {
        start<PermissionKActivity>()
    }

    fun goExecutorKActivity(view: View) {
        start<ExecutorKActivity>()
    }

    fun goUtilKActivity(view: View) {
        start<UtilKActivity>()
    }
}