package com.mozhimen.abilityk.opencvk

import android.widget.ImageView
import org.opencv.core.Mat

/**
 * @ClassName OpenCVKExts
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/15 0:35
 * @Version 1.0
 */
fun ImageView.setMat(mat: Mat) {
    this.setImageBitmap(OpenCVKTrans.mat2Bitmap(mat))
}