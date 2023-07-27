package com.mozhimen.componentk.camerak.camerax.commons

import androidx.camera.core.ImageProxy


/**
 * @ClassName ICameraXKAnalyzer
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/24 18:48
 * @Version 1.0
 */
interface ICameraKXAnalyzer {
    fun analyze(imageProxy: ImageProxy)
}