package com.mozhimen.basick.utilk.kotlin

import android.text.Spanned
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.text.UtilKHtml

/**
 * @ClassName UtilKStrHtml
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/20
 * @Version 1.0
 */
@RequiresApi(CVersCode.V_24_7_N)
fun String.strHtml2chars(flags: Int): Spanned =
    UtilKStrHtml.strHtml2chars(this, flags)

fun String.strHtml2chars(): Spanned =
    UtilKStrHtml.strHtml2chars(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKStrHtml {
    @JvmStatic
    @RequiresApi(CVersCode.V_24_7_N)
    fun strHtml2chars(strHtml: String, flags: Int): Spanned =
        UtilKHtml.fromHtml(strHtml, flags)

    @JvmStatic
    fun strHtml2chars(strHtml: String): Spanned =
        UtilKHtml.fromHtml(strHtml)
}