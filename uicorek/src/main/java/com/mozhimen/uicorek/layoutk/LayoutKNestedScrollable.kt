package com.mozhimen.uicorek.layoutk

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * @ClassName LayoutKNestedScrollable
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/13 11:57
 * @Version 1.0
 */
class LayoutKNestedScrollable @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0) : BaseLayoutKFrame(context, attrs, defStyleRes) {

    private var _touchSlop = 0
    private var _initialX = 0f
    private var _initialY = 0f
    private val _parentViewPager: ViewPager2?
        get() {
            var view: View? = parent as? View
            while (view != null && view !is ViewPager2) {
                view = view.parent as? View
            }
            return view as? ViewPager2
        }

    private val _child: View? get() = if (childCount > 0) getChildAt(0) else null

    ///////////////////////////////////////////////////////////////////////////////////////////

    init {
        _touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            0 -> _child?.canScrollHorizontally(direction) ?: false
            1 -> _child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    private fun handleInterceptTouchEvent(e: MotionEvent) {
        val orientation = _parentViewPager?.orientation ?: return

        // Early return if child can't scroll in same direction as parent
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            _initialX = e.x
            _initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - _initialX
            val dy = e.y - _initialY
            val isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL

            // assuming ViewPager2 touch-slop is 2x touch-slop of child
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

            if (scaledDx > _touchSlop || scaledDy > _touchSlop) {
                if (isVpHorizontal == (scaledDy > scaledDx)) {
                    // Gesture is perpendicular, allow all parents to intercept
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    // Gesture is parallel, query child if movement in that direction is possible
                    if (canChildScroll(orientation, if (isVpHorizontal) dx else dy)) {
                        // Child can scroll, disallow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(true)
                    } else {
                        // Child cannot scroll, allow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
            }
        }
    }
}