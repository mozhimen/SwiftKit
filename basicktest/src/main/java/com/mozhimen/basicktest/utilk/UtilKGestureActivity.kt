package com.mozhimen.basicktest.utilk

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.basicktest.databinding.ActivityUtilkGestureBinding

class UtilKGestureActivity : BaseKActivityVB<ActivityUtilkGestureBinding>() {

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

    override fun initData(savedInstanceState: Bundle?) {

    }
}