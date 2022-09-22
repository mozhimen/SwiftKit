package com.mozhimen.basick.fpsk.commons

/**
 * @ClassName IFpsKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/22 16:03
 * @Version 1.0
 */
interface IFpsKListener {
    fun onFrame(fps: Double)
}