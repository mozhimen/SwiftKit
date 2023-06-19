package com.mozhimen.componentk.netk.file.download.utils

import androidx.annotation.IntDef
import com.mozhimen.componentk.netk.file.download.NOTIFIER_HIDDEN
import com.mozhimen.componentk.netk.file.download.NOTIFIER_VISIBLE
import com.mozhimen.componentk.netk.file.download.NOTIFIER_VISIBLE_NOTIFY_COMPLETED
import com.mozhimen.componentk.netk.file.download.NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(
    NOTIFIER_VISIBLE,
    NOTIFIER_HIDDEN,
    NOTIFIER_VISIBLE_NOTIFY_COMPLETED,
    NOTIFIER_VISIBLE_NOTIFY_ONLY_COMPLETION
)
@Retention(AnnotationRetention.SOURCE)
annotation class NotifierVisibility
