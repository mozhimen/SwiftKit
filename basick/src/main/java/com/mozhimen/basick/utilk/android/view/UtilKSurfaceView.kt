package com.mozhimen.basick.utilk.android.view

import android.opengl.EGL14
import android.opengl.EGLConfig
import android.opengl.GLES20
import android.view.Surface


/**
 * @ClassName UtilKSurfaceView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/12/17 17:05
 * @Version 1.0
 */
object UtilKSurfaceView {
    fun clearSurface(surface: Surface) {
        val display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        val version = IntArray(2)
        EGL14.eglInitialize(display, version, 0, version, 1)
        val attribList = intArrayOf(
            EGL14.EGL_RED_SIZE, 8,
            EGL14.EGL_GREEN_SIZE, 8,
            EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_NONE, 0,
            EGL14.EGL_NONE
        )
        val configs = arrayOfNulls<EGLConfig>(1)
        val numConfigs = IntArray(1)
        EGL14.eglChooseConfig(display, attribList, 0, configs, 0, configs.size, numConfigs, 0)
        val config = configs[0]
        val context = EGL14.eglCreateContext(
            display, config, EGL14.EGL_NO_CONTEXT, intArrayOf(
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
            ), 0
        )
        val eglSurface = EGL14.eglCreateWindowSurface(
            display, config, surface, intArrayOf(
                EGL14.EGL_NONE
            ), 0
        )
        EGL14.eglMakeCurrent(display, eglSurface, eglSurface, context)
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        EGL14.eglSwapBuffers(display, eglSurface)
        EGL14.eglDestroySurface(display, eglSurface)
        EGL14.eglMakeCurrent(display, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT)
        EGL14.eglDestroyContext(display, context)
        EGL14.eglTerminate(display)
    }
}