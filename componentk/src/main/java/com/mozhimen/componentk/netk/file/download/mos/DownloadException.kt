package com.mozhimen.componentk.netk.file.download.mos

/**
 *
 * @author by chiclaim@google.com
 */
internal class DownloadException(val errorType: Int, errMsg: String, val responseCode: Int = 0) : Exception(errMsg)