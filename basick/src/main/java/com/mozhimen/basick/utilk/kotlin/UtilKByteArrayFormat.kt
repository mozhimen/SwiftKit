package com.mozhimen.basick.utilk.kotlin

import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.UtilKFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @ClassName UtilKByteArrayFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:13
 * @Version 1.0
 */
fun ByteArray.asFile(destFile: File, isOverwrite: Boolean = true): File? =
    UtilKByteArrayFormat.bytes2file(this, destFile, isOverwrite)

fun ByteArray.asObj(): Any? =
    UtilKByteArrayFormat.bytes2obj(this)

fun ByteArray.asHexStr(): String =
    UtilKByteArrayFormat.bytes2hexStr(this)

object UtilKByteArrayFormat {
    @JvmStatic
    fun bytes2file(bytes: ByteArray, destFile: File, isOverwrite: Boolean = true): File? {
        UtilKFile.createFile(destFile)
        val fileOutputStream = FileOutputStream(destFile, !isOverwrite)
        try {
            fileOutputStream.write(bytes)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKFile.TAG)
        } finally {
            fileOutputStream.flush()
            fileOutputStream.close()
        }
        return null
    }

    @JvmStatic
    fun bytes2obj(bytes: ByteArray): Any? {
        var byteArrayInputStream: ByteArrayInputStream? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            byteArrayInputStream = ByteArrayInputStream(bytes)
            objectInputStream = ObjectInputStream(byteArrayInputStream)
            return objectInputStream.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKByteArray.TAG)
        } finally {
            byteArrayInputStream?.close()
            objectInputStream?.close()
        }
        return null
    }

    @JvmStatic
    fun <T> t2bytes(obj: T): ByteArray? =
        obj2bytes(obj!!)

    @JvmStatic
    fun obj2bytes(obj: Any): ByteArray? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(obj)
            objectOutputStream.flush()
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(UtilKByteArray.TAG)
        } finally {
            byteArrayOutputStream?.close()
            objectOutputStream?.close()
        }
        return null
    }

    /**
     * 转HexString
     * @param bytes ByteArray
     * @param size Int
     * @return String
     */
    @JvmStatic
    fun bytes2hexStr(bytes: ByteArray, size: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until size) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) stringBuilder.append('0')
            stringBuilder.append(hex)
        }
        return stringBuilder.toString()
    }

    /**
     * 转换字节数组为16进制字串
     * @param bytes 字节数组
     * @return 16进制字串
     */
    @JvmStatic
    fun bytes2hexStr(bytes: ByteArray): String {
        val stringBuilder = StringBuilder()
        for (byte in bytes) {
            stringBuilder.append(byte.asHexStr())
            // 也可以使用下面的方式。 X 表示大小字母，x 表示小写字母，对应的是 HEX_DIGITS 中字母
            // buf.append(String.format("%02X", value));
        }
        return stringBuilder.toString()
    }
}