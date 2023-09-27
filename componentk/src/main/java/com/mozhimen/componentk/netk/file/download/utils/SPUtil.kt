package com.mozhimen.componentk.netk.file.download.utils

import com.mozhimen.basick.cachek.sharedpreferences.CacheKSP

/**
 * @ClassName SPHelper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/26 21:27
 * @Version 1.0
 */
object SPUtil {
    private val _spProvider by lazy { CacheKSP.instance.with("sp_netk_file_download") }

    @JvmStatic
    fun getLong(key: String, value: Long): Long =
        _spProvider.getLong(key, value)

    @JvmStatic
    fun putLong(key: String, value: Long) {
        _spProvider.putLong(key, value)
    }

    @JvmStatic
    fun clear() {
        _spProvider.clear()
    }
}