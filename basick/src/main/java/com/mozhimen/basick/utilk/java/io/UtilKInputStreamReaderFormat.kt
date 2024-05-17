package com.mozhimen.basick.utilk.java.io

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @ClassName UtilKInputStreamReaderFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
fun InputStreamReader.inputStreamReader2bufferedReader(): BufferedReader =
    UtilKInputStreamReaderFormat.inputStreamReader2bufferedReader(this)

//////////////////////////////////////////////////////////////////////////////

object UtilKInputStreamReaderFormat {
    @JvmStatic
    fun inputStreamReader2bufferedReader(inputStreamReader: InputStreamReader): BufferedReader =
        BufferedReader(inputStreamReader)
}