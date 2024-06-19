package com.mozhimen.basick.utilk.android.app

import android.app.PendingIntent
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
    fun get_ofActivity(requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        UtilKPendingIntent.getActivity(_context, requestCode, intent, flags)

    @JvmStatic
    fun get_ofBroadCast(requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        UtilKPendingIntent.getBroadcast(_context, requestCode, intent, flags)

    /////////////////////////////////////////////////////////////////

    @Deprecated("""
        Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.
        Strongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE if some functionality depends on the PendingIntent being mutable, e.g. if it needs to be used with inline replies or bubbles.
    """)
    @JvmStatic
    fun get_ofActivity_NONE(requestCode: Int, intent: Intent): PendingIntent =
        get_ofActivity(requestCode, intent, CPendingIntent.FLAG_NONE)

    @JvmStatic
    fun get_ofActivity_IMMUTABLE(requestCode: Int, intent: Intent): PendingIntent =
        get_ofActivity(requestCode, intent, CPendingIntent.FLAG_IMMUTABLE)

    @JvmStatic
    fun get_ofActivity_UPDATE_CURRENT(requestCode: Int, intent: Intent): PendingIntent =
        get_ofActivity(requestCode, intent, getFlag_UPDATE_CURRENT())

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get_ofBroadCast_UPDATE_CURRENT(requestCode: Int, intent: Intent): PendingIntent =
        get_ofBroadCast(requestCode, intent, getFlag_UPDATE_CURRENT())

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFlag_UPDATE_CURRENT(): Int =
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            CPendingIntent.FLAG_UPDATE_CURRENT or CPendingIntent.FLAG_IMMUTABLE
        } else CPendingIntent.FLAG_UPDATE_CURRENT
}