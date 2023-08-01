package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKByteArrayDeal
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:11
 * @Version 1.0
 */
object UtilKByteArrayDeal {
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
}