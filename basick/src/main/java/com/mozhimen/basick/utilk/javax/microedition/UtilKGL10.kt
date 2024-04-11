package com.mozhimen.basick.utilk.javax.microedition

import android.graphics.Bitmap
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL10

/**
 * @ClassName UtilKGL10
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 17:55
 * @Version 1.0
 */
object UtilKGL10 {
    //gl10转bitmap
    @JvmStatic
    @Throws(Exception::class)
    fun gl102bitmapARGB8888(gl10: GL10, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val bufferBytes = IntArray(width * height)
        val bitmapBytes = IntArray(width * height)
        val intBuffer = IntBuffer.wrap(bufferBytes)
        intBuffer.position(0)
        gl10.glReadPixels(x, y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer)
        var offset1: Int
        var offset2: Int
        for (i in 0 until height) {
            offset1 = i * width
            offset2 = (height - i - 1) * width
            for (j in 0 until width) {
                val texturePixel = bufferBytes[offset1 + j]
                val blue = texturePixel shr 16 and 0xff
                val red = texturePixel shl 16 and 0x00ff0000
                val pixel = texturePixel and -0xff0100 or red or blue
                bitmapBytes[offset2 + j] = pixel
            }
        }
        return Bitmap.createBitmap(bitmapBytes, width, height, Bitmap.Config.ARGB_8888)
    }
}