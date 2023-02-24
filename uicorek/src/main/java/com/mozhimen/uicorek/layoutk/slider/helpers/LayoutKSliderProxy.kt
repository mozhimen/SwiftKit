package com.mozhimen.uicorek.layoutk.slider.helpers

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import com.mozhimen.uicorek.viewk.commons.IViewK
import com.mozhimen.basick.utilk.view.gesture.UtilKGesture
import com.mozhimen.uicorek.layoutk.slider.LayoutKSlider
import com.mozhimen.uicorek.layoutk.slider.commons.ILayoutKSlider
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderScrollListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.layoutk.slider.mos.MSlider
import com.mozhimen.uicorek.layoutk.slider.mos.MSliderAttrs

/**
 * @ClassName LayoutKSliderProxy
 * @Description
 *
 *  minLabel               maxLabel LabelTop
 *  minVal    currentVal    maxVal  ValTop
 *
 *               rod    section
 * |----------<======>----||-----| Slider
 *           ___/\___
 *          |  tip  |             Bubble
 *
 *  minLabel               maxLabel LabelBottom
 *  minVal    currentVal    maxVal  ValBottom
 *
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 14:33
 * @Version 1.0
 */

internal class LayoutKSliderProxy(
    private val _context: Context
) : IViewK, ILayoutKSlider {

    //region # variate
    private lateinit var _layoutKSlider: LayoutKSlider
    private lateinit var _attrs: MSliderAttrs
    private val _slider: MSlider by lazy { MSlider() }
    private val _rod: MRod by lazy { MRod() }
    private var _scrollableParentView: ViewGroup? = null
    private var _rodIsScrolling = false
    private var _isEnableScroll = true

    private lateinit var _paintRightSlider: Paint
    private lateinit var _paintLeftSlider: Paint
    private lateinit var _paintRod: Paint

    private var _sliderListener: ISliderScrollListener? = null
    //endregion

    //region # public func
    fun init(layoutKSlider: LayoutKSlider) {
        _layoutKSlider = layoutKSlider
    }

    fun getRod(): MRod {
        return _rod
    }

    fun getSlider(): MSlider {
        return _slider
    }

    fun setSliderListener(sliderListener: ISliderScrollListener) {
        _sliderListener = sliderListener
    }

    fun setRodDefaultPercent(percent: Float) {
        _rod.currentPercent = percent
    }

    fun getHeightMeasureSpec(): Int {
        var height = 0
        height += if (!_attrs.rodIsInside) _attrs.rodRadius.toInt() * 2 else _attrs.sliderHeight.toInt()
        height += AttrsParser.DEFAULT_PADDING_VERTICAL * 2
        return height
    }

    fun attachScrollableParentView(scrollableParentView: ViewGroup?) {
        _scrollableParentView = scrollableParentView
    }
    //endregion

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        _attrs = AttrsParser.parseAttrs(_context, attrs)
    }

    override fun initPaint() {
        _paintRod = Paint()
        _paintRod.isAntiAlias = true
        _paintRod.strokeWidth = 2f
        _paintRod.color = _attrs.rodColor

        _paintRightSlider = Paint()
        _paintRightSlider.isAntiAlias = true
        _paintRightSlider.strokeWidth = 2f
        _paintRightSlider.color = _attrs.sliderRodRightColor

        _paintLeftSlider = Paint()
        _paintLeftSlider.isAntiAlias = true
        _paintLeftSlider.strokeWidth = 2f
        _paintLeftSlider.color = _attrs.sliderRodLeftColor
    }

    override fun initView() {
        _isEnableScroll = _attrs.rodScrollEnable
        initSlider()
        initRod()
    }

    private fun initRod() {
        _rod.apply {
            isInsideSlider = _attrs.rodIsInside
            radius = _attrs.rodRadius
            radiusInside = _attrs.rodRadiusInside
            rodColor = _attrs.rodColor
            rodColorInside = _attrs.rodColorInside
            currentPercent = _attrs.rodDefaultPercent
        }
    }

    private fun initSlider() {
        _slider.apply {
            val distance = _attrs.rodRadius - (_attrs.sliderHeight / 2f)
            height = _attrs.sliderHeight
            topY = (if (!_attrs.rodIsInside) distance else 0f) + AttrsParser.DEFAULT_PADDING_VERTICAL
            rodLeftColor = _attrs.sliderRodLeftColor
            rodLeftGradientColor = _attrs.sliderRodLeftGradientColor
            rodRightColor = _attrs.sliderRodRightColor
        }
    }

    private fun refreshSlider() {
        _slider.apply {
            val distance = _rod.radius - (_slider.height / 2f)
            leftX = _layoutKSlider.paddingStart.toFloat() + if (!_rod.isInsideSlider) _rod.radius else _slider.height / 2f
            width = _layoutKSlider.width - _layoutKSlider.paddingStart.toFloat() - _layoutKSlider.paddingEnd - _slider.height - if (!_rod.isInsideSlider) distance * 2 else 0f
            if (_slider.rodLeftColor != _slider.rodLeftGradientColor) {
                _paintLeftSlider.shader =
                    LinearGradient(_slider.leftX, _slider.topY, _slider.rightX, _slider.bottomY, _slider.rodLeftColor, _slider.rodLeftGradientColor, Shader.TileMode.CLAMP)
            }
        }
    }

    private fun refreshRod() {
        _rod.apply {
            minX = _layoutKSlider.paddingStart.toFloat() + if (!_rod.isInsideSlider) _rod.radius else _slider.heightHalf
            maxX = _layoutKSlider.width - _layoutKSlider.paddingEnd.toFloat() - if (!_rod.isInsideSlider) _rod.radius else _slider.heightHalf
            centerY = _slider.centerY
        }
    }

    fun refreshView() {
        refreshSlider()
        refreshRod()
        _layoutKSlider.invalidate()
    }

    fun onDraw(canvas: Canvas) {
        //slider
        drawSlider(canvas)
        //rod
        drawRod(canvas)
    }

    private fun drawSlider(canvas: Canvas) {
        //rightSlider
        _paintRightSlider.color = _slider.rodRightColor
        canvas.drawCircle(
            _slider.leftX,
            _slider.centerY,
            _slider.heightHalf,
            _paintRightSlider
        )
        canvas.drawCircle(
            _slider.rightX,
            _slider.centerY,
            _slider.heightHalf,
            _paintRightSlider
        )
        canvas.drawRect(
            _slider.leftX,
            _slider.topY,
            _slider.rightX,
            _slider.bottomY,
            _paintRightSlider
        )

        //leftSlider
        _paintRightSlider.color = _slider.rodLeftColor
        if (_rod.currentVal > _rod.minVal) {
            val distance = if (!_rod.isInsideSlider) 0f else _slider.heightHalf - _rod.radius
            canvas.drawCircle(
                _slider.leftX,
                _slider.centerY,
                if (!_rod.isInsideSlider) _slider.heightHalf else _rod.radius,
                _paintRightSlider
            )
            canvas.drawRect(
                _slider.leftX,
                _slider.topY + distance,
                _rod.currentX,
                _slider.bottomY - distance,
                _paintLeftSlider
            )
        }
    }

    private fun drawRod(canvas: Canvas) {
        val color = _rod.rodColor
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radius, _paintRod)//外圆
        _paintRod.color = _rod.rodColorInside
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radiusInside, _paintRod)//内圆
        _paintRod.color = color
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (!_isEnableScroll) return true
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                _scrollableParentView?.requestDisallowInterceptTouchEvent(false)
                if (_rodIsScrolling) {
                    _sliderListener?.onScrollEnd(_rod.currentPercent, _rod.currentVal, _rod)
                }
                _rodIsScrolling = false
            }
            MotionEvent.ACTION_DOWN -> {
                _rodIsScrolling = if (!UtilKGesture.isTapInArea(
                        event,
                        _slider.leftX - if (!_rod.isInsideSlider) _rod.radius else _slider.heightHalf,
                        _slider.topY - AttrsParser.DEFAULT_PADDING_VERTICAL,
                        _slider.rightX + if (!_rod.isInsideSlider) _rod.radius else _slider.heightHalf,
                        _slider.bottomY + AttrsParser.DEFAULT_PADDING_VERTICAL
                    )
                ) {
                    return true
                } else {
                    _sliderListener?.onScrollStart()
                    true
                }
                if (_rodIsScrolling) {
                    _rod.currentPercent = event.x / _rod.intervalX
                    _layoutKSlider.postInvalidate()
                }
                _scrollableParentView?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (_rodIsScrolling) {
                    _rod.currentPercent = event.x / _rod.intervalX
                    _layoutKSlider.postInvalidate()
                }
            }
        }
        return true
    }

    override fun updateRodPercent(percent: Float) {
        _rod.currentPercent = percent
        refreshView()
    }
}
