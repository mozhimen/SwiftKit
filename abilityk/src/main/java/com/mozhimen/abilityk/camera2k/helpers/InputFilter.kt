package com.mozhimen.abilityk.camera2k.helpers

import android.content.res.Resources
import android.graphics.SurfaceTexture
import android.opengl.GLES20
import java.nio.ByteBuffer

/**
 * @ClassName InputFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 13:33
 * @Version 1.0
 */
open class InputFilter(res: Resources) : BaseFilter(res) {
    private var _filter: CameraFilter? = null
    private var _width = 0
    private var _height = 0

    private val _fFrame = IntArray(1)
    private val _fTexture = IntArray(1)
    private val _cameraTexture = IntArray(1)

    private var _surfaceTexture: SurfaceTexture? = null
    private val _coordOM = FloatArray(16)

    //获取Track数据
    //private val _tBuffer: ByteBuffer? = null

    init {
        _filter = CameraFilter(res)
    }

    fun setCoordMatrix(matrix: FloatArray?) {
        _filter.setCoordMatrix(matrix)
    }

    fun getTexture(): SurfaceTexture? {
        return _surfaceTexture
    }

    fun setFlag(flag: Int) {
        _filter.setFlag(flag)
    }

    fun initBuffer() {}

    fun setMatrix(matrix: FloatArray?) {
        _filter.setMatrix(matrix)
    }

    override fun getOutputTexture(): Int {
        return _fTexture[0]
    }

    override fun onFilterCreate() {
        _filter.create()
        createOesTexture()
        _surfaceTexture = SurfaceTexture(_cameraTexture[0])
    }

    override fun onFilterSizeChanged(width: Int, height: Int) {
        _filter.setSize(width, height)
        if (this._width != width || this._height != height) {
            this._width = width
            this._height = height
            //创建FrameBuffer和Texture
            deleteFrameBuffer()
            GLES20.glGenFramebuffers(1, _fFrame, 0)
            EasyGlUtils.genTexturesWithParameter(1, _fTexture, 0, GLES20.GL_RGBA, width, height)
        }
    }

    override fun draw() {
        val a = GLES20.glIsEnabled(GLES20.GL_DEPTH_TEST)
        if (a) {
            GLES20.glDisable(GLES20.GL_DEPTH_TEST)
        }
        if (_surfaceTexture != null) {
            _surfaceTexture!!.updateTexImage()
            _surfaceTexture!!.getTransformMatrix(_coordOM)
            _filter.setCoordMatrix(_coordOM)
        }
        EasyGlUtils.bindFrameTexture(_fFrame[0], _fTexture[0])
        GLES20.glViewport(0, 0, _width, _height)
        _filter.setTextureId(_cameraTexture[0])
        _filter.draw()
        // Log.e("wuwang","textureFilter draw");
        EasyGlUtils.unBindFrameBuffer()
        if (a) {
            GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        }
    }

    private fun deleteFrameBuffer() {
        GLES20.glDeleteFramebuffers(1, _fFrame, 0)
        GLES20.glDeleteTextures(1, _fTexture, 0)
    }

    private fun createOesTexture() {
        GLES20.glGenTextures(1, _cameraTexture, 0)
    }
}