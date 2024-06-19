package com.mozhimen.basick.utilk.android.app

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mozhimen.basick.elemk.android.app.cons.CPendingIntent
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKPendingIntent
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/2 21:48
 * @Version 1.0
 */
object UtilKPendingIntentWrapper : BaseUtilK() {
    @JvmStatic
    fun get_ofActivity_IMMUTABLE(requestCode: Int, intent: Intent): PendingIntent =
        UtilKPendingIntent.getActivity(_context, requestCode, intent, CPendingIntent.FLAG_IMMUTABLE)

    @JvmStatic
    fun get_ofActivity_UPDATE_CURRENT(requestCode: Int, intent: Intent): PendingIntent =
        UtilKPendingIntent.getActivity(_context, requestCode, intent, getFlag_UPDATE_CURRENT())

    @JvmStatic
    fun get_ofBroadCast_UPDATE_CURRENT(requestCode: Int, intent: Intent): PendingIntent =
        UtilKPendingIntent.getBroadcast(_context, requestCode, intent, getFlag_UPDATE_CURRENT())

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlag_UPDATE_CURRENT(): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            CPendingIntent.FLAG_UPDATE_CURRENT or CPendingIntent.FLAG_IMMUTABLE
        } else CPendingIntent.FLAG_UPDATE_CURRENT
}