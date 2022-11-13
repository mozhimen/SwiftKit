package com.mozhimen.uicorek.drawablek.arrow.mos

import android.graphics.Color
import android.graphics.RectF
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowDirection
import com.mozhimen.uicorek.drawablek.arrow.cons.EArrowPosPolicy

/**
 * @ClassName MShape
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/13 22:39
 * @Version 1.0
 */
data class MShape(
    var rect: RectF = RectF(),
    var borderWidth: Float = 0f,
    var borderColor: Int = Color.WHITE,

    var fillColor: Int = Color.WHITE,

    var gapWidth: Float = 0f,

    var arrowDirection: EArrowDirection = EArrowDirection.None,
    var arrowPosPolicy: EArrowPosPolicy = EArrowPosPolicy.TargetCenter,
    var arrowPosDelta: Float = 0f,
    var arrowWidth: Float = 0f,
    var arrowHeight: Float = 0f,
    var arrowToX: Float = 0f,
    var arrowToY: Float = 0f,
    var arrowPeakX: Float = 0f,
    var arrowPeakY: Float = 0f,

    var cornerTopLeftRadius: Float = 0f,
    var cornerTopRightRadius: Float = 0f,
    var cornerBottomLeftRadius: Float = 0f,
    var cornerBottomRightRadius: Float = 0f
)