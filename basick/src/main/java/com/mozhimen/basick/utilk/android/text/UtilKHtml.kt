package com.mozhimen.basick.utilk.android.text

import android.text.Html
import android.text.Spanned
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKHtml
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/20
 * @Version 1.0
 */
object UtilKHtml {

    @JvmStatic
    @RequiresApi(CVersCode.V_24_7_N)
    fun fromHtml(strHtml: String, flags: Int): Spanned =
        Html.fromHtml(strHtml, flags)

    @JvmStatic
    fun fromHtml(strHtml: String): Spanned =
        Html.fromHtml(strHtml)
}