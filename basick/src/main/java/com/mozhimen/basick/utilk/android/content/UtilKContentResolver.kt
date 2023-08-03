package com.mozhimen.basick.utilk.android.content

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapCompress
import com.mozhimen.basick.utilk.android.media.UtilKMediaScannerConnection
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * @ClassName UtilKContentResolver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/31 18:12
 * @Version 1.0
 */
@RequiresApi(CVersCode.V_29_10_Q)
@RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
fun Bitmap.anyBitmap2imageFile(filePathWithName: String, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        UtilKContentResolver.anyBitmap2imageFile(this, filePathWithName, compressFormat, quality)

object UtilKContentResolver : BaseUtilK() {
    @JvmStatic
    fun get(context: Context): ContentResolver =
            UtilKContext.getContentResolver(context)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun query(context: Context, @RequiresPermission.Read uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? =
            get(context).query(uri, projection, selection, selectionArgs, sortOrder)

    @JvmStatic
    fun openInputStream(context: Context, uri: Uri): InputStream? =
            get(context).openInputStream(uri)

    @JvmStatic
    fun openOutputStream(context: Context, uri: Uri): OutputStream? =
            get(context).openOutputStream(uri)

    @JvmStatic
    fun delete(context: Context, @RequiresPermission.Write uri: Uri, where: String?, selectionArgs: Array<String>?) {
        get(context).delete(uri, where, selectionArgs)
    }

    @JvmStatic
    fun insert(context: Context, @RequiresPermission.Write uri: Uri, values: ContentValues?): Uri? =
            get(context).insert(uri, values)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
    fun anyBitmap2imageFile(sourceBitmap: Bitmap, filePathWithName: String, compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? {
        var outputStream: OutputStream? = null
        val destFile = UtilKFile.createFile(filePathWithName)
        try {
            val contentValues = ContentValues().apply {
                put(CMediaStore.Images.ImageColumns.DATA, destFile.absolutePath)
                put(CMediaStore.Images.ImageColumns.DISPLAY_NAME, filePathWithName.split("/").lastOrNull() ?: UtilKFile.getStrFileNameForStrNowDate())
                put(CMediaStore.Images.ImageColumns.MIME_TYPE, CMediaFormat.MIMETYPE_IMAGE_JPEG)
                put(CMediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis().toString())
            }
            insert(_context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {
                outputStream = openOutputStream(_context, it)
                sourceBitmap.applyAnyBitmapCompress(compressFormat, quality, outputStream!!)
                return destFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream?.flush()
            outputStream?.close()
            try {
                UtilKMediaScannerConnection.scanFile(_context, arrayOf(destFile.absolutePath), arrayOf(CMediaFormat.MIMETYPE_IMAGE_JPEG)) { path, uri ->
                    "bitmap2Album: path $path, uri $uri".dt(TAG)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        return null
    }

    @JvmStatic
    fun deleteImageFile(filePathWithName: String) {
        delete(_context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, "${CMediaStore.Images.Media.DATA}='${filePathWithName}'", null)
    }
}