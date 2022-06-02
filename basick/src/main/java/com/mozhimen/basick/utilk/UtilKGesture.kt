package com.mozhimen.basick.utilk

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * @ClassName UtilKGesture
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/28 14:05
 * @Version 1.0
 */
object UtilKGesture {

    private const val TAG = "UtilKGesture>>>>>"

    /**
     * 计算手指间间距
     * @param event MotionEvent
     * @return Float
     */
    fun fingerDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    interface IFlingListener {
        fun onFlingUp()
        fun onFlingDown()
        fun onFlingLeft()
        fun onFlingRight()
    }

    open class GestureFlingCallback : IFlingListener, GestureDetector.SimpleOnGestureListener() {
        private val _minDistance = 200 //最小识别距离
        private val _minVelocity = 20 //最小识别速度

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            //大于设定的最小滑动距离并且在水平/竖直方向速度绝对值大于设定的最小速度，则执行相应方法
            if (e1.x - e2.x > _minDistance && abs(velocityX) > _minVelocity) {
                onFlingLeft()
            } else if (e2.x - e1.x > _minDistance && abs(velocityX) > _minVelocity) {
                onFlingRight()
            } else if (e1.y - e2.y > _minDistance && abs(velocityY) > _minVelocity) {
                onFlingUp()
            } else if (e2.y - e1.y > _minDistance && abs(velocityY) > _minVelocity) {
                onFlingDown()
            }
            return false
        }

        override fun onFlingUp() {

        }

        override fun onFlingDown() {

        }

        override fun onFlingLeft() {

        }

        override fun onFlingRight() {

        }
    }
}