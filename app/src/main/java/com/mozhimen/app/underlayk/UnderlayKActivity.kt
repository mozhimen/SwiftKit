package com.mozhimen.app.underlayk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.underlayk.debugk.DebugKActivity
import com.mozhimen.app.underlayk.logk.LogKActivity
import com.mozhimen.basick.extsk.start

class UnderlayKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_underlayk)
    }

    fun goLogK(view: View) {
        start<LogKActivity>()
    }

    fun goDebugK(view: View) {
        start<DebugKActivity>()
    }
}