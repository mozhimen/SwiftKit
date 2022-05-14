package com.mozhimen.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.abilityk.AbilityKActivity
import com.mozhimen.app.basicsk.BasicsKActivity
import com.mozhimen.app.componentk.ComponentKActivity
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.app.uicorek.UICoreKActivity
import com.mozhimen.basicsk.extsk.start

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goDemo(view: View) {
        start<DemoActivity>()
    }

    fun goComponentK(view: View) {
        start<ComponentKActivity>()
    }

    fun goAbilityK(view: View) {
        start<AbilityKActivity>()
    }

    fun goBasicsK(view: View) {
        start<BasicsKActivity>()
    }

    fun goUICoreK(view: View) {
        start<UICoreKActivity>()
    }
}