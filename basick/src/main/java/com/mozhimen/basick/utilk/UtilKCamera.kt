package com.mozhimen.basick.utilk

import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.roundToInt

/**
 * @ClassName UtilKCamera
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/7 19:09
 * @Version 1.0
 */
object UtilKCamera {
    /**
     * 计算对焦和测光区域
     *
     * @param coefficient        比率
     * @param originFocusCenterX 对焦中心点X
     * @param originFocusCenterY 对焦中心点Y
     * @param originFocusWidth   对焦宽度
     * @param originFocusHeight  对焦高度
     * @param previewViewWidth   预览宽度
     * @param previewViewHeight  预览高度
     */
    fun focusMeteringArea(coefficient: Float,
                          originFocusCenterX: Float, originFocusCenterY: Float,
                          originFocusWidth: Int, originFocusHeight: Int,
                          previewViewWidth: Int, previewViewHeight: Int): Rect {
        val halfFocusAreaWidth = (originFocusWidth * coefficient / 2).toInt()
        val halfFocusAreaHeight = (originFocusHeight * coefficient / 2).toInt()
        val centerX = (originFocusCenterX / previewViewWidth * 2000 - 1000).toInt()
        val centerY = (originFocusCenterY / previewViewHeight * 2000 - 1000).toInt()
        val rectF = RectF(clamp(centerX - halfFocusAreaWidth, -1000, 1000).toFloat(),
            clamp(centerY - halfFocusAreaHeight, -1000, 1000).toFloat(),
            clamp(centerX + halfFocusAreaWidth, -1000, 1000).toFloat(),
            clamp(centerY + halfFocusAreaHeight, -1000, 1000).toFloat())
        return Rect(
            rectF.left.roundToInt(), rectF.top.roundToInt(),
            rectF.right.roundToInt(), rectF.bottom.roundToInt()
        )
    }

    private fun clamp(value: Int, min: Int, max: Int): Int {
        return value.coerceAtLeast(min).coerceAtMost(max)
    }
}