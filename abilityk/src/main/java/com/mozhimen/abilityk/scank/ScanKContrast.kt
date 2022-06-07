package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import com.mozhimen.opencvk.OpenCVK
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

/**
 * @ClassName ScanKContrast
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/4 13:00
 * @Version 1.0
 */
object ScanKContrast {
    @JvmStatic
    fun similarity(bitmap: Bitmap, orgBitmap: Bitmap): Double {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        require(bitmap.width == orgBitmap.width && bitmap.height == orgBitmap.height) { "two bmp must have same size" }

        val mat1 = Mat()
        val mat2 = Mat()
        val matSrc = Mat()
        val matDes = Mat()
        Utils.bitmapToMat(bitmap, mat1)
        Utils.bitmapToMat(orgBitmap, mat2)

        Imgproc.cvtColor(mat1, matSrc, Imgproc.COLOR_BGR2GRAY)
        Imgproc.cvtColor(mat2, matDes, Imgproc.COLOR_BGR2GRAY)

        matSrc.convertTo(matSrc, CvType.CV_32F)
        matDes.convertTo(matDes, CvType.CV_32F)
        return Imgproc.compareHist(matSrc, matDes, Imgproc.CV_COMP_CORREL)
    }
}