package com.mozhimen.uicorek.layoutk.slider.mos

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.slider.Slidr

/**
 * @ClassName Settings
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 18:00
 * @Version 1.0
 */
class Settings(slidr: Slidr) {
    private val slidr: Slidr
    private val paintBar: Paint
    fun getPaintBar() = paintBar
    private val paintIndicator: Paint
    fun getPaintIndicator(): Paint = paintIndicator
    private val paintStep: Paint
    fun getPaintStep() = paintStep
    private val paintTextTop: TextPaint
    fun getPaintTextTop() = paintTextTop
    private val paintTextBottom: TextPaint
    fun getPaintTextBottom() = paintTextBottom
    private val paintBubbleTextCurrent: TextPaint
    fun getPaintBubbleTextCurrent(): TextPaint = paintBubbleTextCurrent
    private val paintBubble: Paint
    fun getPaintBubble(): Paint = paintBubble
    private var colorBackground = Color.parseColor("#cccccc")
    fun getColorBackground() = colorBackground
    private val colorStoppover = Color.BLACK
    private val textColor = Color.parseColor("#6E6E6E")
    private var textTopSize = 12
    private var textBottomSize = 12
    private val textSizeBubbleCurrent = 16
    fun getTextSizeBubbleCurrent(): Int = textSizeBubbleCurrent
    private var barHeight = 15f
    fun getBarHeight(): Float = barHeight
    private var paddingCorners = 0f
    fun setPaddingCorners(paddingCorners: Float) {
        this.paddingCorners = paddingCorners
    }

    fun getPaddingCorners(): Float = paddingCorners
    private var step_colorizeAfterLast = false
    fun getStep_colorizeAfterLast() = step_colorizeAfterLast
    private var step_drawLines = true
    fun getStep_drawLines() = step_drawLines
    private var step_colorizeOnlyBeforeIndicator = true
    fun getStep_colorizeOnlyBeforeIndicator() = step_colorizeOnlyBeforeIndicator
    private var drawTextOnTop = true
    fun getDrawTextOnTop() = drawTextOnTop
    private var drawTextOnBottom = true
    fun getDrawTextOnBottom() = drawTextOnBottom
    private var drawBubble = true
    fun getDrawBubble(): Boolean = drawBubble
    private var modeRegion = false
    fun getModeRegion(): Boolean = modeRegion
    private var indicatorInside = false
    fun getIndicatorInside() = indicatorInside
    private var regions_textFollowRegionColor = false
    fun getRegions_textFollowRegionColor() = regions_textFollowRegionColor
    private var regions_centerText = true
    fun getRegions_centerText() = regions_centerText
    private var regionColorLeft = Color.parseColor("#007E90")
    fun getRegionColorLeft() = regionColorLeft
    private var regionColorRight = Color.parseColor("#ed5564")
    fun getRegionColorRight() = regionColorRight
    private var editOnBubbleClick = true
    fun getEditOnBubbleClick(): Boolean = editOnBubbleClick
    private val bubbleColorEditing = Color.WHITE
    fun getBubbleColorEditing() = bubbleColorEditing

    fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.LayoutKSlider)
            setColorBackground(a.getColor(R.styleable.LayoutKSlider_layoutKSlider_backgroundColor, colorBackground))
            step_colorizeAfterLast = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_colorizeAfterLast, step_colorizeAfterLast)
            step_drawLines = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_drawLine, step_drawLines)
            step_colorizeOnlyBeforeIndicator =
                a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_step_colorizeOnlyBeforeIndicator, step_colorizeOnlyBeforeIndicator)
            drawTextOnTop = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_textTop_visible, drawTextOnTop)
            setTextTopSize(a.getDimensionPixelSize(R.styleable.LayoutKSlider_layoutKSlider_textTop_size, dpToPx(textTopSize).toInt()))
            drawTextOnBottom = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_textBottom_visible, drawTextOnBottom)
            setTextBottomSize(a.getDimensionPixelSize(R.styleable.LayoutKSlider_layoutKSlider_textBottom_size, dpToPx(textBottomSize).toInt()))
            barHeight = a.getDimensionPixelOffset(R.styleable.LayoutKSlider_layoutKSlider_height, dpToPx(barHeight).toInt()).toFloat()
            drawBubble = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_draw_bubble, drawBubble)
            modeRegion = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions, modeRegion)
            regionColorLeft = a.getColor(R.styleable.LayoutKSlider_layoutKSlider_region_leftColor, regionColorLeft)
            regionColorRight = a.getColor(R.styleable.LayoutKSlider_layoutKSlider_region_rightColor, regionColorRight)
            indicatorInside = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_indicator_inside, indicatorInside)
            regions_textFollowRegionColor =
                a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions_textFollowRegionColor, regions_textFollowRegionColor)
            regions_centerText = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_regions_centerText, regions_centerText)
            editOnBubbleClick = a.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_edditable, editOnBubbleClick)
            a.recycle()
        }
    }

    fun setStep_colorizeAfterLast(step_colorizeAfterLast: Boolean) {
        this.step_colorizeAfterLast = step_colorizeAfterLast
        slidr.update()
    }

    fun setDrawTextOnTop(drawTextOnTop: Boolean) {
        this.drawTextOnTop = drawTextOnTop
        slidr.update()
    }

    fun setDrawTextOnBottom(drawTextOnBottom: Boolean) {
        this.drawTextOnBottom = drawTextOnBottom
        slidr.update()
    }

    fun setDrawBubble(drawBubble: Boolean) {
        this.drawBubble = drawBubble
        slidr.update()
    }

    fun setModeRegion(modeRegion: Boolean) {
        this.modeRegion = modeRegion
        slidr.update()
    }

    fun setRegionColorLeft(regionColorLeft: Int) {
        this.regionColorLeft = regionColorLeft
        slidr.update()
    }

    fun setRegionColorRight(regionColorRight: Int) {
        this.regionColorRight = regionColorRight
        slidr.update()
    }

    fun setColorBackground(colorBackground: Int) {
        this.colorBackground = colorBackground
        slidr.update()
    }

    fun setTextTopSize(textSize: Int) {
        textTopSize = textSize
        paintTextTop.textSize = textSize.toFloat()
        slidr.update()
    }

    fun setTextBottomSize(textSize: Int) {
        textBottomSize = textSize
        paintTextBottom.textSize = textSize.toFloat()
        slidr.update()
    }

    private fun dpToPx(size: Int): Float {
        return size * slidr.resources.displayMetrics.density
    }

    private fun dpToPx(size: Float): Float {
        return size * slidr.resources.displayMetrics.density
    }

    init {
        this.slidr = slidr

        paintIndicator = Paint()
        paintIndicator.isAntiAlias = true
        paintIndicator.strokeWidth = 2f

        paintBar = Paint()
        paintBar.isAntiAlias = true
        paintBar.strokeWidth = 2f
        paintBar.color = colorBackground

        paintStep = Paint()
        paintStep.isAntiAlias = true
        paintStep.strokeWidth = 5f
        paintStep.color = colorStoppover

        paintTextTop = TextPaint()
        paintTextTop.isAntiAlias = true
        paintTextTop.style = Paint.Style.FILL
        paintTextTop.color = textColor
        paintTextTop.textSize = textTopSize.toFloat()

        paintTextBottom = TextPaint()
        paintTextBottom.isAntiAlias = true
        paintTextBottom.style = Paint.Style.FILL
        paintTextBottom.color = textColor
        paintTextBottom.textSize = textBottomSize.toFloat()

        paintBubbleTextCurrent = TextPaint()
        paintBubbleTextCurrent.isAntiAlias = true
        paintBubbleTextCurrent.style = Paint.Style.FILL
        paintBubbleTextCurrent.color = Color.WHITE
        paintBubbleTextCurrent.strokeWidth = 2f
        paintBubbleTextCurrent.textSize = dpToPx(textSizeBubbleCurrent)

        paintBubble = Paint()
        paintBubble.isAntiAlias = true
        paintBubble.strokeWidth = 3f
    }
}
