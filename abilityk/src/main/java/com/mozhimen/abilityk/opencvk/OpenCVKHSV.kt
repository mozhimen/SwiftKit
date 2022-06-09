package com.mozhimen.abilityk.opencvk

import com.mozhimen.abilityk.opencvk.mos.OpenCVKColorHSV
import com.mozhimen.basick.logk.LogK
import com.mozhimen.opencvk.OpenCVK
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * @ClassName OpenCVKHSV
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 11:16
 * @Version 1.0
 */
object OpenCVKHSV {
    private const val TAG = "OpenCVKHSV>>>>>"

    @JvmStatic
    fun divideColor(matSrc: Mat, colorHSV: OpenCVKColorHSV): Mat {
        val matHsv = Mat()
        try {
            Imgproc.pyrMeanShiftFiltering(matSrc, matSrc, 15.0, 80.0)
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_RGB2HSV)
            Core.inRange(
                matHsv,
                Scalar(colorHSV.hMin, colorHSV.sMin, colorHSV.vMin),
                Scalar(colorHSV.hMax, colorHSV.sMax, colorHSV.vMin),
                matHsv
            )
            Imgproc.GaussianBlur(matHsv, matHsv, Size(5.0, 3.0), 0.0)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
        }
        return matHsv
    }
}