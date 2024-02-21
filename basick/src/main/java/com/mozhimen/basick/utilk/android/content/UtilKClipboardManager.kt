package com.mozhimen.basick.utilk.android.content

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * @ClassName UtilKClipboardManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/21
 * @Version 1.0
 */
object UtilKClipboardManager {
    @JvmStatic
    fun get(context: Context): ClipboardManager =
        UtilKContext.getClipboardManager(context)

    @JvmStatic
    fun getPrimaryClip(context: Context): ClipData? =
        get(context).primaryClip

}