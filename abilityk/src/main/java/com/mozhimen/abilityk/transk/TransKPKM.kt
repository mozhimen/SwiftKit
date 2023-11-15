package com.mozhimen.abilityk.transk

import android.opengl.ETC1Util.ETC1Texture
import android.util.Log
import com.mozhimen.basick.elemk.android.opengl.cons.CETC1
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.content.UtilKAssetManager
import com.mozhimen.basick.utilk.android.opengl.UtilKETC1
import com.mozhimen.basick.utilk.kotlin.UtilKCharSequence
import com.mozhimen.basick.utilk.kotlin.strFilePath2fileInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * @ClassName ZipKPkmReader
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/28 23:26
 * @Version 1.0
 */
class TransKPKM : BaseUtilK() {
    private var _path: String? = null
    private var _zipInputStream: ZipInputStream? = null
    private var _zipEntry: ZipEntry? = null
    private var _pkmByteBuffer: ByteBuffer? = null

    fun setZipPath(path: String) {
        _path = path
    }

    fun open(): Boolean {
        if (_path == null && UtilKCharSequence.isNullOrEmpty(_path)) {
            _zipInputStream = null
            _zipEntry = null
            return false
        }
        return try {
            _zipInputStream = if (_path!!.startsWith("assets/")) {
                val inputStream = UtilKAssetManager.open(_path!!.substring(7), _context)
                ZipInputStream(inputStream)
            } else {
                ZipInputStream(_path!!.strFilePath2fileInputStream())
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false
        }
    }

    fun close() {
        if (_zipInputStream != null) {
            try {
                _zipInputStream!!.closeEntry()
                _zipInputStream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            } finally {
                _zipInputStream = null
                _zipEntry = null
            }
        }
        if (_pkmByteBuffer != null) {
            _pkmByteBuffer!!.clear()
            _pkmByteBuffer = null
        }
    }

    fun getNextStream(): InputStream? =
        if (hasElements()) _zipInputStream else null

    fun getNextTexture(): ETC1Texture? {
        if (hasElements()) {
            try {
                return _zipInputStream?.let { createTexture(it) }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        return null
    }

    ///////////////////////////////////////////////////////////////////////////////

    private fun hasElements(): Boolean {
        try {
            if (_zipInputStream != null) {
                _zipEntry = _zipInputStream!!.nextEntry
                if (_zipEntry != null)
                    return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return false
    }

    @Throws(IOException::class)
    private fun createTexture(inputStream: InputStream): ETC1Texture {
        val bytes = ByteArray(4096)
        if (inputStream.read(bytes, 0, CETC1.ETC_PKM_HEADER_SIZE) != CETC1.ETC_PKM_HEADER_SIZE) {
            Log.e(TAG, bytes.contentToString())
            throw IOException("Unable to read PKM file header.")
        }
        if (_pkmByteBuffer == null)
            _pkmByteBuffer = ByteBuffer.allocateDirect(CETC1.ETC_PKM_HEADER_SIZE).order(ByteOrder.nativeOrder())

        _pkmByteBuffer!!.put(bytes, 0, CETC1.ETC_PKM_HEADER_SIZE).position(0)
        if (UtilKETC1.isValid(_pkmByteBuffer!!))
            throw IOException("Not a PKM file.")

        val width = UtilKETC1.getWidth(_pkmByteBuffer!!)
        val height = UtilKETC1.getHeight(_pkmByteBuffer!!)
        val encodedSize = UtilKETC1.getEncodedDataSize(width, height)
        val byteBuffer = ByteBuffer.allocateDirect(encodedSize).order(ByteOrder.nativeOrder())
        var readCount: Int
        while (inputStream.read(bytes).also { readCount = it } != -1) {
            byteBuffer.put(bytes, 0, readCount)
        }
        byteBuffer.position(0)
        return ETC1Texture(width, height, byteBuffer)
    }
}