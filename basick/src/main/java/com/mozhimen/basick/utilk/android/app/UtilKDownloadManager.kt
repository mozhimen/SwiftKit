package com.mozhimen.basick.utilk.android.app

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKDownloadManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/18 18:10
 * @Version 1.0
 */
object UtilKDownloadManager {
    @JvmStatic
    fun get(context: Context): DownloadManager =
        UtilKContext.getDownloadManager(context)

    @JvmStatic
    fun getUriForDownloadedFile(context: Context, id: Long): Uri? =
        get(context).getUriForDownloadedFile(id)
}