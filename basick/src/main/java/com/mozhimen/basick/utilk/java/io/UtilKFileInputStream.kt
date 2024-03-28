package com.mozhimen.basick.utilk.java.io

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @ClassName UtilKFileInputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKFileInputStream {
    @JvmStatic
    fun get(file: File): FileInputStream =
        FileInputStream(file)

    @JvmStatic
    fun get(file: File, isAppend: Boolean = false): FileOutputStream =
        FileOutputStream(file, isAppend)


}