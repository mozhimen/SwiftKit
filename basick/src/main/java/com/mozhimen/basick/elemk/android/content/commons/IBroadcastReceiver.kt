package com.mozhimen.basick.elemk.android.content.commons

import android.content.Context

/**
 * @ClassName IBroadcastReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/11 18:20
 * @Version 1.0
 */
interface IBroadcastReceiver {
    fun registerReceiver(context: Context)
    fun unregisterReceiver(context: Context)
}