package com.mozhimen.abilityk.opencvk

import android.graphics.Bitmap
import com.mozhimen.basick.logk.LogK
import com.mozhimen.opencvk.OpenCVK
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

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
        val matSrcGray = Mat()
        val matDesGray = Mat()

        try {
            Imgproc.cvtColor(matSrc, matSrcGray, Imgproc.COLOR_BGR2GRAY)
            Imgproc.cvtColor(matDes, matDesGray, Imgproc.COLOR_BGR2GRAY)

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