package com.mozhimen.basick.utilk.open

import android.opengl.Matrix

/**
 * @ClassName UtilKMatrix
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 13:03
 * @Version 1.0
 */
object UtilKMatrix {
    const val MATRIX_FIT_XY = 0
    const val MATRIX_CENTER_CROP = 1
    const val MATRIX_CENTER_IN_SIDE = 2
    const val MATRIX_FIT_START = 3
    const val MATRIX_FIT_END = 4

    @JvmStatic
    fun getMatrix(
        matrix: FloatArray,
        type: Int,
        imgWidth: Int,
        imgHeight: Int,
        viewWidth: Int,
        viewHeight: Int
    ) {
        if (imgHeight > 0 && imgWidth > 0 && viewWidth > 0 && viewHeight > 0) {
            val projection = FloatArray(16)
            val camera = FloatArray(16)
            if (type == MATRIX_FIT_XY) {
                Matrix.orthoM(projection, 0, -1f, 1f, -1f, 1f, 1f, 3f)
                Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
                Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
            }
            val sWhView = viewWidth.toFloat() / viewHeight
            val sWhImg = imgWidth.toFloat() / imgHeight
            if (sWhImg > sWhView) {
                when (type) {
                    MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 1f, 1 - 2 * sWhImg / sWhView, 1f, 1f, 3f)
                    MATRIX_FIT_END -> Matrix.orthoM(projection, 0, -1f, 1f, -1f, 2 * sWhImg / sWhView - 1, 1f, 3f)
                }
            } else {
                when (type) {
                    MATRIX_CENTER_CROP -> Matrix.orthoM(projection, 0, -1f, 1f, -sWhImg / sWhView, sWhImg / sWhView, 1f, 3f)
                    MATRIX_CENTER_IN_SIDE -> Matrix.orthoM(projection, 0, -sWhView / sWhImg, sWhView / sWhImg, -1f, 1f, 1f, 3f)
                    MATRIX_FIT_START -> Matrix.orthoM(projection, 0, -1f, 2 * sWhView / sWhImg - 1, -1f, 1f, 1f, 3f)
                    MATRIX_FIT_END -> Matrix.orthoM(projection, 0, 1 - 2 * sWhView / sWhImg, 1f, -1f, 1f, 1f, 3f)
                }
            }
            Matrix.setLookAtM(camera, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
            Matrix.multiplyMM(matrix, 0, projection, 0, camera, 0)
        }
    }

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

    @JvmStatic
    fun rotateMatrix(matrix: FloatArray, angle: Float): FloatArray {
        Matrix.rotateM(matrix, 0, angle, 0f, 0f, 1f)
        return matrix
    }

    @JvmStatic
    fun flipMatrix(matrix: FloatArray, isFlipX: Boolean, isFlipY: Boolean): FloatArray {
        if (isFlipX || isFlipY) {
            Matrix.scaleM(matrix, 0, if (isFlipX) -1f else 1f, if (isFlipY) -1f else 1f, 1f)
        }
        return matrix
    }

    @JvmStatic
    fun getOriginalMatrix(): FloatArray = floatArrayOf(1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f)
}