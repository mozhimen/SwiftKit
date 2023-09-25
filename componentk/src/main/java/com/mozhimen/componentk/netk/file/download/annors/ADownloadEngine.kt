package com.mozhimen.componentk.netk.file.download.annors

import androidx.annotation.IntDef

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(value = [ADownloadEngine.EMBED, ADownloadEngine.SYSTEM_DM])
@Retention(AnnotationRetention.SOURCE)
annotation class ADownloadEngine {
    companion object {
        const val EMBED = 0//使用内置的下载引擎
        const val SYSTEM_DM = 1//使用系统的 DownloadManager
    }
}
