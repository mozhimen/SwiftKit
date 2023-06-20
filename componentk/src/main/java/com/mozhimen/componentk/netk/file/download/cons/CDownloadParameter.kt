package com.mozhimen.componentk.netk.file.download.cons

/**
 * @ClassName CDownloadParameter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 19:58
 * @Version 1.0
 */
object CDownloadParameter {
    const val TABLE_NAME = "t_download"
    const val COLUMN_ID = "id"
    const val COLUMN_URL = "url"
    const val COLUMN_FILENAME = "fileName"
    const val COLUMN_DESTINATION_URI = "dest_uri"
    const val COLUMN_IGNORE_LOCAL = "ignore_local"
    const val COLUMN_NEED_INSTALL = "need_install"
    const val COLUMN_NOTIFICATION_VISIBILITY = "notifier_visibility"
    const val COLUMN_NOTIFICATION_TITLE = "notifier_title"
    const val COLUMN_NOTIFICATION_CONTENT = "notifier_content"
    const val COLUMN_TOTAL_BYTES = "totalBytes"
    const val COLUMN_STATUS = "status"

    const val EXTRA_URL = "url"
    const val EXTRA_FROM = "from"
    const val EXTRA_FROM_NOTIFIER = 2

    const val DOWNLOAD_ENGINE_EMBED = 0//使用内置的下载引擎
    const val DOWNLOAD_ENGINE_SYSTEM_DM = 1//使用系统的 DownloadManager

    const val NOTIFICATION_CHANNEL_ID = "download_channel_normal"
    const val EMBED_HTTP_TEMP_REDIRECT = 307
    const val EMBED_HTTP_PERM_REDIRECT = 308
    const val EMBED_HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416
    const val EMBED_MAX_REDIRECTS = 5  // can't be more than 7.
}