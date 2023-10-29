package com.mozhimen.basick.utilk.android.os

import android.content.Context
import android.os.Vibrator
import com.mozhimen.basick.utilk.android.content.UtilKContext


/**
 * @ClassName UtilKVibrator
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 15:14
 * @Version 1.0
 */
object UtilKVibrator {
    @JvmStatic
    fun get(context: Context): Vibrator =
        UtilKContext.getVibrator(context)

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasVibrator(context: Context): Boolean =
        get(context).hasVibrator()

    ////////////////////////////////////////////////////////////////

    @JvmStatic
    fun vibrate(context: Context, milliseconds: Long) {
        if (hasVibrator(context)) get(context).vibrate(milliseconds)
    }

    /**
     * 让手机以我们自己设定的pattern[]模式振动
     * long pattern[] = {1000, 20000, 10000, 10000, 30000};
     */
    fun vibrate(context: Context, pattern: LongArray, repeat: Int) {
        if (hasVibrator(context)) get(context).vibrate(pattern, repeat)
    }

    fun cancel(context: Context) {
        if (hasVibrator(context))
            get(context).cancel()
    }
}