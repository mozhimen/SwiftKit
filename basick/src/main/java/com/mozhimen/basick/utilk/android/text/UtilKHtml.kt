package com.mozhimen.basick.utilk.android.text

import android.text.Html
import com.mozhimen.basick.elemk.android.text.cons.CHtml
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKHtml
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/20
 * @Version 1.0
 */
object UtilKHtml {
    @JvmStatic
    fun fromHtml(content: String): CharSequence =
        if (UtilKBuildVersion.isAfterV_24_7_N())
            Html.fromHtml(content, CHtml.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(content)
}