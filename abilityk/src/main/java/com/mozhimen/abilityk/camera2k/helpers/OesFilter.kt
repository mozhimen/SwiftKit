package com.mozhimen.abilityk.camera2k.helpers

import android.opengl.GLES11Ext
import android.opengl.GLES20
import com.mozhimen.abilityk.camera2k.commons.BaseFilter

/**
 * @ClassName OesFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 14:26
 * @Version 1.0
 */
open class OesFilter : BaseFilter() {
    private var _hCoordMatrix = 0
    private var _coordMatrix: FloatArray = originalMatrix.copyOf(16)

    fun setCoordMatrix(matrix: FloatArray) {
        _coordMatrix = matrix
    }

    override fun onFilterCreate() {
        createProgramByAssetsFile("shader/oes_base_vertex.sh", "shader/oes_base_fragment.sh")
        _hCoordMatrix = GLES20.glGetUniformLocation(program, "vCoordMatrix")
    }

    override fun onFilterSizeChanged(width: Int, height: Int) {}

    override fun onSetExpandData() {
        super.onSetExpandData()
        GLES20.glUniformMatrix4fv(_hCoordMatrix, 1, false, _coordMatrix, 0)
    }

    override fun onBindTexture() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + getTextureType())
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, getTextureId())
        GLES20.glUniform1i(hTexture, getTextureType())
    }
}