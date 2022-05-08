package com.mozhimen.app.componentk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.basicsk.basek.BaseKDemoActivity
import com.mozhimen.app.componentk.guidek.GuideKActivity
import com.mozhimen.basicsk.extsk.start

class ComponentKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentk)
    }

    fun goGuideKActivity(view: View) {
        start<GuideKActivity>()
    }
}