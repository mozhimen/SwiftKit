package com.mozhimen.debugktest

import android.os.Bundle
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.debugk.global.DebugK
import com.mozhimen.debugktest.databinding.ActivityDebugkBinding

class DebugKActivity : BaseKActivityVB<ActivityDebugkBinding>() {

    private val _gestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : UtilKGesture.GestureFlingCallback() {
            override fun onFlingLeft() {
                toggleDebugDialog()
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            toggleDebugDialog()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return _gestureDetector.onTouchEvent(event)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    fun toggleDebugDialog() {
        if (BuildConfig.DEBUG) {
            DebugK.toggleDialog(this.supportFragmentManager)
        }
    }
}