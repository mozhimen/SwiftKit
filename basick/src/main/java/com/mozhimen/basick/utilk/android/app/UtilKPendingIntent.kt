package com.mozhimen.basick.utilk.android.app

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKPendingIntent
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/18 23:18
 * @Version 1.0
 */
object UtilKPendingIntent {
    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        PendingIntent.getActivity(context, requestCode, intent, flags)

    @JvmStatic
    fun getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int, opts: Bundle?): PendingIntent =
        PendingIntent.getActivity(context, requestCode, intent, flags, opts)

    @JvmStatic
    fun getBroadcast(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        PendingIntent.getBroadcast(context, requestCode, intent, flags)

    @JvmStatic
    fun getService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        PendingIntent.getService(context, requestCode, intent, flags)

    @RequiresApi(CVersCode.V_26_8_O)
    @JvmStatic
    fun getForegroundService(context: Context, requestCode: Int, intent: Intent, flags: Int): PendingIntent =
        PendingIntent.getForegroundService(context, requestCode, intent, flags)

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int): PendingIntent =
        PendingIntent.getActivities(context, requestCode, intents, flags)

    @JvmStatic
    fun getActivities(context: Context, requestCode: Int, intents: Array<Intent>, flags: Int, opts: Bundle?): PendingIntent =
        PendingIntent.getActivities(context, requestCode, intents, flags, opts)

}