package com.mozhimen.uicorek.refreshk.helpers

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * @ClassName RefreshKGestureDetector
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/17 23:48
 * @Version 1.0
 */
open class RefreshKGestureDetector : GestureDetector.OnGestureListener {
    override fun onDown(e: MotionEvent?): Boolean = false

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean = false

    override fun onLongPress(e: MotionEvent?) {}

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean = false
}