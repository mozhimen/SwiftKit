package com.mozhimen.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.basicsmk.BasicsMKActivity
import com.mozhimen.app.componentmk.ComponentMKActivity
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.app.uicoremk.UICoreMKActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goDemo(view: View) {
        startActivity(Intent(this, DemoActivity::class.java))
    }

    fun goComponentMK(view: View) {
        startActivity(Intent(this, ComponentMKActivity::class.java))
    }

    fun goBasicsMK(view: View) {
        startActivity(Intent(this, BasicsMKActivity::class.java))
    }

    fun goUICoreMK(view: View) {
        startActivity(Intent(this, UICoreMKActivity::class.java))
    }
}