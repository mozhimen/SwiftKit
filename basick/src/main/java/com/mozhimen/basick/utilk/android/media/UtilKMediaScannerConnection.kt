package com.mozhimen.basick.utilk.android.media

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener

/**
 * @ClassName UtilKMediaScannerConnection
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 17:48
 * @Version 1.0
 */
object UtilKMediaScannerConnection {
    @JvmStatic
    fun scanFile(context: Context, paths: Array<String>, mimeTypes: Array<String>, callback: OnScanCompletedListener) {
        MediaScannerConnection.scanFile(context, paths, mimeTypes, callback)
    }
}