package com.mozhimen.app.underlayk.debugk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import com.mozhimen.app.R
import com.mozhimen.underlayk.debugk.DebugK

class DebugKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debugk)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            DebugK.toggleDebugKDialog(this.supportFragmentManager)
        }
        return super.onKeyDown(keyCode, event)
    }
}