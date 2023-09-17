package com.mozhimen.basicktest.elemk.android

import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.elemk.android.view.bases.BaseOnFlingCallback
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityUtilkGestureBinding

class ElemKGestureFlingActivity : BaseActivityVB<ActivityUtilkGestureBinding>() {

    private val _gestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : BaseOnFlingCallback() {
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