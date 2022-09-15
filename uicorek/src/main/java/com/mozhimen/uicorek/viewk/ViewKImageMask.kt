package com.mozhimen.uicorek.viewk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GestureDetectorCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.basek.commons.IBaseKView
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.utilk.UtilKView

/**
 * @ClassName ViewKImageMask
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/14 16:03
 * @Version 1.0
 */
class ViewKImageMask @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr)/*, IBaseKView*/ {

    /*//region
    private companion object {
        private val SCALE_PERCENT_MIN = 0.2f
        private val SCALE_PERCENT_MAX = 1f
    }

    private lateinit var _paint: Paint
    private var _borderColor = Color.BLACK
    private var _borderWidth = 2f.dp2px().toFloat()

    private val _scalePercent = 0.5f
    private var _isMoveOrScale = false
    private var _maskRect: MaskRect = MaskRect(0f, 0f, 0f, 0f)
    private var _centerX = 0f
    private var _centerY = 0f

    private val _viewKParentScrollable: ViewGroup?
        get() = UtilKView.getParentViewMatch(this, ScrollView::class.java, NestedScrollView::class.java, RecyclerView::class.java) as ViewGroup?
    //endregion

    init {
        initPaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _centerX = w / 2f
        _centerY = h / 2f
        _maskRect = MaskRect(w * _scalePercent, h * _scalePercent, _centerX, _centerY)
    }

    override fun initFlag() {
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
    }

    override fun initPaint() {
    }

    override fun initData() {
        _paint = Paint()
        _paint.color = _borderColor
        _paint.style = Paint.Style.STROKE
        _paint.strokeWidth = _borderWidth
    }

    override fun initView() {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawRect(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                _viewKParentScrollable?.requestDisallowInterceptTouchEvent(false)
                _isMoveOrScale = false
            }
            MotionEvent.ACTION_DOWN -> {
                _isMoveOrScale = if (_maskRect.isTapInArea(event)) {
                    return true
                } else {
                    true
                }
                _viewKParentScrollable?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (_isMoveOrScale) {
                    if (event.pointerCount == 1) {
                        var evX = event.x
                        evX -= _sliderPaddingHorizontal
                        if (evX < 0) {
                            evX = 0f
                        }
                        if (evX > _sliderWidth) {
                            evX = _sliderWidth
                        }
                        _rodX = evX
                    } else if (event.pointerCount == 2) {

                    }
                }
            }
        }
        return true
    }

    private fun drawRect(canvas: Canvas) {

    }

    private class MaskRect {
        var width: Float = 0f
            set(value) {
                field = value
                refresh()
            }
        var height: Float = 0f
            set(value) {
                field = value
                refresh()
            }
        var centerX: Float = 0f
            set(value) {
                field = value
                refresh()
            }
        var centerY: Float = 0f
            set(value) {
                field = value
                refresh()
            }

        var leftX: Float = 0f
        var rightX: Float = 0f
        var topY: Float = 0f
        var bottomY: Float = 0f

        constructor(width: Float, height: Float, centerX: Float, centerY: Float) {
            this.width = width
            this.height = height
            this.centerX = centerX
            this.centerY = centerY
        }

        fun refresh() {
            leftX = centerX - (width / 2f)
            leftX = centerX + (width / 2f)
            topY = centerY - (height / 2f)
            bottomY = centerY + (height / 2f)
        }

        fun isTapInArea(e: MotionEvent): Boolean = e.x in leftX..rightX && e.y >= topY && e.y < bottomY
    }*/
}