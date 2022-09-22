package com.mozhimen.app.componentk.debugk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import com.mozhimen.app.BuildConfig
import com.mozhimen.app.databinding.ActivityDebugkBinding
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.componentk.debugk.DebugK

class DebugKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDebugkBinding.inflate(layoutInflater) }
    private val TAG = "DebugKActivity>>>>>"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
    }

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

    fun toggleDebugDialog() {
        if (BuildConfig.DEBUG) {
            DebugK.toggleDialog(this.supportFragmentManager)
        }
    }
}