package com.mozhimen.uicorek.layoutk.slider.mos

import android.view.MotionEvent
import com.mozhimen.basick.extsk.dp2px
import com.mozhimen.uicorek.layoutk.slider.Slidr

/**
 * @ClassName Bubble
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/8 17:58
 * @Version 1.0
 */
class Bubble {
    private val height = 0f
    private val width = 0f
    private val x = 0f
    private val y = 0f
    fun clicked(e: MotionEvent): Boolean {
        return e.x >= x && e.x <= x + width && e.y >= y && e.y < y + height
    }

    fun getHeight(): Float {
        return height - Slidr.BUBBLE_ARROW_HEIGHT.dp2px()
    }

    fun getX(): Float {
        return x.coerceAtLeast(0f)
    }

    fun getY(): Float {
        return y.coerceAtLeast(0f)
    }
}