package com.mozhimen.abilityk.opencvk

import android.graphics.Bitmap
import com.mozhimen.opencvk.OpenCVK
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

/**
 * @ClassName OpenCVKTrans
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/8 18:47
 * @Version 1.0
 */
object OpenCVKTrans {
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

    /**
     * 转为灰度图
     * @param mat Mat
     */
    @JvmStatic
    fun mat2Gray(mat: Mat): Mat {
        val matGray = Mat()
        Imgproc.cvtColor(mat, matGray, Imgproc.COLOR_BGR2GRAY)
        return matGray
    }
}