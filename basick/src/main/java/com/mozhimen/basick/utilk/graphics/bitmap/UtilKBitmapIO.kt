package com.mozhimen.basick.utilk.graphics.bitmap

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.datatype.UtilKString
import com.mozhimen.basick.utilk.device.UtilKDate
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import java.io.*

/**
 * @ClassName UtilKBitmapIO
 * @Description Bitmap IO 类
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/20 16:44
 * @Version 1.0
 */
object UtilKBitmapIO {
    private const val TAG = "UtilKBitmapIO>>>>>"
    private val _context = UtilKApplication.instance.get()

    @JvmStatic
    fun filePath2Bitmap(filePathWithName: String): Bitmap? {
        return if (filePathWithName.isEmpty() || UtilKString.isHasSpace(filePathWithName)) null
        else BitmapFactory.decodeFile(filePathWithName)
    }

    /**
     * 位图转文件
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @return File
     */
    @JvmStatic
    fun bitmap2JpegAlbumFile(sourceBitmap: Bitmap, filePathWithName: String, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        return if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
            bitmap2JpegAlbumFileAfter29(sourceBitmap, filePathWithName, quality)
        } else {
            bitmap2JpegAlbumFileBefore29(sourceBitmap, filePathWithName, quality)
        }
    }

    /**
     * 存相册 after 29
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @param quality Int
     * @return String
     */
    fun bitmap2JpegAlbumFileAfter29(sourceBitmap: Bitmap, filePathWithName: String, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        if (TextUtils.isEmpty(filePathWithName)) return null
        var outputStream: OutputStream? = null
        val destFile = UtilKFile.createFile(filePathWithName)
        val pathArray: Array<String> = arrayOf(destFile.absolutePath)
        val typeArray: Array<String> = arrayOf("image/jpeg")
        try {
            val contentValues = ContentValues()
            val contentResolver: ContentResolver = _context.contentResolver
            contentValues.put(MediaStore.Images.ImageColumns.DATA, destFile.absolutePath)
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filePathWithName.split("/").lastOrNull() ?: UtilKDate.getNowString())
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis().toString())
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) // 插入相册
            uri?.let {
                outputStream = contentResolver.openOutputStream(uri)
                sourceBitmap.compress(CompressFormat.JPEG, quality, outputStream)
            }
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream?.flush()
            outputStream?.close()
            try {
                MediaScannerConnection.scanFile(_context, pathArray, typeArray) { path, uri -> Log.d(TAG, "bitmap2Album: path $path, uri $uri") }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        return null
    }

    /**
     * 保存图片 before 29
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @param quality Int
     * @return String
     */
    @JvmStatic
    fun bitmap2JpegAlbumFileBefore29(sourceBitmap: Bitmap, filePathWithName: String, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        if (TextUtils.isEmpty(filePathWithName)) return null
        return bitmap2JpegAlbumFileBefore29(sourceBitmap, File(filePathWithName), quality)
    }

    /**
     * bitmap转文件
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @param quality Int
     * @return String
     */
    @JvmStatic
    fun bitmap2JpegFile(sourceBitmap: Bitmap, filePathWithName: String, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        return bitmap2JpegAlbumFileBefore29(sourceBitmap, filePathWithName, quality)
    }

    /**
     * 保存图片 before 29
     * @param destFile String
     * @param sourceBitmap Bitmap?
     */
    @JvmStatic
    fun bitmap2JpegAlbumFileBefore29(sourceBitmap: Bitmap, destFile: File, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        UtilKFile.createFile(destFile)
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            bufferedOutputStream = BufferedOutputStream(FileOutputStream(destFile))
            sourceBitmap.compress(CompressFormat.JPEG, quality, bufferedOutputStream)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedOutputStream?.flush()
            bufferedOutputStream?.close()
        }
        return null
    }

    /**
     * 从相册获得图片
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun uri2Bitmap(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
                val source = ImageDecoder.createSource(_context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(_context.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
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