package com.mozhimen.basick.utilk.android.graphics

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toDrawable
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.UtilKFile
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 * @ClassName UtilKBitmapFormat
 * @Description Bitmap bytes 流转换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */

fun Bitmap.asJpegBytes(): ByteArray? =
    UtilKBitmapFormat.bitmap2jpegBytes(this)

fun Bitmap.asRgb565Bitmap(): Bitmap =
    UtilKBitmapFormat.bitmap2rgb565Bitmap(this)

object UtilKBitmapFormat : BaseUtilK() {

    @JvmStatic
    fun bitmap2rgb565Bitmap(sourceBitmap: Bitmap): Bitmap {
        return sourceBitmap.copy(Bitmap.Config.RGB_565, true)
    }

    @JvmStatic
    fun bitmap2bytes(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray? {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream(sourceBitmap.width * sourceBitmap.height * 4)
            sourceBitmap.compress(compressFormat, quality, byteArrayOutputStream)            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            return byteArrayOutputStream.toByteArray()            //将字节数组输出流转化为字节数组byte[]
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            byteArrayOutputStream?.flush()
            byteArrayOutputStream?.close()
        }
        return null
    }

    @JvmStatic
    fun bitmap2jpegBytes(sourceBitmap: Bitmap, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray? =
        bitmap2bytes(sourceBitmap, CompressFormat.JPEG, quality)

    @JvmStatic
    fun bitmap2drawable(sourceBitmap: Bitmap): Drawable =
        sourceBitmap.toDrawable(UtilKResource.getSystemResources())

    @JvmStatic
    fun bitmap2drawable2(sourceBitmap: Bitmap): Drawable =
        BitmapDrawable(UtilKResource.getAppResources(_context), sourceBitmap)

    @JvmStatic
    fun bitmap2jpegAlbumFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) null
            else bitmap2albumFileAfter29(sourceBitmap, filePathWithName, CompressFormat.JPEG, quality)
        } else {
            bitmap2albumFileBefore29(sourceBitmap, filePathWithName, CompressFormat.JPEG, quality)
        }

    /**
     * 存相册 after 29
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @param quality Int
     * @return String
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
    fun bitmap2albumFileAfter29(
        sourceBitmap: Bitmap,
        filePathWithName: String,
        compressFormat: CompressFormat = CompressFormat.JPEG,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? {
        if (TextUtils.isEmpty(filePathWithName)) return null
        var outputStream: OutputStream? = null
        val destFile = UtilKFile.createFile(filePathWithName)
        try {
            val contentValues = ContentValues()
            val contentResolver: ContentResolver = UtilKContentResolver.get(_context)
            contentValues.put(MediaStore.Images.ImageColumns.DATA, destFile.absolutePath)
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filePathWithName.split("/").lastOrNull() ?: UtilKFile.nowStr2fileName())
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, CMediaFormat.MIMETYPE_IMAGE_JPEG)
            contentValues.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis().toString())
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) // 插入相册
            uri?.let {
                outputStream = contentResolver.openOutputStream(uri)
                sourceBitmap.compress(compressFormat, quality, outputStream)
            }
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream?.flush()
            outputStream?.close()
            try {
                val pathArray: Array<String> = arrayOf(destFile.absolutePath)
                val typeArray: Array<String> = arrayOf(CMediaFormat.MIMETYPE_IMAGE_JPEG)
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
    fun bitmap2albumFileBefore29(
        sourceBitmap: Bitmap,
        filePathWithName: String,
        compressFormat: CompressFormat = CompressFormat.JPEG,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? =
        bitmap2albumFileBefore29(sourceBitmap, File(filePathWithName), compressFormat, quality)

    /**
     * 保存图片 before 29
     * @param destFile String
     * @param sourceBitmap Bitmap?
     */
    @JvmStatic
    fun bitmap2albumFileBefore29(
        sourceBitmap: Bitmap,
        destFile: File,
        compressFormat: CompressFormat = CompressFormat.JPEG,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? {
        UtilKFile.createFile(destFile)
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            bufferedOutputStream = BufferedOutputStream(FileOutputStream(destFile))
            sourceBitmap.compress(compressFormat, quality, bufferedOutputStream)
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
     * bitmap转文件
     * @param sourceBitmap Bitmap
     * @param filePathWithName String
     * @param quality Int
     * @return String
     */
    @JvmStatic
    fun bitmap2jpegFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bitmap2albumFileBefore29(sourceBitmap, filePathWithName, CompressFormat.JPEG, quality)

    @JvmStatic
    fun bitmap2pngFile(sourceBitmap: Bitmap, filePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bitmap2albumFileBefore29(sourceBitmap, filePathWithName, CompressFormat.PNG, quality)


}