package com.mozhimen.app.basicsk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.basicsk.databusk.DataBusKActivity
import com.mozhimen.app.basicsk.executork.ExecutorKActivity
import com.mozhimen.app.basicsk.logk.LogKActivity
import com.mozhimen.app.basicsk.permissionk.PermissionKActivity
import com.mozhimen.app.basicsk.stackk.StackKActivity
import com.mozhimen.basicsk.databusk.DataBusK
import com.mozhimen.basicsk.executork.ExecutorK

class BasicsKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basicsk)

        //黏性事件测试
        DataBusK.with<String>("stickyData").setStickyData("stickyData from ComponentKActivity")
    }

    fun goDataBusKActivity(view: View) {
        startActivity(Intent(this, DataBusKActivity::class.java))
    }

    fun goPermissionKActivity(view: View) {
        startActivity(Intent(this, PermissionKActivity::class.java))
    }

    fun goStackKActivity(view: View) {
        startActivity(Intent(this, StackKActivity::class.java))
    }

    fun goExecutorKActivity(view: View) {
        startActivity(Intent(this, ExecutorKActivity::class.java))
    }

    fun goLogKActivity(view: View) {
        startActivity(Intent(this, LogKActivity::class.java))
    }
}