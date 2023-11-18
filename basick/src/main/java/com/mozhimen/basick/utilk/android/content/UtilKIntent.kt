package com.mozhimen.basick.utilk.android.content

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent

/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/26 23:03
 * @Version 1.0
 */
fun Intent.isIntentAvailable(context: Context): Boolean =
    UtilKIntent.isIntentAvailable(this, context)

fun Intent.getQueryParameter(key: String): String? =
    UtilKIntent.getQueryParameter(this, key)

object UtilKIntent {
    @JvmStatic
    fun getQueryParameter(intent: Intent, key: String): String? =
        intent.data?.getQueryParameter(key)

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * 要启动的intent是否可用
     */
    fun isIntentAvailable(intent: Intent, context: Context): Boolean =
        resolveActivity(intent, context) != null

    ///////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("QueryPermissionsNeeded")
    @JvmStatic
    fun resolveActivity(intent: Intent, context: Context): ComponentName? =
        intent.resolveActivity(UtilKPackageManager.get(context))


}