package com.mozhimen.componentk.netk.file.download

import android.content.Context
import com.mozhimen.basick.utilk.java.security.UtilKMd5
import com.mozhimen.componentk.netk.file.download.SpHelper
import java.io.File

internal object DownloadUtils {

    fun getLocalDownloadId(context: Context, url: String): Long {
        return SpHelper.get(context).getLong("${UtilKMd5.str2strMd5(url)}-id", -1L)
    }

    fun saveDownloadId(context: Context, url: String, id: Long) {
        SpHelper.get(context).putLong("${UtilKMd5.str2strMd5(url)}-id", id)
    }

    internal fun getPercent(totalSize: Long, downloadedSize: Long) = if (totalSize <= 0) 0 else
        (downloadedSize / totalSize.toDouble() * 100).toInt()

    fun getDownloadDir(context: Context): File {
        val dir = context.externalCacheDir
        // a file named cache
        if (dir?.isDirectory == true) {
            return dir
        }
        return context.filesDir
    }
}