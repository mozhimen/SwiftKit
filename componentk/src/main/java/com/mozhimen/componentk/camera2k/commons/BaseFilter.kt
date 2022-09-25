package com.mozhimen.componentk.camera2k.commons

import android.opengl.GLES20
import android.util.SparseArray
import com.mozhimen.basick.utilk.UtilKAssets
import com.mozhimen.basick.utilk.UtilKGL
import com.mozhimen.basick.utilk.UtilKMatrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * @ClassName BaseFilter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/27 15:52
 * @Version 1.0
 */
abstract class BaseFilter {

    companion object {
//        private const val TAG = "BaseFilter>>>>>"
//
//        const val BASEFILTER_KEY_OUT = 0x101
//        const val BASEFILTER_KEY_IN = 0x102
//        const val BASEFILTER_KEY_INDEX = 0x201
    }

    private var verPos = floatArrayOf(-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f)//顶点坐标
    open var coordPos = floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f)//纹理坐标

    protected val originalMatrix: FloatArray = UtilKMatrix.getOriginalMatrix()//单位矩阵
    protected var program = 0//程序句柄
    private var _hPosition = 0//顶点坐标句柄
    private var _hCoord = 0//纹理坐标句柄
    private var _hMatrix = 0//总变换矩阵句柄
    protected var hTexture = 0//默认纹理贴图句柄

    private var _verBuffer: FloatBuffer? = null//顶点坐标Buffer
    protected var texBuffer: FloatBuffer? = null//纹理坐标Buffer

    //protected var _minDexBuffer: ShortBuffer? = null//索引坐标Buffer
    private var _filterFlag = 0

    private var _matrix = originalMatrix.copyOf(16)
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

    open fun setMatrix(matrix: FloatArray) {
        _matrix = matrix
    }

    fun setTextureId(textureId: Int) {
        _textureId = textureId
    }

    open fun setFilterFlag(filterFlag: Int) {
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

    /**
     * 实现此方法，完成程序的创建，可直接调用createProgram来实现
     */
    protected abstract fun onFilterCreate()

    protected abstract fun onFilterSizeChanged(width: Int, height: Int)

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

    open fun getMatrix(): FloatArray {
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

    open fun getOutputTexture(): Int = -1

    protected open fun createProgramByAssetsFile(vertex: String, fragment: String) {
        createProgram(
            UtilKAssets.txt2String2(vertex),
            UtilKAssets.txt2String2(fragment)
        )
    }

    protected open fun createProgram(vertex: String, fragment: String) {
        program = UtilKGL.createGlProgram(vertex, fragment)
        _hPosition = GLES20.glGetAttribLocation(program, "vPosition")
        _hCoord = GLES20.glGetAttribLocation(program, "vCoord")
        _hMatrix = GLES20.glGetUniformLocation(program, "vMatrix")
        hTexture = GLES20.glGetUniformLocation(program, "vTexture")
    }

    protected open fun onUseProgram() {
        GLES20.glUseProgram(program)
    }

    /**
     * 启用顶点坐标和纹理坐标进行绘制
     */
    protected open fun onDraw() {
        GLES20.glEnableVertexAttribArray(_hPosition)
        GLES20.glVertexAttribPointer(_hPosition, 2, GLES20.GL_FLOAT, false, 0, _verBuffer)
        GLES20.glEnableVertexAttribArray(_hCoord)
        GLES20.glVertexAttribPointer(_hCoord, 2, GLES20.GL_FLOAT, false, 0, texBuffer)
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
        GLES20.glUniform1i(hTexture, _textureType)
    }

    /**
     * Buffer初始化
     */
    protected open fun initBuffer() {
        val a = ByteBuffer.allocateDirect(32)
        a.order(ByteOrder.nativeOrder())
        _verBuffer = a.asFloatBuffer()
        _verBuffer!!.put(verPos)
        _verBuffer!!.position(0)
        val b = ByteBuffer.allocateDirect(32)
        b.order(ByteOrder.nativeOrder())
        texBuffer = b.asFloatBuffer()
        texBuffer!!.put(coordPos)
        texBuffer!!.position(0)
    }
}