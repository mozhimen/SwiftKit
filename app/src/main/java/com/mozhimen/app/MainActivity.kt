package com.mozhimen.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.basick.extsk.start

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goDemo(view: View) {
        start<DemoActivity>()
    }
}