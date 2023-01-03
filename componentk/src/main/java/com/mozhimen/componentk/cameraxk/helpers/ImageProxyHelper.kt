package com.mozhimen.componentk.cameraxk.helpers

import androidx.camera.core.ImageProxy

/**
 * @ClassName ImageProxyHelper
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 1:14
 * @Version 1.0
 */
object ImageProxyHelper {
    /**
     * 关闭
     * @param imageProxy ImageProxy
     */
    @JvmStatic
    fun close(imageProxy: ImageProxy) {
        imageProxy.close()
    }

    /**
     * 获得format
     * @param imageProxy ImageProxy
     * @return Int
     */
    @JvmStatic
    fun getFormat(imageProxy: ImageProxy): Int {
        return imageProxy.format
    }
}