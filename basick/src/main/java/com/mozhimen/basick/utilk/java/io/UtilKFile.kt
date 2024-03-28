package com.mozhimen.basick.utilk.java.io

import java.io.File


/**
 * @ClassName UtilKFile
 * @Description android:requestLegacyExternalStorage="true" application 设置
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 11:59
 * @Version 1.0
 */

object UtilKFile {
    @JvmStatic
    fun getAbsolutePath(file: File): String =
        file.absolutePath

    @JvmStatic
    fun getLastModified(file: File): Long =
        file.lastModified()

    @JvmStatic
    fun getNameWithoutExtension(file: File): String =
        file.nameWithoutExtension
}

