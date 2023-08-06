package com.mozhimen.basick.utilk.android.graphics

import android.graphics.Matrix


/**
 * @ClassName UtilKMatrix
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/6 22:30
 * @Version 1.0
 */
object UtilKMatrix {

    @JvmStatic
    fun applyRotate(matrix: Matrix, degree: Float, px: Float, py: Float) {
        matrix.setRotate(degree, px, py)
    }

    @JvmStatic
    fun postRotate(matrix: Matrix, degree: Float) {
        matrix.postRotate(degree)
    }

    @JvmStatic
    fun postFlip(matrix: Matrix, flipX: Boolean = false, flipY: Boolean = false) {
        if (flipX || flipY)
            matrix.postScale(if (flipX) -1f else 1f, if (flipY) -1f else 1f)
    }

    @JvmStatic
    fun postScale(matrix: Matrix, ratioX: Float, ratioY: Float) {
        matrix.postScale(ratioX, ratioY)
    }

    @JvmStatic
    fun postTranslate(matrix: Matrix, dx: Float, dy: Float) {
        matrix.postTranslate(dx, dy)
    }

    @JvmStatic
    fun getValues(matrix: Matrix, values: FloatArray) {
        matrix.getValues(values)
    }
}