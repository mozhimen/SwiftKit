package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.ByteBuffer


/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */

fun <T> T.asBytes(): ByteArray? =
    UtilKByteArray.t2bytes(this)

fun ByteArray.asObj(): Any? =
    UtilKByteArray.bytes2obj(this)

fun ByteArray.asBitmap(): Bitmap =
    UtilKByteArray.bytes2bitmap(this)

object UtilKByteArray : BaseUtilK() {
    private val HEX_DIGITS = arrayOf(
        "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    )

    @JvmStatic
    fun bytes2bitmap(bytes: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

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
            e.message?.et(TAG)
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
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream?.close()
            objectOutputStream?.close()
        }
        return null
    }

    /**
     * byteBuffer转ByteArray
     * @param byteBuffer ByteBuffer
     * @return ByteArray
     */
    @JvmStatic
    fun byteBuffer2bytes(byteBuffer: ByteBuffer): ByteArray {
        byteBuffer.rewind()    // Rewind the buffer to zero
        val data = ByteArray(byteBuffer.remaining())
        byteBuffer.get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    /**
     * 合并Bytes
     * @param byteArray1 ByteArray
     * @param byteArray2 ByteArray
     * @return ByteArray
     */
    @JvmStatic
    fun joinBytes2(byteArray1: ByteArray, byteArray2: ByteArray): ByteArray {
        val byteArray3 = ByteArray(byteArray1.size + byteArray2.size)
        System.arraycopy(byteArray1, 0, byteArray3, 0, byteArray1.size)
        System.arraycopy(byteArray2, 0, byteArray3, byteArray1.size, byteArray2.size)
        return byteArray3
    }

    /**
     * 合并Bytes
     * @param bytes1 ByteArray
     * @param bytes2 ByteArray
     * @param bytes3 ByteArray
     * @param bytes4 ByteArray
     * @param bytes5 ByteArray
     * @return ByteArray
     */
    @JvmStatic
    fun joinBytes5(bytes1: ByteArray, bytes2: ByteArray, bytes3: ByteArray, bytes4: ByteArray, bytes5: ByteArray): ByteArray {
        val result = bytes1.copyOf(bytes1.size + bytes2.size + bytes3.size + bytes4.size + bytes5.size)
        System.arraycopy(bytes2, 0, result, bytes1.size, bytes2.size)
        System.arraycopy(bytes3, 0, result, bytes1.size + bytes2.size, bytes3.size)
        System.arraycopy(bytes4, 0, result, bytes1.size + bytes2.size + bytes3.size, bytes4.size)
        System.arraycopy(bytes5, 0, result, bytes1.size + bytes2.size + bytes3.size + bytes4.size, bytes5.size)
        return result
    }

    /**
     * cs校验
     * @param bytes
     * @return Byte
     */
    @JvmStatic
    fun checkCSBytes(bytes: ByteArray): Byte {
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


    /**
     * 截取Bytes
     * @param bytes ByteArray
     * @param offset Int
     * @param length Int
     * @return ByteArray
     */
    @JvmStatic
    fun subBytes(bytes: ByteArray, offset: Int, length: Int): ByteArray {
        val byteArray1 = ByteArray(length)
        System.arraycopy(bytes, offset, byteArray1, 0, length)
        return byteArray1
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
        for (value in bytes) {
            stringBuilder.append(byte2hexStr(value))
            // 也可以使用下面的方式。 X 表示大小字母，x 表示小写字母，对应的是 HEX_DIGITS 中字母
            // buf.append(String.format("%02X", value));
        }
        return stringBuilder.toString()
    }

    /**
     * 字节转成字符.
     * @param byte 原始字节.
     * @return 转换后的字符.
     */
    @JvmStatic
    fun byte2hexStr(byte: Byte): String {
        return HEX_DIGITS[(byte.toInt() and 0xf0) shr 4] + HEX_DIGITS[byte.toInt() and 0x0f]
    }
}