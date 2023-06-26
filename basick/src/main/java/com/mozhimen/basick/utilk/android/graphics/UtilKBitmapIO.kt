package com.mozhimen.basick.utilk.android.graphics

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import coil.request.ImageRequest
import com.mozhimen.basick.elemk.cons.CMediaFormat
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.kotlin.UtilKString
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import java.io.*
import java.net.URL

/**
 * @ClassName UtilKBitmapIO
 * @Description Bitmap IO 类
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/20 16:44
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET)
object UtilKBitmapIO : BaseUtilK() {

    /**
     * 协程方式 获取Bitmap
     * @param url String
     * @return Bitmap
     */
    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    suspend fun url2BitmapCoroutine(url: String): Bitmap? {
        return (UtilKContext.getImageLoader(_context).execute(ImageRequest.Builder(_context).data(url).build()).drawable as? BitmapDrawable)?.bitmap
    }

    /**
     * 获取Bitmap
     * @param url String
     * @return Bitmap?
     */
    @JvmStatic
    @RequiresPermission(CPermission.INTERNET)
    fun url2Bitmap(url: String): Bitmap? {
        val tempURL = URL(url)
        var inputStream: InputStream? = null
        return try {
            inputStream = tempURL.openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }

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
            if (ActivityCompat.checkSelfPermission(_context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null
            }
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
    @JvmStatic
    @RequiresApi(CVersionCode.V_29_10_Q)
    @RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
    fun bitmap2JpegAlbumFileAfter29(sourceBitmap: Bitmap, filePathWithName: String, @androidx.annotation.IntRange(from = 0, to = 100) quality: Int = 100): File? {
        if (TextUtils.isEmpty(filePathWithName)) return null
        var outputStream: OutputStream? = null
        val destFile = UtilKFile.createFile(filePathWithName)
        try {
            val contentValues = ContentValues()
            val contentResolver: ContentResolver = UtilKContext.getContentResolver(_context)
            contentValues.put(MediaStore.Images.ImageColumns.DATA, destFile.absolutePath)
            contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, filePathWithName.split("/").lastOrNull() ?: UtilKFile.nowStr2FileName())
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, CMediaFormat.MIMETYPE_IMAGE_JPEG)
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
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(UtilKContext.getContentResolver(_context), uri))
            } else {
                MediaStore.Images.Media.getBitmap(UtilKContext.getContentResolver(_context), uri)
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
        UtilKContext.getContentResolver(_context).delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${MediaStore.Images.Media.DATA}='${deleteFilePath}'", null)
    }
}