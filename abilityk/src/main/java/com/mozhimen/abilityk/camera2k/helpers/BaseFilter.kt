package com.mozhimen.abilityk.camera2k.helpers

import android.content.res.Resources
import android.opengl.GLES20
import android.util.SparseArray
import com.mozhimen.basick.utilk.UtilKAssets
import com.mozhimen.basick.utilk.UtilKGL
import com.mozhimen.basick.utilk.UtilKMatrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import java.util.*

/**
 * @ClassName BaseFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/16 13:33
 * @Version 1.0
 */
abstract class BaseFilter(res: Resources) {
    private val TAG = "BaseFilter"

    val BASEFILTER_KEY_OUT = 0x101
    val BASEFILTER_KEY_IN = 0x102
    val BASEFILTER_KEY_INDEX = 0x201

    var verPos = floatArrayOf(-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f)//顶点坐标
    var coordPos = floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f)//纹理坐标

    protected var filterResources: Resources = res

    protected val _originalMatrix: FloatArray = UtilKMatrix.getOriginalMatrix()//单位矩阵
    protected var _program = 0//程序句柄
    protected var _hPosition = 0//顶点坐标句柄
    protected var _hCoord = 0//纹理坐标句柄
    protected var _hMatrix = 0//总变换矩阵句柄
    protected var _hTexture = 0//默认纹理贴图句柄

    protected var _verBuffer: FloatBuffer? = null//顶点坐标Buffer
    protected var _texBuffer: FloatBuffer? = null//纹理坐标Buffer
    protected var _minDexBuffer: ShortBuffer? = null//索引坐标Buffer
    protected var _filterFlag = 0

    private var _matrix = Arrays.copyOf(_originalMatrix, 16)
    private var _textureType = 0 //默认使用Texture2D0
    private var _textureId = 0
    private var _bools: SparseArray<BooleanArray>? = null
    private var _ints: SparseArray<IntArray>? = null
    private var _floats: SparseArray<FloatArray>? = null

    init {
        initBuffer()
    }

    fun setTextureType(type: Int) {
        _textureType = type
    }

    fun setMatrix(matrix: FloatArray) {
        _matrix = matrix
    }

    fun setTextureId(textureId: Int) {
        _textureId = textureId
    }

    fun setFilterFlag(filterFlag: Int) {
        _filterFlag = filterFlag
    }

    fun setFloat(type: Int, vararg params: Float) {
        if (_floats == null) {
            _floats = SparseArray()
        }
        _floats!!.put(type, params)
    }

    fun setInt(type: Int, vararg params: Int) {
        if (_ints == null) {
            _ints = SparseArray()
        }
        _ints!!.put(type, params)
    }

    fun setBool(type: Int, vararg params: Boolean) {
        if (_bools == null) {
            _bools = SparseArray()
        }
        _bools!!.put(type, params)
    }

    open fun create() {
        onFilterCreate()
    }

    open fun changeSize(width: Int, height: Int) {
        onFilterSizeChanged(width, height)
    }

    open fun draw() {
        onClear()
        onUseProgram()
        onSetExpandData()
        onBindTexture()
        onDraw()
    }

    open fun getMatrix(): FloatArray? {
        return _matrix
    }

    fun getTextureType(): Int {
        return _textureType
    }

    fun getTextureId(): Int {
        return _textureId
    }

    fun getFlag(): Int {
        return _filterFlag
    }

    fun getBool(type: Int, index: Int): Boolean {
        if (_bools == null) return false
        val b = _bools!![type]
        return !(b == null || b.size <= index) && b[index]
    }

    fun getInt(type: Int, index: Int): Int {
        if (_ints == null) return 0
        val b = _ints!![type]
        return if (b == null || b.size <= index) {
            0
        } else b[index]
    }

    fun getFloat(type: Int, index: Int): Float {
        if (_floats == null) return 0f
        val b = _floats!![type]
        return if (b == null || b.size <= index) 0f else b[index]
    }

    fun getOutputTexture(): Int = -1

    /**
     * 实现此方法，完成程序的创建，可直接调用createProgram来实现
     */
    protected abstract fun onFilterCreate()

    protected abstract fun onFilterSizeChanged(width: Int, height: Int)

    protected open fun createProgramByAssetsFile(vertex: String, fragment: String) {
        createProgram(
            UtilKAssets.txt2String(filterResources, vertex),
            UtilKAssets.txt2String(filterResources, fragment)
        )
    }

    protected open fun createProgram(vertex: String, fragment: String) {
        _program = UtilKGL.createGlProgram(vertex, fragment)
        _hPosition = GLES20.glGetAttribLocation(_program, "vPosition")
        _hCoord = GLES20.glGetAttribLocation(_program, "vCoord")
        _hMatrix = GLES20.glGetUniformLocation(_program, "vMatrix")
        _hTexture = GLES20.glGetUniformLocation(_program, "vTexture")
    }

    protected open fun onUseProgram() {
        GLES20.glUseProgram(_program)
    }

    /**
     * 启用顶点坐标和纹理坐标进行绘制
     */
    protected open fun onDraw() {
        GLES20.glEnableVertexAttribArray(_hPosition)
        GLES20.glVertexAttribPointer(_hPosition, 2, GLES20.GL_FLOAT, false, 0, _verBuffer)
        GLES20.glEnableVertexAttribArray(_hCoord)
        GLES20.glVertexAttribPointer(_hCoord, 2, GLES20.GL_FLOAT, false, 0, _texBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(_hPosition)
        GLES20.glDisableVertexAttribArray(_hCoord)
    }

    /**
     * 清除画布
     */
    protected open fun onClear() {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
    }

    /**
     * 设置其他扩展数据
     */
    protected open fun onSetExpandData() {
        GLES20.glUniformMatrix4fv(_hMatrix, 1, false, _matrix, 0)
    }

    /**
     * 绑定默认纹理
     */
    protected open fun onBindTexture() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + _textureType)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getTextureId())
        GLES20.glUniform1i(_hTexture, _textureType)
    }

    /**
     * Buffer初始化
     */
    private fun initBuffer() {
        val a = ByteBuffer.allocateDirect(32)
        a.order(ByteOrder.nativeOrder())
        _verBuffer = a.asFloatBuffer()
        _verBuffer!!.put(verPos)
        _verBuffer!!.position(0)
        val b = ByteBuffer.allocateDirect(32)
        b.order(ByteOrder.nativeOrder())
        _texBuffer = b.asFloatBuffer()
        _texBuffer!!.put(coordPos)
        _texBuffer!!.position(0)
    }
}