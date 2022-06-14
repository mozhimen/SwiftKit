package com.mozhimen.app.abilityk.opencvk

import android.os.Bundle
import android.util.Log
import com.mozhimen.abilityk.opencvk.OpenCVKTrans
import com.mozhimen.abilityk.opencvk.setMat
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityOpencvkMatchBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.opencvk.OpenCVK
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfDMatch
import org.opencv.core.MatOfKeyPoint
import org.opencv.features2d.FlannBasedMatcher
import org.opencv.features2d.ORB

class OpenCVKMatchActivity : BaseKActivity<ActivityOpencvkMatchBinding, BaseKViewModel>(R.layout.activity_opencvk_match) {
    private val firstBgr by lazy {
        OpenCVKTrans.bitmap2Mat(UtilKRes.getDrawable(R.mipmap.scank_contrast_test)!!.drawable2Bitmap())
    }

    private val secondBgr by lazy {
        OpenCVKTrans.bitmap2Mat(UtilKRes.getDrawable(R.mipmap.scank_contrast_test)!!.drawable2Bitmap())
    }

    private val firstGray by lazy { OpenCVKTrans.mat2Gray(firstBgr) }
    private val secondGray by lazy { OpenCVKTrans.mat2Gray(secondBgr) }

    override fun initData(savedInstanceState: Bundle?) {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        doORBFlannMatch()
    }

    private fun doORBFlannMatch() {
        val firstKeyPoints = MatOfKeyPoint()
        val secondKeyPoints = MatOfKeyPoint()

        val firstDescriptor = Mat()
        val secondDescriptor = Mat()

        orbFeatures(firstGray, firstKeyPoints, firstDescriptor)
        orbFeatures(secondGray, secondKeyPoints, secondDescriptor)
        if (firstDescriptor.type() != CvType.CV_32F && secondDescriptor.type() != CvType.CV_32F) {
            firstDescriptor.convertTo(firstDescriptor, CvType.CV_32F)
            secondDescriptor.convertTo(secondDescriptor, CvType.CV_32F)
        }

        val matches = MatOfDMatch()
        val matcher = FlannBasedMatcher.create()
        matcher.match(firstDescriptor, secondDescriptor, matches)
        Log.e(TAG, "matchers size = ${matches.size()}")

        val list = matches.toList()
        list.sortBy { it.distance }
        Log.e(TAG, "min = ${list.first().distance}")
        //val min = list.first().distance
        val max = list.last().distance

        val goodMatchers = list.filter {
            it.distance < max.times(0.4)
        }

        Log.e(TAG, " good matchers size = ${goodMatchers.size}")
    }

    private fun orbFeatures(source: Mat, keyPoints: MatOfKeyPoint, descriptor: Mat) {
        val orbDetector = ORB.create(1000, 1.2f)
        orbDetector.detect(source, keyPoints)
        orbDetector.compute(source, keyPoints, descriptor)
        Log.e(TAG, "count = ${keyPoints.size()}")
    }

    override fun onDestroy() {
        firstBgr.release()
        secondBgr.release()
        firstGray.release()
        secondGray.release()
        super.onDestroy()
    }
}