package com.mozhimen.basick.utilk.android.opengl

import android.opengl.Matrix

/**
 * @ClassName UtilKMatrixDeal
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/6 21:57
 * @Version 1.0
 */
object UtilKMatrix {
    @JvmStatic
    fun rotateM(matrix: FloatArray, mOffset: Int, a: Float, x: Float, y: Float, z: Float) {
        Matrix.rotateM(matrix, mOffset, a, x, y, z)
    }

    @JvmStatic
    fun scaleM(matrix: FloatArray, mOffset: Int, x: Float, y: Float, z: Float) {
        Matrix.scaleM(matrix, mOffset, x, y, z)
    }

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun rotateM_ofZ(matrix: FloatArray, angle: Float): FloatArray {
        Matrix.rotateM(matrix, 0, angle, 0f, 0f, 1f)
        return matrix
    }

    @JvmStatic
    fun flip_ofXY(matrix: FloatArray, isFlipX: Boolean, isFlipY: Boolean): FloatArray {
        if (isFlipX || isFlipY)
            Matrix.scaleM(matrix, 0, if (isFlipX) -1f else 1f, if (isFlipY) -1f else 1f, 1f)
        return matrix
    }
}