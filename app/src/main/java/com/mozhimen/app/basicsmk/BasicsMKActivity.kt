package com.mozhimen.app.basicsmk

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.R
import com.mozhimen.app.basicsmk.databusmk.DataBusMKActivity
import com.mozhimen.app.basicsmk.permissionmk.PermissionMKActivity
import com.mozhimen.app.basicsmk.stackmk.StackMKActivity

class BasicsMKActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basicsmk)
    }

    fun goDataBusMKActivity(view: View) {
        startActivity(Intent(this, DataBusMKActivity::class.java))
    }

    fun goPermissionMKActivity(view: View) {
        startActivity(Intent(this, PermissionMKActivity::class.java))
    }

    fun goStackMKActivity(view: View) {
        startActivity(Intent(this, StackMKActivity::class.java))
    }
}