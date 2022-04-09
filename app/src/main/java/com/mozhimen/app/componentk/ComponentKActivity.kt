package com.mozhimen.app.componentk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.componentk.basek.BaseKDemoActivity
import com.mozhimen.app.componentk.navigationk.NavigationKActivity
import com.mozhimen.basicsk.databusk.DataBusK

class ComponentKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentk)
    }

    fun goBaseKDemoActivity(view: View) {
        startActivity(Intent(this, BaseKDemoActivity::class.java))
    }

    fun goNavigationKActivity(view: View) {
        startActivity(Intent(this, NavigationKActivity::class.java))
    }
}