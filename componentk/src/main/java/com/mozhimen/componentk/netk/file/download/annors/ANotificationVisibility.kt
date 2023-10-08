package com.mozhimen.componentk.netk.file.download.annors

import androidx.annotation.IntDef
import com.mozhimen.basick.elemk.android.app.cons.CDownloadManager

/**
 *
 * @author by chiclaim@google.com
 */
@IntDef(value = [ANotificationVisibility.VISIBLE, ANotificationVisibility.HIDDEN, ANotificationVisibility.VISIBLE_NOTIFY_COMPLETED, ANotificationVisibility.VISIBLE_NOTIFY_ONLY_COMPLETION])
@Retention(AnnotationRetention.SOURCE)
annotation class ANotificationVisibility {
    companion object {
        const val VISIBLE = CDownloadManager.Request.VISIBILITY_VISIBLE
        const val HIDDEN = CDownloadManager.Request.VISIBILITY_HIDDEN
        const val VISIBLE_NOTIFY_COMPLETED = CDownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        const val VISIBLE_NOTIFY_ONLY_COMPLETION = CDownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION
    }
}
