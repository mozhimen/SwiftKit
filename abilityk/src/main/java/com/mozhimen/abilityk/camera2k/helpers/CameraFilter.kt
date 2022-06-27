package com.mozhimen.abilityk.camera2k.helpers

import android.hardware.Camera

/**
 * @ClassName CameraFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/27 16:35
 * @Version 1.0
 */
open class CameraFilter : OesFilter() {
    override fun initBuffer() {
        super.initBuffer()
        onMovie()
    }

    override fun setFilterFlag(filterFlag: Int) {
        super.setFilterFlag(filterFlag)
        if (getFlag() == Camera.CameraInfo.CAMERA_FACING_FRONT) {    //前置摄像头
            onCameraFront()
        } else if (getFlag() == Camera.CameraInfo.CAMERA_FACING_BACK) {   //后置摄像头
            onCameraBack()
        }
    }

    private fun onCameraFront() {
        val coord = floatArrayOf(
            1.0f, 0.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f
        )
        resetTexBuffer(coord)
    }

    private fun onCameraBack() {
        val coord = floatArrayOf(
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
        )
        resetTexBuffer(coord)
    }

    private fun onMovie() {
        /*float[] coord = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
        };*/
        val coord = floatArrayOf(
            1.0f, 1.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
        )
        resetTexBuffer(coord)
    }

    private fun resetTexBuffer(coord: FloatArray) {
        texBuffer?.apply {
            clear()
            put(coord)
            position(0)
        }
    }
}