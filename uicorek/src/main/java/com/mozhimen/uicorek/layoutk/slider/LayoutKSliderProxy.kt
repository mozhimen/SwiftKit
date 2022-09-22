package com.mozhimen.uicorek.layoutk.slider

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import com.mozhimen.basick.basek.BaseViewCallback
import com.mozhimen.basick.utilk.UtilKGesture
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderListener
import com.mozhimen.uicorek.layoutk.slider.helpers.LayoutKSliderParser
import com.mozhimen.uicorek.layoutk.slider.mos.LayoutKSliderAttrs
import com.mozhimen.uicorek.layoutk.slider.mos.Rod
import com.mozhimen.uicorek.layoutk.slider.mos.Slider

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
class LayoutKSliderProxy(
    private val _context: Context,
) :
    BaseViewCallback() {

    //region # variate
    private lateinit var _layoutKSlider: LayoutKSlider
    private lateinit var _attrs: LayoutKSliderAttrs
    private var _slider: Slider = Slider()
    private var _rod: Rod = Rod()
    private var _scrollableParentView: ViewGroup? = null
    private var _rodIsScrolling = false

    private lateinit var _paintRightSlider: Paint
    private lateinit var _paintLeftSlider: Paint
    private lateinit var _paintRod: Paint

    private var _sliderListener: ISliderListener? = null
    //endregion

    //region # public func
    fun init(layoutKSlider: LayoutKSlider) {
        _layoutKSlider = layoutKSlider
    }

    fun getRod(): Rod {
        return _rod
    }

    fun getSlider(): Slider {
        return _slider
    }

    fun setSliderListener(sliderListener: ISliderListener) {
        _sliderListener = sliderListener
    }

    fun getHeightMeasureSpec(): Int {
        var height = 0
        height += if (_attrs.rodIsInside) _attrs.sliderHeight.toInt() else _attrs.rodRadius.toInt() * 2
        height += LayoutKSliderParser.DEFAULT_PADDING_VERTICAL * 2
        return height
    }

    fun attachScrollableParentView(scrollableParentView: ViewGroup?) {
        _scrollableParentView = scrollableParentView
    }
    //endregion

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs ?: return
        _attrs = LayoutKSliderParser.parseAttrs(_context, attrs)
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
        initSlider()
        initRod()
        _layoutKSlider.invalidate()
    }

    private fun initRod() {
        val distance = if (!_attrs.rodIsInside) 0f else _slider.heightHalf - _attrs.rodRadius
        _rod = Rod(
            _layoutKSlider.paddingStart.toFloat() + _attrs.rodRadius + distance,
            _layoutKSlider.width - _layoutKSlider.paddingEnd.toFloat() - _attrs.rodRadius - distance,
            _slider.centerY,
            _attrs.rodIsInside,
            _attrs.rodRadius,
            _attrs.rodRadiusInside,
            _sliderListener
        )
    }

    private fun initSlider() {
        _slider = Slider(
            _layoutKSlider.width - _layoutKSlider.paddingStart.toFloat() - _layoutKSlider.paddingEnd - _attrs.sliderHeight,
            _attrs.sliderHeight,
            _layoutKSlider.paddingStart.toFloat() + _attrs.sliderHeight / 2f,
            (if (_attrs.rodIsInside) 0f else _attrs.rodRadius - (_attrs.sliderHeight / 2f)) + LayoutKSliderParser.DEFAULT_PADDING_VERTICAL
        )
        _slider.rodLeftColor = _attrs.sliderRodLeftColor
        _slider.rodRightColor = _attrs.sliderRodRightColor
        //set paint shader
        if (_attrs.sliderRodLeftColor != _attrs.sliderRodLeftGradientColor) {
            _paintLeftSlider.shader = LinearGradient(
                _slider.leftX,
                _slider.topY,
                _slider.rightX,
                _slider.bottomY,
                _attrs.sliderRodLeftColor, _attrs.sliderRodLeftGradientColor,
                Shader.TileMode.CLAMP
            )
        }
    }

    fun onDraw(canvas: Canvas) {
        canvas.save()

        //slider
        drawSlider(canvas)

        //rod
        drawRod(canvas)

        canvas.restore()
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
        val color = _attrs.rodColor
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radius, _paintRod)//外圆
        _paintRod.color = _attrs.rodColorInside
        canvas.drawCircle(_rod.currentX, _rod.centerY, _rod.radiusInside, _paintRod)//内圆
        _paintRod.color = color
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                _scrollableParentView?.requestDisallowInterceptTouchEvent(false)
                _sliderListener?.onScrollEnd(_rod.currentVal)
                _rodIsScrolling = false
            }
            MotionEvent.ACTION_DOWN -> {
                _rodIsScrolling = if (!UtilKGesture.isTapInArea(
                        event,
                        _slider.leftX,
                        _slider.rightX,
                        _slider.topY - LayoutKSliderParser.DEFAULT_PADDING_VERTICAL,
                        _slider.bottomY + LayoutKSliderParser.DEFAULT_PADDING_VERTICAL
                    )
                ) {
                    return true
                } else {
                    _sliderListener?.onScrollStart()
                    true
                }
                _scrollableParentView?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                if (_rodIsScrolling) {
                    _rod.currentX = event.x
                    _layoutKSlider.postInvalidate()
                }
            }
        }
        return true
    }
}