package com.mozhimen.componentk.camerak.camerax.commons

import androidx.camera.core.ImageCapture
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.cons.ECameraKXTimer
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig

/**
 * @ClassName ICameraXKAction
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/9 14:32
 * @Version 1.0
 */
interface ICameraKX {
    fun initCameraX(owner: LifecycleOwner, config: MCameraKXConfig)
    fun initCameraX(owner: LifecycleOwner)
    fun startCameraX()
    fun startCapture()
    fun setCameraXListener(listener: ICameraKXListener)
    fun setCameraXCaptureListener(listener: ICameraKXCaptureListener)
    fun setCameraXFrameListener(listener: ICameraXKFrameListener)
    fun changeHdr(isOpen: Boolean)
    fun changeFlash(@ImageCapture.FlashMode flashMode: Int)
    fun changeCountDownTimer(timer: ECameraKXTimer)
    fun changeCameraXFacing(@ACameraKXFacing facing: Int)
}