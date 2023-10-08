package com.mozhimen.componentk.netk.file.download.cons

import android.content.Context
import com.mozhimen.componentk.R
import com.mozhimen.componentk.netk.file.download.mos.DownloadException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @ClassName CErrorCode
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 20:25
 * @Version 1.0
 */
object CErrorCode {
    const val NO_NETWORK = 1
    const val DM_FAILED = 3
    const val MISSING_URI = 4
    const val TOO_MANY_REDIRECTS = 5
    const val CANNOT_RESUME = 6
    const val MISSING_LOCATION_WHEN_REDIRECT = 7
    const val UNHANDLED = 8

    @JvmStatic
    fun getDescriptionForException(context: Context, exception: Throwable): String =
        if (exception is DownloadException) {
            when (exception.errorType) {
                NO_NETWORK -> context.getString(R.string.netk_file_notifier_content_without_network)
                CANNOT_RESUME -> context.getString(R.string.netk_file_notifier_content_partial_error)
                TOO_MANY_REDIRECTS -> context.getString(R.string.netk_file_notifier_content_too_many_redirects)
                MISSING_LOCATION_WHEN_REDIRECT -> context.getString(R.string.netk_file_notifier_content_missing_location)
                else -> context.getString(
                    R.string.netk_file_notifier_content_unhandled_err,
                    exception.responseCode
                )
            }
        } else {
            when (exception) {
                is SocketTimeoutException -> context.getString(R.string.netk_file_notifier_content_network_timeout)
                is SocketException -> context.getString(R.string.netk_file_notifier_content_without_network)
                is ConnectException -> context.getString(R.string.netk_file_notifier_content_without_network)
                is UnknownHostException -> context.getString(R.string.netk_file_notifier_content_without_network)
                else -> context.getString(R.string.netk_file_notifier_content_err_placeholder, exception.message ?: exception::class.java.name)
            }
        }
}