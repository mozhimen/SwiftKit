package com.mozhimen.uicorek.layoutk.slider

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.*
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.basek.BaseKLayoutFrame
import com.mozhimen.basick.utilk.UtilKView
import com.mozhimen.uicorek.layoutk.slider.commons.ISliderListener
import com.mozhimen.uicorek.layoutk.slider.mos.MRod
import com.mozhimen.uicorek.layoutk.slider.mos.MSlider

/**
 * @ClassName LayoutKSlider
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
 * @Date 2022/9/8 14:14
 * @Version 1.0
 */

class LayoutKSlider @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseKLayoutFrame(context, attrs, defStyleAttr, defStyleRes) {

    private var _layoutKSliderProxy: LayoutKSliderProxy = LayoutKSliderProxy(context)

    init {
        setWillNotDraw(false)
        _layoutKSliderProxy.init(this)
        initAttrs(attrs, defStyleAttr)
        initPaint()
        minimumHeight = _layoutKSliderProxy.getHeightMeasureSpec()
    }

    val rod: MRod
        get() = _layoutKSliderProxy.getRod()

    val slider:MSlider
    get() = _layoutKSliderProxy.getSlider()

    fun setSliderListener(sliderListener: ISliderListener) {
        _layoutKSliderProxy.setSliderListener(sliderListener)
    }

    override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        _layoutKSliderProxy.initAttrs(attrs, defStyleAttr)
    }

    private fun initPaint() {
        _layoutKSliderProxy.initPaint()
    }

    override fun initView() {
        _layoutKSliderProxy.initView()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(_layoutKSliderProxy.getHeightMeasureSpec(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initView()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        _layoutKSliderProxy.onDraw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return _layoutKSliderProxy.onTouchEvent(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        _layoutKSliderProxy.attachScrollableParentView(UtilKView.getParentViewMatch(this, ScrollView::class.java, NestedScrollView::class.java, RecyclerView::class.java) as ViewGroup?)
    }
}