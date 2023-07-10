package com.mozhimen.basick.elemk.android.content.commons


/**
 * @ClassName ITimeReceiverListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:30
 * @Version 1.0
 */
interface ITimeReceiverListener {
    fun onTimeZoneChanged() {}
    fun onTimeTick() {}
    fun onTimeChanged() {}
}