package com.mozhimen.basick.utilk.java.datatype

import java.nio.ByteBuffer


/**
 * @ClassName UtilKByteArray
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 13:15
 * @Version 1.0
 */
object UtilKByteArray {
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
     * @param bytes
     * @return Byte
     */
    @JvmStatic
    fun checkCS(bytes: ByteArray): Byte {
        var result: Byte
        try {
            var num = 0
            for (i in bytes.indices) {
                num = (num + bytes[i]) % 256
            }
            result = num.toByte()
        } catch (e: Exception) {
            result = 0
        }
        return result
    }

    /**
     * 合并Bytes
     * @param bytes1 ByteArray
     * @param bytes2 ByteArray
     * @return ByteArray
     */
    @JvmStatic
    fun joinBytes(bytes1: ByteArray, bytes2: ByteArray): ByteArray {
        val bytes3 = ByteArray(bytes1.size + bytes2.size)
        System.arraycopy(bytes1, 0, bytes3, 0, bytes1.size)
        System.arraycopy(bytes2, 0, bytes3, bytes1.size, bytes2.size)
        return bytes3
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
        val bytes1 = ByteArray(length)
        System.arraycopy(bytes, offset, bytes1, 0, length)
        return bytes1
    }

    /**
     * 转HexString
     * @param bytes ByteArray
     * @param size Int
     * @return String
     */
    @JvmStatic
    fun bytes2HexStr(bytes: ByteArray, size: Int): String {
        val sb = StringBuilder()
        for (i in 0 until size) {
            val hex = Integer.toHexString(0xFF and bytes[i].toInt())
            if (hex.length == 1) {
                sb.append('0')
            }
            sb.append(hex)
        }
        return sb.toString()
    }
}