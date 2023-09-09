package com.mozhimen.basick.utilk.java.io

import android.os.FileUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.kotlin.appendStrLineBreak
import com.mozhimen.basick.utilk.kotlin.str2bytes
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * @ClassName UtilKFileOutputStream
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 16:54
 * @Version 1.0
 */
fun FileOutputStream.writeStr2fileOutputStream(str: String) {
    UtilKFileOutputStream.writeStr2fileOutputStream(this, str)
}

fun FileOutputStream.writeBytes2fileOutputStream(bytes: ByteArray) {
    UtilKFileOutputStream.writeBytes2fileOutputStream(this, bytes)
}

//////////////////////////////////////////////////////////////////////////

object UtilKFileOutputStream : IUtilK {
    @JvmStatic
    fun writeStr2fileOutputStream(fileOutputStream: FileOutputStream, str: String) {
        writeBytes2fileOutputStream(fileOutputStream, str.appendStrLineBreak().str2bytes())
    }

    @JvmStatic
    fun writeBytes2fileOutputStream(fileOutputStream: FileOutputStream, bytes: ByteArray) {
        fileOutputStream.flushClose { it.write(bytes) }
    }
}