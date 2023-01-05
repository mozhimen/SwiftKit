package com.mozhimen.abilityk.opencvk

import org.opencv.core.Mat
import org.opencv.core.Size
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

    /**
     * 霍夫圆匹配
     * @param matSrc Mat
     * @return Int
     */
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