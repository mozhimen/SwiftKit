package com.mozhimen.basick.utilk.java.io

import com.mozhimen.basick.elemk.commons.IA_Listener
import java.io.Reader
import java.nio.charset.Charset

/**
 * @ClassName UtilKReader
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
fun Reader.forEachLine_use(charset: Charset = Charsets.UTF_8, action: IA_Listener<String>) {
    UtilKReader.forEachLine_use(this, charset, action)
}

/////////////////////////////////////////////////////////////////////////////
object UtilKReader {
    @JvmStatic
    fun forEachLine_use(reader: Reader, charset: Charset = Charsets.UTF_8, action: IA_Listener<String>) {
        reader.use { it.forEachLine(action) }
    }
}