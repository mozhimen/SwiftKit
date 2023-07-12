package com.mozhimen.componentk.netk.file.download.utils

import androidx.annotation.IntDef
import com.mozhimen.componentk.netk.file.download.cons.CDownloadParameter

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(value = [CDownloadParameter.DOWNLOAD_ENGINE_EMBED, CDownloadParameter.DOWNLOAD_ENGINE_SYSTEM_DM])
@Retention(AnnotationRetention.SOURCE)
annotation class DownloadEngine
