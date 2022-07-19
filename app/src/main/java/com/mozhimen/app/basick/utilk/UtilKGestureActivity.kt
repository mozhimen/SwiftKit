package com.mozhimen.app.basick.utilk

import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityUtilkGestureBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKGesture

class UtilKGestureActivity : BaseKActivity<ActivityUtilkGestureBinding, BaseKViewModel>(R.layout.activity_utilk_gesture) {

    private val _gestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : UtilKGesture.GestureFlingCallback() {
            override fun onFlingLeft() {
                "左划".showToast()
            }

            override fun onFlingDown() {
                "下划".showToast()
            }

            override fun onFlingRight() {
                "右划".showToast()
            }

            override fun onFlingUp() {
                "上划".showToast()
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return _gestureDetector.onTouchEvent(event)
    }
}