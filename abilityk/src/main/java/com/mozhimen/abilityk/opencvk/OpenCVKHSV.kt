package com.mozhimen.abilityk.opencvk

import android.graphics.Bitmap
import com.mozhimen.abilityk.opencvk.mos.OpenCVKColorHSV
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
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_BGR2HSV)
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

    @JvmStatic
    fun divideColor(bitmap: Bitmap, colorHSV: OpenCVKColorHSV): Bitmap {
        val matSrc = OpenCVKTrans.bitmap2Mat(bitmap)
        val matHsv = Mat()
        try {
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_BGR2HSV)
            Core.inRange(
                matHsv,
                Scalar(colorHSV.hMin, colorHSV.sMin, colorHSV.vMin),
                Scalar(colorHSV.hMax, colorHSV.sMax, colorHSV.vMin),
                matHsv
            )
            Imgproc.cvtColor(matHsv, matHsv, Imgproc.COLOR_BGR2GRAY)
            Imgproc.GaussianBlur(matHsv, matHsv, Size(3.0, 3.0), 0.0)
            return OpenCVKTrans.mat2Bitmap(matHsv)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
            matHsv.release()
        }
        return bitmap
    }

    fun colorFilter(bitmap: Bitmap, colorHSV: OpenCVKColorHSV): Bitmap {
        val matSrc = OpenCVKTrans.bitmap2Mat(bitmap)
        val matHsv = Mat()
        var hsv: DoubleArray
        try {
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_BGR2HSV)
            val rowNum = matHsv.rows()
            val colNum = matHsv.cols()
            for (i in 0 until rowNum) {
                for (j in 0 until colNum) {
                    hsv = matHsv.get(i, j).clone()
                    if (!(hsv[0] in colorHSV.hMin..colorHSV.hMax && hsv[1] in colorHSV.sMin..colorHSV.sMax && hsv[2] in colorHSV.vMin..colorHSV.vMax)) {
                        matHsv.put(i, j, 0.0, 0.0, 255.0)
                    }
                }
            }
            return OpenCVKTrans.mat2Bitmap(matHsv)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
            matHsv.release()
        }
        return bitmap
    }

    fun colorFilter(matSrc: Mat, colorHSV: OpenCVKColorHSV): Mat {
        val matHsv = Mat()
        var hsv: DoubleArray
        try {
            Imgproc.cvtColor(matSrc, matHsv, Imgproc.COLOR_BGR2HSV)
            val rowNum = matHsv.rows()
            val colNum = matHsv.cols()
            for (i in 0 until rowNum) {
                for (j in 0 until colNum) {
                    hsv = matHsv.get(i, j).clone()
                    if (!(hsv[0] in colorHSV.hMin..colorHSV.hMax && hsv[1] in colorHSV.sMin..colorHSV.sMax && hsv[2] in colorHSV.vMin..colorHSV.vMax)) {
                        matHsv.put(i, j, 0.0, 0.0, 255.0)
                    }
                }
            }
            return matHsv
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
        }
        return matHsv
    }
}