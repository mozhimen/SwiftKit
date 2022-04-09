package com.mozhimen.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.basicsk.BasicsKActivity
import com.mozhimen.app.componentk.ComponentKActivity
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.app.uicorek.UICoreKActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goDemo(view: View) {
        startActivity(Intent(this, DemoActivity::class.java))
    }

    fun goComponentK(view: View) {
        startActivity(Intent(this, ComponentKActivity::class.java))
    }

    fun goBasicsK(view: View) {
        startActivity(Intent(this, BasicsKActivity::class.java))
    }

    fun goUICoreK(view: View) {
        startActivity(Intent(this, UICoreKActivity::class.java))
    }
}