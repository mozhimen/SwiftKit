package com.mozhimen.componentk.cameraxk.commons

import androidx.camera.core.CameraXConfig
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.cons.ECameraXKTimer
import com.mozhimen.componentk.cameraxk.mos.MCameraXKConfig

/**
 * @ClassName ICameraXKAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 14:32
 * @Version 1.0
 */
interface ICameraXK {
    fun initCamera(owner: LifecycleOwner, config: MCameraXKConfig)
    fun initCamera(owner: LifecycleOwner)
    fun startCamera()
    fun setCameraXKListener(listener: ICameraXKListener)
    fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener)
    fun setCameraXKFrameListener(listener: ICameraXKFrameListener)
    fun changeHdr(isOpen: Boolean)
    fun changeFlash(@ImageCapture.FlashMode flashMode: Int)
    fun changeCountDownTimer(timer: ECameraXKTimer)
    fun changeCameraFacing(@ACameraXKFacing facing: Int)
    fun takePicture()
}