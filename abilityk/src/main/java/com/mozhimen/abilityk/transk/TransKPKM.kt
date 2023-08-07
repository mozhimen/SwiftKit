package com.mozhimen.abilityk.transk

import android.opengl.ETC1
import android.opengl.ETC1Util.ETC1Texture
import android.util.Log
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.content.UtilKAsset
import com.mozhimen.basick.utilk.kotlin.UtilKCharSequence
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
    private var _headerBuffer: ByteBuffer? = null

    fun setZipPath(path: String) {
        this._path = path
    }

    fun open(): Boolean {
        if (_path == null && UtilKCharSequence.isNullOrEmpty(_path)) {
            _zipInputStream = null
            _zipEntry = null
            return false
        }
        return try {
            _zipInputStream = if (_path!!.startsWith("assets/")) {
                val inputStream = UtilKAsset.open(_path!!.substring(7))
                ZipInputStream(inputStream)
            } else {
                ZipInputStream(FileInputStream(_path))
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
        if (_headerBuffer != null) {
            _headerBuffer!!.clear()
            _headerBuffer = null
        }
    }

    fun getNextStream(): InputStream? {
        return if (hasElements()) {
            _zipInputStream
        } else null
    }

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

    private fun hasElements(): Boolean {
        try {
            if (_zipInputStream != null) {
                _zipEntry = _zipInputStream!!.nextEntry
                if (_zipEntry != null) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return false
    }

    @Throws(IOException::class)
    private fun createTexture(inputStream: InputStream): ETC1Texture {
        var width = 0
        var height = 0
        val bytes = ByteArray(4096)
        run {
            if (inputStream.read(bytes, 0, ETC1.ETC_PKM_HEADER_SIZE) != ETC1.ETC_PKM_HEADER_SIZE) {
                Log.e(TAG, bytes.contentToString())
                throw IOException("Unable to read PKM file header.")
            }
            if (_headerBuffer == null) {
                _headerBuffer = ByteBuffer.allocateDirect(ETC1.ETC_PKM_HEADER_SIZE)
                    .order(ByteOrder.nativeOrder())
            }
            _headerBuffer!!.put(bytes, 0, ETC1.ETC_PKM_HEADER_SIZE).position(0)
            if (!ETC1.isValid(_headerBuffer)) {
                throw IOException("Not a PKM file.")
            }
            width = ETC1.getWidth(_headerBuffer)
            height = ETC1.getHeight(_headerBuffer)
        }
        val encodedSize = ETC1.getEncodedDataSize(width, height)
        val dataBuffer = ByteBuffer.allocateDirect(encodedSize).order(ByteOrder.nativeOrder())
        var readCount: Int
        while (inputStream.read(bytes).also { readCount = it } != -1) {
            dataBuffer.put(bytes, 0, readCount)
        }
        dataBuffer.position(0)
        return ETC1Texture(width, height, dataBuffer)
    }
}