package com.mozhimen.componentk.netk.file.download.utils

import androidx.annotation.IntDef
import com.mozhimen.componentk.netk.file.download.DOWNLOAD_ENGINE_EMBED
import com.mozhimen.componentk.netk.file.download.DOWNLOAD_ENGINE_SYSTEM_DM

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(
    DOWNLOAD_ENGINE_EMBED,
    DOWNLOAD_ENGINE_SYSTEM_DM
)
@Retention(AnnotationRetention.SOURCE)
annotation class DownloadEngine
