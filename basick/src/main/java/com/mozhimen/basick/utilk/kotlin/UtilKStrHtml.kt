package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.android.text.UtilKHtml

/**
 * @ClassName UtilKStrHtml
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/20
 * @Version 1.0
 */
fun String.strHtml2chars(): CharSequence =
    UtilKStrHtml.strHtml2chars(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKStrHtml {
    @JvmStatic
    fun strHtml2chars(strHtml: String): CharSequence =
        UtilKHtml.fromHtml(strHtml)
}