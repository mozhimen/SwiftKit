package com.mozhimen.abilityk.opencvk

import android.graphics.Bitmap
import com.mozhimen.abilityk.opencvk.mos.OpenCVKColorHSV
import com.mozhimen.basick.logk.LogK
import org.opencv.core.*
import org.opencv.imgproc.Imgproc


/**
 * @ClassName OpenCVKShape
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 13:49
 * @Version 1.0
 */
object OpenCVKShape {
    private const val TAG = "OpenCVKShape>>>>>"

    @JvmStatic
    fun getCircleNum(bitmap: Bitmap): Int {
        val matSrc = OpenCVKTrans.bitmap2Mat(bitmap)
        val matSrcGray = Mat()
        val matCircles = Mat()

        try {
            Imgproc.pyrMeanShiftFiltering(matSrc, matSrcGray, 15.0, 80.0)
            Imgproc.cvtColor(matSrcGray, matSrcGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.GaussianBlur(matSrcGray, matSrcGray, Size(3.0, 3.0), 0.0)
            Imgproc.HoughCircles(matSrcGray, matCircles, Imgproc.HOUGH_GRADIENT, 1.0, 20.0, 100.0, 30.0, 10, 200)
            return matCircles.cols()
        } catch (e: Exception) {
            LogK.et(TAG, e.message ?: "")
            e.printStackTrace()
        } finally {
            matSrc.release()
            matSrcGray.release()
            matCircles.release()
        }
        return 0
    }

    @JvmStatic
    fun getCircleNum(bitmap: Bitmap, colorHSV: OpenCVKColorHSV): Int {
        val matSrc = OpenCVKTrans.bitmap2Mat(bitmap)
        val matHsv = Mat()
        val matCircles = Mat()

        try {
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_BGR2HSV)
            Core.inRange(
                matHsv,
                Scalar(colorHSV.hMin, colorHSV.sMin, colorHSV.vMin),
                Scalar(colorHSV.hMax, colorHSV.sMax, colorHSV.vMin),
                matHsv
            )
            Imgproc.HoughCircles(matHsv, matCircles, Imgproc.HOUGH_GRADIENT, 1.0, 20.0, 100.0, 30.0, 5, 100)
            return matCircles.cols()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
            matHsv.release()
            matCircles.release()
        }
        return 0
    }

    @JvmStatic
    fun getCircleNum(matSrc: Mat): Int {
        val matGray = Mat()
        val matCircles = Mat()

        try {
            Imgproc.pyrMeanShiftFiltering(matSrc, matSrc, 15.0, 80.0)
            Imgproc.cvtColor(matSrc, matGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.GaussianBlur(matGray, matGray, Size(3.0, 3.0), 0.0)
            Imgproc.HoughCircles(matGray, matCircles, Imgproc.HOUGH_GRADIENT, 1.0, 20.0, 100.0, 25.0, 35, 70)
            return matCircles.cols()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
            matGray.release()
            matCircles.release()
        }
        return 0
    }
}