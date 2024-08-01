package com.mozhimen.basick.utilk.android.webkit

import android.webkit.WebStorage

/**
 * @ClassName UtilKWebStorage
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/31
 * @Version 1.0
 */
object UtilKWebStorage {
    @JvmStatic
    fun get(): WebStorage =
        WebStorage.getInstance()

    @JvmStatic
    fun deleteAllData() {
        get().deleteAllData()
    }
}