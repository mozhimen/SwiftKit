package com.mozhimen.uicorek.layoutk.slider

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import com.mozhimen.basick.basek.commons.IBaseKLayout
import com.mozhimen.uicorek.R

/**
 * @ClassName LayoutKSliderProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 14:33
 * @Version 1.0
 */
class LayoutKSliderProxy(private val _context: Context) {

    /* //region # variate
     private var _colorBackground: Int
     private var _step_colorizeAfterLast: Boolean
     private var _step_drawLines: Boolean
     private var _step_colorizeOnlyBeforeIndicator: Boolean
     private var _drawTextOnTop: Boolean
     private var _textTopSize: Int
     private var _drawTextOnBottom: Boolean
     private var _textBottomSize: Int
     private var _barHeight: Float
     private var _drawBubble: Boolean
     private var _modeRegion: Boolean
     private var _regionColorLeft: Int
     private var _regionColorRight: Int
     private var _indicatorInside: Boolean
     private var _regions_textFollowRegionColor: Boolean
     private var _regions_centerText: Boolean
     private var _editOnBubbleClick: Boolean

     private lateinit var _paintBubble: Paint
     private lateinit var _paintBubbleTextCurrent: TextPaint
     private lateinit var _paintTextBottom: TextPaint
     private lateinit var _paintTextTop: TextPaint
     private lateinit var _paintStep: Paint
     private lateinit var _paintBar: Paint
     private lateinit var _paintIndicator: Paint
     //endregion

     fun init(attrs: AttributeSet?) {
         initAttrs(attrs, 0)
         initPaint()
         initView()
     }

     override fun initFlag() {

     }

     override fun initAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
         attrs ?: return

         val typedArray: TypedArray = _context.obtainStyledAttributes(attrs, R.styleable.Slidr)
         _colorBackground =
             typedArray.getColor(R.styleable.Slidr_slidr_backgroundColor, colorBackground)
         _step_colorizeAfterLast =
             typedArray.getBoolean(R.styleable.Slidr_slidr_step_colorizeAfterLast, step_colorizeAfterLast)
         _step_drawLines =
             typedArray.getBoolean(R.styleable.Slidr_slidr_step_drawLine, step_drawLines)
         _step_colorizeOnlyBeforeIndicator =
             typedArray.getBoolean(R.styleable.Slidr_slidr_step_colorizeOnlyBeforeIndicator, step_colorizeOnlyBeforeIndicator)
         _drawTextOnTop =
             typedArray.getBoolean(R.styleable.Slidr_slidr_textTop_visible, drawTextOnTop)
         _textTopSize =
             typedArray.getDimensionPixelSize(R.styleable.Slidr_slidr_textTop_size, dpToPx(textTopSize) as Int)
         _drawTextOnBottom =
             typedArray.getBoolean(R.styleable.Slidr_slidr_textBottom_visible, drawTextOnBottom)
         _textBottomSize =
             typedArray.getDimensionPixelSize(R.styleable.Slidr_slidr_textBottom_size, dpToPx(textBottomSize) as Int)
         _barHeight =
             typedArray.getDimensionPixelOffset(R.styleable.Slidr_slidr_barHeight, dpToPx(barHeight) as Int).toFloat()
         _drawBubble =
             typedArray.getBoolean(R.styleable.Slidr_slidr_draw_bubble, drawBubble)
         _modeRegion =
             typedArray.getBoolean(R.styleable.Slidr_slidr_regions, modeRegion)
         _regionColorLeft =
             typedArray.getColor(R.styleable.Slidr_slidr_region_leftColor, regionColorLeft)
         _regionColorRight =
             typedArray.getColor(R.styleable.Slidr_slidr_region_rightColor, regionColorRight)
         _indicatorInside =
             typedArray.getBoolean(R.styleable.Slidr_slidr_indicator_inside, indicatorInside)
         _regions_textFollowRegionColor =
             typedArray.getBoolean(R.styleable.Slidr_slidr_regions_textFollowRegionColor, regions_textFollowRegionColor)
         _regions_centerText =
             typedArray.getBoolean(R.styleable.Slidr_slidr_regions_centerText, regions_centerText)
         _editOnBubbleClick =
             typedArray.getBoolean(R.styleable.Slidr_slidr_edditable, editOnBubbleClick)
         typedArray.recycle()
     }

     override fun initView() {
         //setColorBackground(colorBackground)
         //setTextTopSize(textTopSize)
         //setTextBottomSize(textBottomSize)
     }

     private fun initPaint() {
         _paintIndicator = Paint()
         _paintIndicator.isAntiAlias = true
         _paintIndicator.strokeWidth = 2f

         _paintBar = Paint()
         _paintBar.isAntiAlias = true
         _paintBar.strokeWidth = 2f
         _paintBar.color = _colorBackground

         _paintStep = Paint()
         _paintStep.isAntiAlias = true
         _paintStep.strokeWidth = 5f
         _paintStep.color = _colorStoppover

         _paintBubble = Paint()
         _paintBubble.isAntiAlias = true
         _paintBubble.strokeWidth = 3f

         _paintTextTop = TextPaint()
         _paintTextTop.isAntiAlias = true
         _paintTextTop.style = Paint.Style.FILL
         _paintTextTop.color = _textColor
         _paintTextTop.textSize = _textTopSize.toFloat()

         _paintTextBottom = TextPaint()
         _paintTextBottom.isAntiAlias = true
         _paintTextBottom.style = Paint.Style.FILL
         _paintTextBottom.color = _textColor
         _paintTextBottom.textSize = _textBottomSize.toFloat()

         _paintBubbleTextCurrent = TextPaint()
         _paintBubbleTextCurrent.isAntiAlias = true
         _paintBubbleTextCurrent.style = Paint.Style.FILL
         _paintBubbleTextCurrent.color = Color.WHITE
         _paintBubbleTextCurrent.strokeWidth = 2f
         _paintBubbleTextCurrent.textSize = dpToPx(_textSizeBubbleCurrent)
     }

     fun update() {
         if (barWidth > 0f) {
             val currentPercent: Float = indicatorX / barWidth
             currentValue = currentPercent * (max - min) + min
             currentValue = Math.round(currentValue).toFloat()
             if (listener != null && oldValue != currentValue) {
                 oldValue = currentValue
                 listener.valueChanged(this@Slidr, currentValue)
             } else {
             }
             updateBubbleWidth()
             editBubbleEditPosition()
         }
         postInvalidate()
     }*/
}