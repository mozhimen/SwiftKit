package com.mozhimen.basick.utilk

import java.nio.ByteBuffer

/**
 * @ClassName UtilKBytes
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/8/30 16:51
 * @Version 1.0
 */
object UtilKBytes {
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
}