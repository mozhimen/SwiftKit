package com.mozhimen.basick.utilk.android.os

import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.java.io.flushClose
import java.io.InputStream
import java.io.OutputStream

/**
 * @ClassName UtilKFileUtils
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/28
 * @Version 1.0
 */
object UtilKFileUtils {
    @JvmStatic
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_29_10_Q)
    fun copy(inputStream: InputStream, outputStream: OutputStream) {
        FileUtils.copy(inputStream, outputStream)
    }

    @JvmStatic
    @Throws(Exception::class)
    @RequiresApi(CVersCode.V_29_10_Q)
    fun copy_use(inputStream: InputStream, outputStream: OutputStream) {
        try {
            FileUtils.copy(inputStream, outputStream)
        } finally {
            inputStream.close()
            outputStream.flushClose()
        }
    }
}