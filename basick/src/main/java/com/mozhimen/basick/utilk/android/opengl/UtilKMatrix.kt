package com.mozhimen.basick.utilk.android.opengl

import android.opengl.Matrix
import com.mozhimen.basick.lintk.annors.AMatrixType

/**
 * @ClassName UtilKMatrix
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 13:03
 * @Version 1.0
 */
object UtilKMatrix {
    @JvmStatic
    fun getMatrixOf(matrix: FloatArray, @AMatrixType type: Int, imgWidth: Int, imgHeight: Int, viewWidth: Int, viewHeight: Int) {
        if (imgHeight > 0 && imgWidth > 0 && viewWidth > 0 && viewHeight > 0) {
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (type == AMatrixType.MATRIX_FIT_XY) {
                Matrix.orthoM(projection, 0, -1f, 1f, -1f, 1f, 1f, 3f)
                Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
                Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
            }
            val sWhView = viewWidth.toFloat() / viewHeight
            val sWhImg = imgWidth.toFloat() / imgHeight
            if (sWhImg > sWhView) {
                when (type) {
                    AMatrixType.MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    AMatrixType.MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 1f, 1 - 2 * sWhImg / sWhView, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_END -> Matrix.orthoM(projection, 0, -1f, 1f, -1f, 2 * sWhImg / sWhView - 1, 1f, 3f)
                }
            } else {
                when (type) {
                    AMatrixType.MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    AMatrixType.MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 2 * sWhView / sWhImg - 1, -1f, 1f, 1f, 3f)
                    AMatrixType.MATRIX_FIT_END -> Matrix.orthoM(projection, 0, 1 - 2 * sWhView / sWhImg, 1f, -1f, 1f, 1f, 3f)
                }
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }

    @JvmStatic
    fun getOriginalMatrix(): FloatArray =
        floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)

    @JvmStatic
    fun getCenterInsideMatrix(matrix: FloatArray, imgWidth: Int, imgHeight: Int, viewWidth: Int, viewHeight: Int) {
        if (imgHeight > 0 && imgWidth > 0 && viewWidth > 0 && viewHeight > 0) {
            val sWhView = viewWidth.toFloat() / viewHeight
            val sWhImg = imgWidth.toFloat() / imgHeight
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (sWhImg > sWhView) {
                Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
            } else {
                Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }
}