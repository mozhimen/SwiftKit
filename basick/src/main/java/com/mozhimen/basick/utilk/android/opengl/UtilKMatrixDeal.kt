package com.mozhimen.basick.utilk.android.opengl

import android.opengl.Matrix

/**
 * @ClassName UtilKMatrixDeal
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/6 21:57
 * @Version 1.0
 */
object UtilKMatrixDeal {
    @JvmStatic
    fun rotate(matrix: FloatArray, angle: Float): FloatArray {
        Matrix.rotateM(matrix, 0, angle, 0f, 0f, 1f)
        return matrix
    }

    @JvmStatic
    fun flip(matrix: FloatArray, isFlipX: Boolean, isFlipY: Boolean): FloatArray {
        if (isFlipX || isFlipY)
            Matrix.scaleM(matrix, 0, if (isFlipX) -1f else 1f, if (isFlipY) -1f else 1f, 1f)
        return matrix
    }
}