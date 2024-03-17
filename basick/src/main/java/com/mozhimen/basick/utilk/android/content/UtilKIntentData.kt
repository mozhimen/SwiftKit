package com.mozhimen.basick.utilk.android.content

import android.content.Intent
import android.net.Uri
import com.mozhimen.basick.lintk.optins.intent_filter.OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE

/**
 * @ClassName UtilKIntentData
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/16 16:54
 * @Version 1.0
 */
@OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE
fun Intent.getQueryParameter(key: String): String? =
    UtilKIntentData.getQueryParameter(this, key)

object UtilKIntentData {
    @JvmStatic
    fun get(intent: Intent): Uri? =
        UtilKIntent.getData(intent)

    //http://com.xxx.xxx?id=?
    @JvmStatic
    @OIntentFilter_ACTION_VIEW_CATEGORY_DEFAULT_BROWSABLE
    fun getQueryParameter(intent: Intent, key: String): String? =
        get(intent)?.getQueryParameter(key)
}