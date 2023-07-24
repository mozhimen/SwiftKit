package com.mozhimen.componentk.cameraxk.commons

import androidx.camera.core.ImageProxy


/**
 * @ClassName ICameraXKAnalyzer
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/24 18:48
 * @Version 1.0
 */
interface ICameraXKAnalyzer {
    fun analyze(imageProxy: ImageProxy)
}