package com.mozhimen.componentk.camera2k.commons

import android.opengl.GLSurfaceView

/**
 * @ClassName ICamera2KRenderer
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 11:22
 * @Version 1.0
 */
interface ICamera2KRenderer : GLSurfaceView.Renderer {
    fun onDestroy()
}