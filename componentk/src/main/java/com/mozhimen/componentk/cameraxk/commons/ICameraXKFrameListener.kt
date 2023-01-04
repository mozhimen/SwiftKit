package com.mozhimen.componentk.cameraxk.commons

import androidx.camera.core.ImageProxy

/**
 * @ClassName ICameraXKFrameListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 0:36
 * @Version 1.0
 */
interface ICameraXKFrameListener {
    fun onFrame(image: ImageProxy)
}