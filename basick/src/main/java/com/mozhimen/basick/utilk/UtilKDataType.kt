package com.mozhimen.basick.utilk

import java.nio.ByteBuffer

/**
 * @ClassName UtilKDataType
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 18:32
 * @Version 1.0
 */
object UtilKDataType {
    /**
     * 判断数据类型是否是原始数据类型
     * @param value Any
     * @return Boolean
     */
    fun isPrimitive(value: Any): Boolean {
        //String
        if (value.javaClass == String::class.java) {
            return true
        }
        try {
            //只适用于int byte short long boolean char double float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * byteBuffer转ByteArray
     * @param byteBuffer ByteBuffer
     * @return ByteArray
     */
    fun byteBuffer2ByteArray(byteBuffer: ByteBuffer): ByteArray {
        byteBuffer.rewind()    // Rewind the buffer to zero
        val data = ByteArray(byteBuffer.remaining())
        byteBuffer.get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }
}