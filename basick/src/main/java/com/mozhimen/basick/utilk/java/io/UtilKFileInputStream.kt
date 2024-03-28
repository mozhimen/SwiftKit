package com.mozhimen.basick.utilk.java.io

import java.io.File
import java.io.FileInputStream

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
}