package com.mozhimen.basicktest.utilk

import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.gesture.commons.GestureFlingCallback
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basicktest.databinding.ActivityUtilkGestureBinding

class UtilKGestureActivity : BaseActivityVB<ActivityUtilkGestureBinding>() {

    private val _gestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : GestureFlingCallback() {
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return _gestureDetector.onTouchEvent(event)
    }

}