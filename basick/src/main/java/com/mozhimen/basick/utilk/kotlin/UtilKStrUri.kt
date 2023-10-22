package com.mozhimen.basick.utilk.kotlin

import android.net.Uri

/**
 * @ClassName UtilKStrUri
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:22
 * @Version 1.0
 */
fun String.strUri2uri(): Uri =
    UtilKStrUri.strUri2uri(this)

object UtilKStrUri {
    @JvmStatic
    fun strUri2uri(strUri: String): Uri =
        Uri.parse(strUri)
}