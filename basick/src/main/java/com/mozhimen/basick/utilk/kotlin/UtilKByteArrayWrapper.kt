package com.mozhimen.basick.utilk.kotlin

/**
 * @ClassName UtilKByteArrayDeal
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:11
 * @Version 1.0
 */
fun ByteArray.indexOf(bytes: ByteArray): Int =
    UtilKByteArrayWrapper.indexOf(this, bytes)

///////////////////////////////////////////////////////////////////////////

object UtilKByteArrayWrapper {
    /** Return the index at which the array was found or -1. */
    @JvmStatic
    fun indexOf(bytesOri: ByteArray, bytesDes: ByteArray): Int {
        if (bytesDes.isEmpty())
            return 0
        outer@ for (i in 0 until bytesOri.size - bytesDes.size + 1) {
            for (j in bytesDes.indices) {
                if (bytesOri[i + j] != bytesDes[j]) {
                    continue@outer
                }
            }
            return i
        }
        return -1
    }


    /**
     * 合并Bytes
     */
    @JvmStatic
    fun joinBytes_of2(bytes1: ByteArray, bytes2: ByteArray): ByteArray {
        val bytes = ByteArray(bytes1.size + bytes2.size)
        System.arraycopy(bytes1, 0, bytes, 0, bytes1.size)
        System.arraycopy(bytes2, 0, bytes, bytes1.size, bytes2.size)
        return bytes
    }

    /**
     * 合并Bytes
     */
    @JvmStatic
    fun joinBytes_of5(bytes1: ByteArray, bytes2: ByteArray, bytes3: ByteArray, bytes4: ByteArray, bytes5: ByteArray): ByteArray {
        val bytes = bytes1.copyOf(bytes1.size + bytes2.size + bytes3.size + bytes4.size + bytes5.size)
        System.arraycopy(bytes2, 0, bytes, bytes1.size, bytes2.size)
        System.arraycopy(bytes3, 0, bytes, bytes1.size + bytes2.size, bytes3.size)
        System.arraycopy(bytes4, 0, bytes, bytes1.size + bytes2.size + bytes3.size, bytes4.size)
        System.arraycopy(bytes5, 0, bytes, bytes1.size + bytes2.size + bytes3.size + bytes4.size, bytes5.size)
        return bytes
    }

    /**
     * 截取Bytes
     */
    @JvmStatic
    fun subBytes(bytes: ByteArray, offset: Int, length: Int): ByteArray {
        val bytes = ByteArray(length)
        System.arraycopy(bytes, offset, bytes, 0, length)
        return bytes
    }
}