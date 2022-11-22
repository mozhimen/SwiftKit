package com.mozhimen.basick.utilk.bitmap

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.mozhimen.basick.utilk.UtilKFile
import com.mozhimen.basick.utilk.context.UtilKApplication
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * @ClassName UtilKBitmapIO
 * @Description Bitmap IO 类
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/20 16:44
 * @Version 1.0
 */
object UtilKBitmapIO {
    private val TAG = "UtilKBitmapIO>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * 位图转文件
     * @param bitmap Bitmap
     * @param destFileName String
     * @return File
     */
    @JvmStatic
    fun bitmap2Album(bitmap: Bitmap, destFileName: String, quality: Int = 80): String {
        var tmpDestFilePath = _context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/${destFileName}"
        if (!tmpDestFilePath.endsWith(".jpg")) {
            tmpDestFilePath += ".jpg"
        }
        Log.d(TAG, "bitmap2Album: tmpDestFilePath $tmpDestFilePath")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            var outputStream: OutputStream? = null
            val destFile = UtilKFile.createFile(tmpDestFilePath)
            val pathArray: Array<String> = arrayOf(destFile.absolutePath)
            val typeArray: Array<String> = arrayOf("image/jpeg")
            try {
                val values = ContentValues()
                val resolver: ContentResolver = _context.contentResolver
                values.put(MediaStore.Images.ImageColumns.DATA, destFile.absolutePath)
                values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, destFileName)
                values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis().toString())
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) // 插入相册
                uri?.let {
                    outputStream = resolver.openOutputStream(uri)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                }
                return tmpDestFilePath
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                outputStream?.apply {
                    flush()
                    close()
                    try {
                        MediaScannerConnection.scanFile(_context, pathArray, typeArray) { path, uri ->
                            Log.d(TAG, "bitmap2Album: path $path, uri $uri")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            return bitmap2File(bitmap, tmpDestFilePath)
        }
        return UtilKFile.msg_wrong
    }

    @JvmStatic
    fun bitmap2File(bitmap: Bitmap, filePathWithName: String, quality: Int = 80, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG): String =
        bitmap2File(bitmap, File(filePathWithName), quality, compressFormat)

    /**
     * 保存图片
     * @param destFile String
     * @param bitmap Bitmap?
     */
    @JvmStatic
    fun bitmap2File(bitmap: Bitmap, destFile: File, quality: Int = 80, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG): String {
        UtilKFile.createFile(destFile)
        val bufferedOutputStream = BufferedOutputStream(FileOutputStream(destFile))
        try {
            bitmap.compress(compressFormat, quality, bufferedOutputStream)
            return destFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bufferedOutputStream.flush()
            bufferedOutputStream.close()
        }
        return UtilKFile.msg_wrong
    }

    /**
     * 从相册获得图片
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun album2Bitmap(uri: Uri): Bitmap? {
        try {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(_context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(_context.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 删除图片
     * @param deleteFilePath String
     */
    @JvmStatic
    fun deleteBitmapFromAlbum(deleteFilePath: String) {
        _context.contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${MediaStore.Images.Media.DATA}='${deleteFilePath}'", null)
    }
}