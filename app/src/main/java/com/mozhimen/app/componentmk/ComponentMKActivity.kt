package com.mozhimen.app.componentmk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.componentmk.basemk.BaseMKDemoActivity

class ComponentMKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentmk)
    }

    fun goBaseMKActivity(view: View) {
        startActivity(Intent(this, BaseMKDemoActivity::class.java))
    }
}