package com.mozhimen.componentk.netk.file.download.utils

import androidx.annotation.IntDef
import com.mozhimen.componentk.netk.file.download.cons.CDownloadConstants

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(value = [CDownloadConstants.NOTIFIER_VISIBLE, CDownloadConstants.NOTIFIER_HIDDEN, CDownloadConstants.NOTIFIER_VISIBLE_NOTIFY_COMPLETED, CDownloadConstants.NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION])
@Retention(AnnotationRetention.SOURCE)
annotation class NotifierVisibility
