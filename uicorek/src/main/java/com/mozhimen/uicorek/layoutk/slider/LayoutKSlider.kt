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

    fun getCurrentVal(): Float =
        _layoutKSliderProxy.getCurrentVal()

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
        //_layoutKSliderProxy.updateValues()
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(_layoutKSliderProxy.getHeightMeasureSpec(), MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initView()
        //_layoutKSliderProxy.updateValues()
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        _layoutKSliderProxy.onDraw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return _layoutKSliderProxy.onTouchEvent(event)
    }

//    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
//        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
//            dispatchKeyEvent(event)
//            //_layoutKSliderProxy.closeEditText()
//            return false
//        }
//        return super.onKeyPreIme(keyCode, event)
//    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        _layoutKSliderProxy.attachScrollableParentView(UtilKView.getParentViewMatch(this, ScrollView::class.java, NestedScrollView::class.java, RecyclerView::class.java) as ViewGroup?)
    }

    //region # public func
    /*

     fun setBubbleListener(bubbleListener: IBubbleListener) {
         _layoutKSliderProxy.setBubbleListener(bubbleListener)
     }

     fun setEditListener(editListener: IEditListener) {
         _layoutKSliderProxy.setEditListener(editListener)
     }

     fun setTextLabelFormatter(textLabelFormatter: ILabelTextFormatter) {
         _layoutKSliderProxy.setTextLabelFormatter(textLabelFormatter)
     }

     fun setTextRodFormatter(textRodFormatter: ITextRodFormatter) {
         _layoutKSliderProxy.setTextRodFormatter(textRodFormatter)
     }

     fun addSection(sections: List<Section>) {
         _layoutKSliderProxy.addSection(sections)
     }

     fun addSection(section: Section) {
         _layoutKSliderProxy.addSection(section)
     }

     fun clearSections() {
         _layoutKSliderProxy.clearSections()
     }

     fun getRodMaxVal(): Float {
         return _layoutKSliderProxy.getRodMaxVal()
     }

     fun setRodMaxVal(rodMaxVal: Float) {
         _layoutKSliderProxy.setRodMaxVal(rodMaxVal)
     }

     fun setRodMinVal(rodMinVal: Float) {
         _layoutKSliderProxy.setRodMinVal(rodMinVal)
     }

     fun getRodCurrentVal(): Float {
         return _layoutKSliderProxy.getRodCurrentVal()
     }

     fun setRodCurrentVal(rodCurrentVal: Float) {
         _layoutKSliderProxy.setRodCurrentVal(rodCurrentVal)
     }

     fun setLabelTextMax(labelTextMax: String) {
         _layoutKSliderProxy.setLabelTextMax(labelTextMax)
     }

     fun setLabelTextMin(labelTextMin: String) {
         _layoutKSliderProxy.setLabelTextMin(labelTextMin)
     }*/
    //endregion
}