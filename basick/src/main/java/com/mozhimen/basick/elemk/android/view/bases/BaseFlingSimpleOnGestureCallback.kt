package com.mozhimen.basick.elemk.android.view.bases

import android.view.GestureDetector
import android.view.MotionEvent
import com.mozhimen.basick.elemk.android.view.commons.IOnFlingListener
import kotlin.math.abs

/**
 * @ClassName GestureFlingCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/27 0:15
 * @Version 1.0
 */
open class BaseFlingSimpleOnGestureCallback : IOnFlingListener, GestureDetector.SimpleOnGestureListener() {
    private val _minDistance = 200 //最小识别距离
    private val _minVelocity = 20 //最小识别速度

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        //大于设定的最小滑动距离并且在水平/竖直方向速度绝对值大于设定的最小速度，则执行相应方法
        if (e1.x - e2.x > _minDistance && abs(velocityX) > _minVelocity) onFlingLeft()
        else if (e2.x - e1.x > _minDistance && abs(velocityX) > _minVelocity) onFlingRight()
        else if (e1.y - e2.y > _minDistance && abs(velocityY) > _minVelocity) onFlingUp()
        else if (e2.y - e1.y > _minDistance && abs(velocityY) > _minVelocity) onFlingDown()
        return false
    }
}