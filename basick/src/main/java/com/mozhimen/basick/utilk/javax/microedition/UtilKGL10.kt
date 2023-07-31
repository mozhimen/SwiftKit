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
    /**
     * gl10转bitmap
     * @param gl10 GL10
     * @param width Int
     * @param height Int
     * @param x Int
     * @param y Int
     * @return Bitmap
     */
    @JvmStatic
    @Throws(Exception::class)
    fun gl102bitmap(gl10: GL10, width: Int, height: Int, x: Int, y: Int): Bitmap {
        val bitmapBuffer = IntArray(width * height)
        val bitmapSource = IntArray(width * height)
        val intBuffer = IntBuffer.wrap(bitmapBuffer)
        intBuffer.position(0)
        gl10.glReadPixels(x, y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer)
        var offset1: Int
        var offset2: Int
        for (i in 0 until height) {
            offset1 = i * width
            offset2 = (height - i - 1) * width
            for (j in 0 until width) {
                val texturePixel = bitmapBuffer[offset1 + j]
                val blue = texturePixel shr 16 and 0xff
                val red = texturePixel shl 16 and 0x00ff0000
                val pixel = texturePixel and -0xff0100 or red or blue
                bitmapSource[offset2 + j] = pixel
            }
        }
        return Bitmap.createBitmap(bitmapSource, width, height, Bitmap.Config.ARGB_8888)
    }
}