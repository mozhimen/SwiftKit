package com.mozhimen.componentk.netk.file.cons

import com.mozhimen.basick.cachek.CacheK
import com.mozhimen.componentk.netk.file.mos.MTaskInfo


/**
 * @ClassName DownloadCache
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 17:48
 * @Version 1.0
 */
object CDownloadCache {
    private const val pre_ = "netk_download_"
    private const val task_info_ = pre_ + "task_info_"

    fun getLastTaskInfo(key: String): MTaskInfo? {
        return CacheK.getCache<MTaskInfo>(pre_ + task_info_ + key)
    }

    fun deleteLastTaskInfo(key: String) {
        return CacheK.deleteCache(pre_ + task_info_ + key)
    }
}