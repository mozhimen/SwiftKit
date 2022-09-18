package com.mozhimen.uicorek.layoutk.slider.helpers

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.basick.extsk.sp2px
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.uicorek.R
import com.mozhimen.uicorek.layoutk.slider.mos.LayoutKSliderAttrs

/**
 * @ClassName LayoutKSliderParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/17 14:19
 * @Version 1.0
 */
internal object LayoutKSliderParser {


    //attrs
    /*var _layoutHeight: Float = LAYOUT_HEIGHT

    var _sliderHeight: Float = SLIDER_HEIGHT
    var _sliderPaddingHorizontal: Float = SLIDER_PADDING_HORIZONTAL
    var _sliderRegionLeftColor = UtilKRes.getColor(R.color.blue_normal)
    var _sliderRegionRightColor = UtilKRes.getColor(R.color.blue_light)

    var _sliderRegionLabelIsShow = false
    var _sliderRegionLabelColorIsFollowRegion = false
    var _sliderRegionLabelColor = UtilKRes.getColor(R.color.blue_normal)

    var _rodColor = _sliderRegionLeftColor
    var _rodIsInside = false
    var _rodRadius: Float = ROD_RADIUS

    var _labelTopIsShow = true
    var _labelBottomIsShow = true
    val _labelTextColor = UtilKRes.getColor(R.color.blue_normal)
    var _labelTopTextSize: Float = LABEL_TOP_TEXT_SIZE
    var _labelBottomTextSize: Float = LABEL_BOTTOM_TEXT_SIZE
    var _labelSliderDistance: Float = LABEL_SLIDER_DISTANCE

    var _sectionCrossLineIsShow = true
    val _sectionCrossLineColor = _sliderRegionLeftColor
    val _sectionCrossLineWidth: Float = SECTION_CROSS_LINE_WIDTH
    var _sectionColorAfterLast = false
    var _sectionColorBeforeRod = true

    var _bubbleIsShow = true
    val _bubbleColor = Color.WHITE
    val _bubbleRadius: Float = BUBBLE_RADIUS
    val _bubbleTextColor = UtilKRes.getColor(R.color.blue_normal)
    val _bubbleTextSize: Float = BUBBLE_TEXT_SIZE
    val _bubbleArrowWidth: Float = BUBBLE_ARROW_WIDTH
    val _bubbleArrowHeight: Float = BUBBLE_ARROW_HEIGHT
    val _bubblePaddingHorizontal: Float = BUBBLE_PADDING_HORIZONTAL
    val _bubblePaddingVertical: Float = BUBBLE_PADDING_VERTICAL
    val _bubbleColorEditing = UtilKRes.getColor(R.color.blue_light)
    var _bubbleEnableEditOnClick = true*/

    //const
    val SLIDER_WIDTH = 100f.dp2px().toFloat()
    val SLIDER_HEIGHT = 10f.dp2px().toFloat()
    val SLIDER_HEIGHT_INSIDE = 16f.dp2px().toFloat()
    val SLIDER_ROD_LEFT_COLOR = UtilKRes.getColor(R.color.blue_normal)
    val SLIDER_ROD_RIGHT_COLOR = UtilKRes.getColor(R.color.gray_light)
    val ROD_COLOR = Color.WHITE
    val ROD_COLOR_INSIDE = UtilKRes.getColor(R.color.blue_dark)
    val ROD_IS_INSIDE = false
    val ROD_MIN_VAL = 0f
    val ROD_MAX_VAL = 0f
    val SLIDER_PADDING_HORIZONTAL = 0f.dp2px().toFloat()
    val LABEL_SLIDER_DISTANCE = 8f.dp2px().toFloat()
    val LABEL_TOP_TEXT_SIZE = 12f.sp2px().toFloat()
    val LABEL_BOTTOM_TEXT_SIZE = 12f.sp2px().toFloat()
    val SECTION_CROSS_LINE_WIDTH = 2f.dp2px().toFloat()
    val BUBBLE_PADDING_HORIZONTAL = 8f.dp2px().toFloat()
    val BUBBLE_PADDING_VERTICAL = 10f.dp2px().toFloat()
    val BUBBLE_ARROW_WIDTH = 10f.dp2px().toFloat()
    val BUBBLE_ARROW_HEIGHT = 10f.dp2px().toFloat()
    val BUBBLE_TEXT_SIZE = 13f.sp2px().toFloat()
    val BUBBLE_RADIUS = 4f.dp2px().toFloat()

    fun parseAttrs(context: Context, attrs: AttributeSet?): LayoutKSliderAttrs {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LayoutKSlider)

        val rodIsInside: Boolean =
            typedArray.getBoolean(R.styleable.LayoutKSlider_layoutKSlider_rodIsInside, ROD_IS_INSIDE)
        val sliderHeight: Float =
            typedArray.getDimension(R.styleable.LayoutKSlider_layoutKSlider_sliderHeight, if (!rodIsInside) SLIDER_HEIGHT else SLIDER_HEIGHT_INSIDE)
        val sliderRodLeftColor: Int =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_sliderRodLeftColor, SLIDER_ROD_LEFT_COLOR)
        val sliderRodRightColor: Int =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_sliderRodRightColor, SLIDER_ROD_RIGHT_COLOR)
        val rodColor: Int =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_rodColor, ROD_COLOR)
        val rodColorInside: Int =
            typedArray.getColor(R.styleable.LayoutKSlider_layoutKSlider_rodColorInside, ROD_COLOR_INSIDE)
        val rodRadius: Float =
            typedArray.getDimension(R.styleable.LayoutKSlider_layoutKSlider_rodRadius, if (!rodIsInside) sliderHeight / 2f * 2f else sliderHeight / 2f * 0.7f)
        val rodRadiusInside: Float =
            typedArray.getDimension(R.styleable.LayoutKSlider_layoutKSlider_rodRadiusInside, if (!rodIsInside) rodRadius * 0.7f else rodRadius * 0.5f)
        val rodMinVal =
            typedArray.getFloat(R.styleable.LayoutKSlider_layoutKSlider_rodMinVal, ROD_MIN_VAL)
        val rodMaxVal =
            typedArray.getFloat(R.styleable.LayoutKSlider_layoutKSlider_rodMaxVal, ROD_MAX_VAL)

        typedArray.recycle()

        return LayoutKSliderAttrs(
            sliderHeight,
            sliderRodLeftColor,
            sliderRodRightColor,
            rodColor,
            rodColorInside,
            rodIsInside,
            rodRadius,
            rodRadiusInside,
            rodMinVal,
            rodMaxVal
        )
    }
}