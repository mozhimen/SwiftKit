package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mozhimen.basick.elemk.cons.CMsg
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */
object UtilKByteArray : BaseUtilK() {

    /**
     * cs校验
     * @param bytes
     * @return Byte
     */
    @JvmStatic
    fun checkCS(bytes: ByteArray): Byte {
        var result: Byte
        try {
            var num = 0
            for (i in bytes.indices) num = (num + bytes[i]) % 256
            result = num.toByte()
        } catch (e: Exception) {
            result = 0
        }
        return result
    }
}