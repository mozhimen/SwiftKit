package com.mozhimen.abilityk.transk

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Mat

/**
 * @ClassName TransKOpenCV
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/8 18:47
 * @Version 1.0
 */
object TransKOpenCV {
    /**
     * bitmap转mat
     * @param bitmap Bitmap
     * @return Mat
     */
    @JvmStatic
    fun bitmap2Mat(bitmap: Bitmap): Mat {
        val matSrc = Mat()
        Utils.bitmapToMat(bitmap, matSrc)
        return matSrc
    }

    /**
     * mat转bitmap
     * @param mat Mat
     * @return Bitmap
     */
    @JvmStatic
    fun mat2Bitmap(mat: Mat): Bitmap {
        val bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, bitmap)
        return bitmap
    }
}