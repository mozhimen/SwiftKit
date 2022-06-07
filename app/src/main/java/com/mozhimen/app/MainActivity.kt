package com.mozhimen.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.abilityk.AbilityKActivity
import com.mozhimen.app.basick.BasicKActivity
import com.mozhimen.app.componentk.ComponentKActivity
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.app.uicorek.UICoreKActivity
import com.mozhimen.app.underlayk.UnderlayKActivity
import com.mozhimen.basick.extsk.start

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

    fun goBasicK(view: View) {
        start<BasicKActivity>()
    }

    fun goUICoreK(view: View) {
        start<UICoreKActivity>()
    }

    fun goUnderlayK(view: View) {
        start<UnderlayKActivity>()
    }
}