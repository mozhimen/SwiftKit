package com.mozhimen.basick.elemk.android.view.bases

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.View.OnTouchListener
import com.mozhimen.basick.elemk.android.view.commons.IOnClickListener
import com.mozhimen.basick.elemk.android.view.commons.IOnDoubleTapListener
import com.mozhimen.basick.elemk.android.view.commons.IOnScaleListener
import com.mozhimen.basick.elemk.android.view.commons.IOnZoomListener
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.IUtilK
import kotlin.math.sqrt

/**
 * @ClassName CameraKXGestureDetector
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/11 20:42
 * @Version 1.0
 */
open class BaseGestureDetector(context: Context) : IUtilK, IOnClickListener, OnTouchListener, IOnScaleListener {
    private var _gestureDetector: GestureDetector? = null
    private var _scaleGestureDetector: ScaleGestureDetector? = null
//    private var _distanceCurrent = 0f//缩放相关
//    private var _distanceLast = 0f

    //////////////////////////////////////////////////////////////////////////////////////

    private val _onGestureListener = object : GestureDetector.SimpleOnGestureListener() {
//        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
//            if (e2.pointerCount >= 2) {// 大于两个触摸点
//                val offSetX: Float = e2.getX(0) - e2.getX(1)//event中封存了所有屏幕被触摸的点的信息，第一个触摸的位置可以通过event.getX(0)/getY(0)得到
//                val offSetY: Float = e2.getY(0) - e2.getY(1)
//                _distanceCurrent = sqrt(offSetX * offSetX + offSetY * offSetY)//运用三角函数的公式，通过计算X,Y坐标的差值，计算两点间的距离
//                if (_distanceLast == 0f) {//如果是第一次进行判断
//                    _distanceLast = _distanceCurrent
//                } else {
//                    if (_distanceCurrent - _distanceLast > 10) onZoomUp()// 放大
//                    else if (_distanceLast - _distanceCurrent > 10) onZoomDown()// 缩小
//                }
//                //在一次缩放操作完成后，将本次的距离赋值给_distanceLast，以便下一次判断
//                //但这种方法写在move动作中，意味着手指一直没有抬起，监控两手指之间的变化距离超过10
//                //就执行缩放操作，不是在两次点击之间的距离变化来判断缩放操作
//                //故这种将本次距离留待下一次判断的方法，不能在两次点击之间使用
//                _distanceLast = _distanceCurrent
//            }
//            return true
//        }

        override fun onLongPress(e: MotionEvent) {
            onLongClick(e.x, e.y)
        }

//        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//            _distanceCurrent = 0f
//            _distanceLast = 0f
//            return true
//        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            "onSingleTapConfirmed: 严格的单击".dt(TAG)
            onSingleClick(e.x, e.y)
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            "onDoubleTap: 双击".dt(TAG)
            onDoubleClick(e.x, e.y)
            return true
        }
    }

    private val _onScaleGestureListener: SimpleOnScaleGestureListener = object : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            onScale(detector.scaleFactor)
            return true
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////

    init {
        _gestureDetector = GestureDetector(context, _onGestureListener)
        _scaleGestureDetector = ScaleGestureDetector(context, _onScaleGestureListener)
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val scale = _scaleGestureDetector!!.onTouchEvent(event)
        val gesture = _gestureDetector!!.onTouchEvent(event)
        return scale || gesture
    }

    //////////////////////////////////////////////////////////////////////////////////////

//    override fun onZoomUp() {}//放大
//
//    override fun onZoomDown() {}//缩小

    override fun onScale(scaleFactor: Float) {}//缩放

    override fun onSingleClick(x: Float, y: Float) {}//点击

    override fun onDoubleClick(x: Float, y: Float) {}//双击

    override fun onLongClick(x: Float, y: Float) {}//长按
}

