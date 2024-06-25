package com.mozhimen.basick.utilk.android.transition

import android.transition.Slide
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKSlide
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/6/25
 * @Version 1.0
 */
object UtilKSlide {
    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun get(slideEdge: Int, duration: Long): Slide =
        Slide().apply {
            setSlideEdge(slideEdge)
            setDuration(duration)
        }
}