package com.mozhimen.abilityk.scank

import android.graphics.Bitmap
import com.mozhimen.abilityk.transk.TransKOpenCV
import com.mozhimen.basick.logk.LogK
import com.mozhimen.opencvk.OpenCVK
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * @ClassName ScanKOpenCV
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/4 13:00
 * @Version 1.0
 */
object ScanKOpenCV {
    private const val TAG = "ScanKOpenCV>>>>>"

    @JvmStatic
    fun similarity(bitmap: Bitmap, orgBitmap: Bitmap): Double {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        require(bitmap.width == orgBitmap.width && bitmap.height == orgBitmap.height) { "two bmp must have same size" }

        val matSrc = TransKOpenCV.bitmap2Mat(bitmap)
        val matDes = TransKOpenCV.bitmap2Mat(orgBitmap)
        val matSrcGray = Mat()
        val matDesGray = Mat()

        try {
            Imgproc.cvtColor(matSrc, matSrcGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.cvtColor(matDes, matDesGray, Imgproc.COLOR_BGR2GRAY)

            matSrcGray.convertTo(matSrcGray, CvType.CV_32F)
            matDesGray.convertTo(matDesGray, CvType.CV_32F)
            return Imgproc.compareHist(matSrcGray, matDesGray, Imgproc.CV_COMP_CORREL)
        } catch (e: Exception) {
            LogK.et(TAG, e.message ?: "")
            e.printStackTrace()
        } finally {
            matSrc.release()
            matDes.release()
            matSrcGray.release()
            matDesGray.release()
        }
        return 0.0
    }

    @JvmStatic
    fun getCircleNum(bitmap: Bitmap): Int {
        val matSrc = TransKOpenCV.bitmap2Mat(bitmap)
        val matSrcGray = Mat()
        val matCircles = Mat()

        try {
            Imgproc.pyrMeanShiftFiltering(matSrc, matSrcGray, 15.0, 80.0)
            Imgproc.cvtColor(matSrcGray, matSrcGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.GaussianBlur(matSrcGray, matSrcGray, Size(3.0, 3.0), 0.0)
            Imgproc.HoughCircles(matSrcGray, matCircles, Imgproc.HOUGH_GRADIENT, 1.0, 20.0, 100.0, 30.0, 10, 200)
            return matCircles.cols()
        } finally {
            matSrc.release()
            matSrcGray.release()
            matCircles.release()
        }
    }
}