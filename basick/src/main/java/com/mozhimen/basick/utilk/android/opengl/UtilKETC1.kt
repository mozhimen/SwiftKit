package com.mozhimen.basick.utilk.android.opengl

import android.opengl.ETC1
import java.nio.Buffer
import java.nio.ByteBuffer

/**
 * @ClassName UtilKETC1
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/6 14:19
 * @Version 1.0
 */
object UtilKETC1 {
    @JvmStatic
    fun getWidth(header: Buffer): Int =
        ETC1.getWidth(header)

    @JvmStatic
    fun getHeight(header: Buffer): Int =
        ETC1.getHeight(header)

    @JvmStatic
    fun getEncodedDataSize(width: Int, height: Int): Int =
        ETC1.getEncodedDataSize(width, height)

    @JvmStatic
    fun isValid(header: Buffer): Boolean =
        ETC1.isValid(header)
}