package com.mozhimen.basick.utilk.kotlin

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

fun <T> T.t2ByteArray(): ByteArray? =
    UtilKByteArray.t2ByteArray(this)

fun ByteArray.byteArray2Obj(): Any? =
    UtilKByteArray.byteArray2Obj(this)

object UtilKByteArray : BaseUtilK() {
    private val HEX_DIGITS = arrayOf(
        "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    )

    @JvmStatic
    fun byteArray2Obj(bytes: ByteArray): Any? {
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
    fun <T> t2ByteArray(obj: T): ByteArray? =
        obj2ByteArray(obj!!)

    @JvmStatic
    fun obj2ByteArray(obj: Any): ByteArray? {
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
    fun byteBuffer2ByteArray(byteBuffer: ByteBuffer): ByteArray {
        byteBuffer.rewind()    // Rewind the buffer to zero
        val data = ByteArray(byteBuffer.remaining())
        byteBuffer.get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    /**
     * 合并数组
     * @param first ByteArray
     * @param second ByteArray
     * @param third ByteArray
     * @param four ByteArray
     * @param fif ByteArray
     * @return ByteArray
     */
    @JvmStatic
    fun concat(first: ByteArray, second: ByteArray, third: ByteArray, four: ByteArray, fif: ByteArray): ByteArray {
        val result = first.copyOf(first.size + second.size + third.size + four.size + fif.size)
        System.arraycopy(second, 0, result, first.size, second.size)
        System.arraycopy(third, 0, result, first.size + second.size, third.size)
        System.arraycopy(four, 0, result, first.size + second.size + third.size, four.size)
        System.arraycopy(fif, 0, result, first.size + second.size + third.size + four.size, fif.size)
        return result
    }

    /**
     * cs校验
     * @param byteArray
     * @return Byte
     */
    @JvmStatic
    fun checkCS(byteArray: ByteArray): Byte {
        var result: Byte
        try {
            var num = 0
            for (i in byteArray.indices) {
                num = (num + byteArray[i]) % 256
            }
            result = num.toByte()
        } catch (e: Exception) {
            result = 0
        }
        return result
    }

    /**
     * 合并Bytes
     * @param byteArray1 ByteArray
     * @param byteArray2 ByteArray
     * @return ByteArray
     */
    @JvmStatic
    fun joinByteArray(byteArray1: ByteArray, byteArray2: ByteArray): ByteArray {
        val byteArray3 = ByteArray(byteArray1.size + byteArray2.size)
        System.arraycopy(byteArray1, 0, byteArray3, 0, byteArray1.size)
        System.arraycopy(byteArray2, 0, byteArray3, byteArray1.size, byteArray2.size)
        return byteArray3
    }

    /**
     * 截取Bytes
     * @param byteArray ByteArray
     * @param offset Int
     * @param length Int
     * @return ByteArray
     */
    @JvmStatic
    fun subByteArray(byteArray: ByteArray, offset: Int, length: Int): ByteArray {
        val byteArray1 = ByteArray(length)
        System.arraycopy(byteArray, offset, byteArray1, 0, length)
        return byteArray1
    }

    /**
     * 转HexString
     * @param byteArray ByteArray
     * @param size Int
     * @return String
     */
    @JvmStatic
    fun byteArray2HexStr(byteArray: ByteArray, size: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until size) {
            val hex = Integer.toHexString(0xFF and byteArray[i].toInt())
            if (hex.length == 1) stringBuilder.append('0')
            stringBuilder.append(hex)
        }
        return stringBuilder.toString()
    }

    /**
     * 转换字节数组为16进制字串
     * @param byteArray 字节数组
     * @return 16进制字串
     */
    @JvmStatic
    fun byteArray2HexStr(byteArray: ByteArray): String {
        val stringBuilder = StringBuilder()
        for (value in byteArray) {
            stringBuilder.append(byte2HexStr(value))
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
    fun byte2HexStr(byte: Byte): String {
        return HEX_DIGITS[(byte.toInt() and 0xf0) shr 4] + HEX_DIGITS[byte.toInt() and 0x0f]
    }


}