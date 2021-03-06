package com.mozhimen.abilityk.cameraxk.commons

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.abilityk.cameraxk.annors.CameraXKFacing
import com.mozhimen.abilityk.cameraxk.mos.CameraXKTimer

/**
 * @ClassName ICameraXKAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 14:32
 * @Version 1.0
 */
interface ICameraXKAction {
    fun setCameraXKListener(listener: ICameraXKListener)
    fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener)
    fun setImageAnalyzer(analyzer: ImageAnalysis.Analyzer)
    fun changeHdr(isOpen: Boolean)
    fun changeFlash(@ImageCapture.FlashMode flashMode: Int)
    fun changeCountDownTimer(timer: CameraXKTimer)
    fun changeCameraFacing(@CameraXKFacing facing: Int)
    fun takePicture()
}