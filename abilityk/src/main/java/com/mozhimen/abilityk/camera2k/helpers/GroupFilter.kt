package com.mozhimen.abilityk.camera2k.helpers

import android.opengl.GLES20
import com.mozhimen.abilityk.camera2k.commons.AFilter
import com.mozhimen.basick.utilk.UtilKMatrix
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * @ClassName GroupFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/27 17:28
 * @Version 1.0
 */
class GroupFilter : AFilter() {
    private var _filterQueue: Queue<AFilter> = ConcurrentLinkedQueue()
    private var _filters: MutableList<AFilter> = ArrayList()
    private var _width = 0
    private var _height: Int = 0
    private var _size = 0

    fun addFilter(filter: AFilter) {
        //绘制到frameBuffer上和绘制到屏幕上的纹理坐标是不一样的
        //Android屏幕相对GL世界的纹理Y轴翻转
        UtilKMatrix.flipMatrix(filter.getMatrix(), isFlipX = false, isFlipY = true)
        _filterQueue.add(filter)
    }

    fun removeFilter(filter: AFilter): Boolean {
        val b = _filters.remove(filter)
        if (b) _size--
        return b
    }

    fun removeFilter(index: Int): AFilter {
        val f = _filters.removeAt(index)
        _size--
        return f
    }

    fun clearAll() {
        _filterQueue.clear()
        _filters.clear()
        _size = 0
    }

    override fun onFilterCreate() {

    }

    override fun onFilterSizeChanged(width: Int, height: Int) {
        _width = width
        _height = height
        updateFilter()
        createFrameBuffer()
    }

    override fun draw() {
        updateFilter()
        textureIndex = 0
        if (_size > 0) {
            for (i in _filters.indices) {
                val filter = _filters[i]
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fFrame[0])
                GLES20.glFramebufferTexture2D(
                    GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                    GLES20.GL_TEXTURE_2D, fTexture[textureIndex % 2], 0
                )
                GLES20.glFramebufferRenderbuffer(
                    GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                    GLES20.GL_RENDERBUFFER, fRender[0]
                )
                GLES20.glViewport(0, 0, _width, _height)
                if (textureIndex == 0) {
                    filter.setTextureId(getTextureId())
                } else {
                    filter.setTextureId(fTexture[(textureIndex - 1) % 2])
                }
                filter.draw()
                unBindFrame()
                textureIndex++
            }
        }
    }

    private fun updateFilter() {
        var aFilter: AFilter
        while (_filterQueue.poll().also { f -> aFilter = f!! } != null) {
            aFilter.create()
            aFilter.changeSize(_width, _height)
            _filters.add(aFilter)
            _size++
        }
    }

    override fun getOutputTexture(): Int {
        return if (_size == 0) getTextureId() else fTexture[(textureIndex - 1) % 2]
    }

    //创建离屏buffer
    private val fTextureSize = 2
    private val fFrame = IntArray(1)
    private val fRender = IntArray(1)
    private val fTexture = IntArray(fTextureSize)
    private var textureIndex = 0

    //创建FrameBuffer
    private fun createFrameBuffer(): Boolean {
        GLES20.glGenFramebuffers(1, fFrame, 0)
        GLES20.glGenRenderbuffers(1, fRender, 0)
        genTextures()
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fFrame[0])
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, fRender[0])
        GLES20.glRenderbufferStorage(
            GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, _width,
            _height
        )
        GLES20.glFramebufferTexture2D(
            GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
            GLES20.GL_TEXTURE_2D, fTexture[0], 0
        )
        GLES20.glFramebufferRenderbuffer(
            GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
            GLES20.GL_RENDERBUFFER, fRender[0]
        )
        unBindFrame()
        return false
    }

    //生成Textures
    private fun genTextures() {
        GLES20.glGenTextures(fTextureSize, fTexture, 0)
        for (i in 0 until fTextureSize) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fTexture[i])
            GLES20.glTexImage2D(
                GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, _width, _height,
                0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null
            )
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        }
    }

    //取消绑定Texture
    private fun unBindFrame() {
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
    }


    /*private fun deleteFrameBuffer() {
        GLES20.glDeleteRenderbuffers(1, fRender, 0)
        GLES20.glDeleteFramebuffers(1, fFrame, 0)
        GLES20.glDeleteTextures(1, fTexture, 0)
    }*/
}