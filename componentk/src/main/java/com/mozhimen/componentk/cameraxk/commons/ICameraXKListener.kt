package com.mozhimen.componentk.cameraxk.commons

/**
 * @ClassName CameraXKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 1:22
 * @Version 1.0
 */
interface ICameraXKListener {
    fun onCameraStartFail(e: String){}
    fun onCameraFlashOn(){}
    fun onCameraFlashAuto(){}
    fun onCameraFlashOff(){}
    fun onCameraHDRCheck(available: Boolean){}
    fun onCameraHDROpen(){}
}