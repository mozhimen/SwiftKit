package com.mozhimen.abilityk.opencvk

import android.graphics.Bitmap
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.features2d.Features2d

import android.content.DialogInterface

import org.opencv.features2d.DescriptorMatcher

import org.opencv.core.MatOfPoint2f

import org.opencv.core.MatOfKeyPoint

import org.opencv.core.MatOfDMatch


/**
 * @ClassName OpenCVKContrast
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 13:49
 * @Version 1.0
 */
object OpenCVKContrast {
    private const val TAG = "OpenCVKContrast>>>>>"

    /**
     * 相似度对比
     * @param bitmap Bitmap
     * @param orgBitmap Bitmap
     * @return Double
     */
    @JvmStatic
    fun similarity(bitmap: Bitmap, orgBitmap: Bitmap): Double {
        require(bitmap.width == orgBitmap.width && bitmap.height == orgBitmap.height) { "two bmp must have same size" }

        var similarity = 0.0
        val matSrc = OpenCVKTrans.bitmap2Mat(bitmap)
        val matDes = OpenCVKTrans.bitmap2Mat(orgBitmap)
        val matSrcGray = OpenCVKTrans.mat2Gray(matSrc)
        val matDesGray = OpenCVKTrans.mat2Gray(matDes)

        try {
            matSrcGray.convertTo(matSrcGray, CvType.CV_32F)
            matDesGray.convertTo(matDesGray, CvType.CV_32F)
            similarity = Imgproc.compareHist(matSrcGray, matDesGray, Imgproc.CV_COMP_CORREL)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            matSrc.release()
            matDes.release()
            matSrcGray.release()
            matDesGray.release()
        }
        return similarity
    }

}