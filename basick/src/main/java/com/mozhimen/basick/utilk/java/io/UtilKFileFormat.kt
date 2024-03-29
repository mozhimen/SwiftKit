package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.android.content.UtilKContentResolverWrapper
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.androidx.core.UtilKFileProvider
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @ClassName UtilKFileFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 16:42
 * @Version 1.0
 */
fun File.file2uri_ofImage(): Uri? =
    UtilKFileFormat.file2uri_ofImage(this)

fun File.file2uri(): Uri? =
    UtilKFileFormat.file2uri(this)

fun File.file2bitmapAny(): Bitmap? =
    UtilKFileFormat.file2bitmapAny(this)

fun File.file2bitmapAny(opts: BitmapFactory.Options): Bitmap? =
    UtilKFileFormat.file2bitmapAny(this, opts)

fun File.file2strFilePath(): String =
    UtilKFileFormat.file2strFilePath(this)

////////////////////////////////////////////////////////////////////////////////////////

fun File.file2fileInputStream(): FileInputStream =
    UtilKFileFormat.file2fileInputStream(this)

fun File.file2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKFileFormat.file2fileOutputStream(this, isAppend)

fun File.file2bufferedOutputStream(isAppend: Boolean = false): BufferedOutputStream =
    UtilKFileFormat.file2bufferedOutputStream(this, isAppend)

////////////////////////////////////////////////////////////////////////////////////////

fun File.file2str_use(): String? =
    UtilKFileFormat.file2str_use(this)

fun File.file2strMd5(): String? =
    UtilKFileFormat.file2strMd5(this)

////////////////////////////////////////////////////////////////////////////////////////

fun File.file2bytes(): ByteArray? =
    UtilKFileFormat.file2bytes(this)

fun File.file2bytes2(): ByteArray? =
    UtilKFileFormat.file2bytes2(this)

////////////////////////////////////////////////////////////////////////////////////////

object UtilKFileFormat : BaseUtilK() {
    @JvmStatic
    fun file2uri_ofImage(file: File): Uri? {
        if (!UtilKFileWrapper.isFileExist(file)) {
            UtilKLogWrapper.e(TAG, "file2imageUri: file isFileExist false")
            return null
        }
        return if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            UtilKContentResolverWrapper.insertImage_after29(_context, file)
        } else file2uri(file)
    }

    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun file2uri(file: File): Uri? {
        if (!UtilKFileWrapper.isFileExist(file)) {
            UtilKLogWrapper.e(TAG, "file2Uri: file ${file.absolutePath} isFileExist false")
            return null
        }
        return if (UtilKBuildVersion.isAfterV_24_7_N()) {
            val authority = "${UtilKPackage.getPackageName()}.fileProvider".also { UtilKLogWrapper.d(TAG, "file2Uri: authority $it") }
            UtilKFileProvider.getUriForFile(_context, authority, file).also {
                UtilKContext.grantUriPermission(_context, it, CIntent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else Uri.fromFile(file)
    }

    @JvmStatic
    fun file2bitmapAny(file: File): Bitmap? =
        UtilKStrFile.strFilePath2bitmapAny(file.file2strFilePath())

    @JvmStatic
    fun file2bitmapAny(file: File, opts: BitmapFactory.Options): Bitmap? =
        UtilKStrFile.strFilePath2bitmapAny(file.file2strFilePath(), opts)

    @JvmStatic
    fun file2strFilePath(file: File): String =
        UtilKFile.getAbsolutePath(file)

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun file2fileInputStream(file: File): FileInputStream =
        UtilKFileInputStream.get(file)

    @JvmStatic
    fun file2fileOutputStream(file: File, isAppend: Boolean = false): FileOutputStream =
        UtilKFileOutputStream.get(file, isAppend)

    @JvmStatic
    fun file2bufferedOutputStream(file: File, isAppend: Boolean = false): BufferedOutputStream =
        file2fileOutputStream(file, isAppend).outputStream2bufferedOutputStream()

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun file2str_use(file: File): String? =
        if (!UtilKFileWrapper.isFileExist(file)) null
        else file.file2fileInputStream().inputStream2str_use_ofBufferedReader()

    @JvmStatic
    fun file2strMd5(file: File): String? =
        if (!UtilKFileWrapper.isFileExist(file)) null
        else file.file2fileInputStream().inputStream2strMd5_use_ofHexString()

    @JvmStatic
    fun file2bytes(file: File): ByteArray? =
        if (!UtilKFileWrapper.isFileExist(file)) null
        else file.file2fileInputStream().inputStream2bytes_use()

    @JvmStatic
    fun file2bytes2(file: File): ByteArray? =
        if (!UtilKFileWrapper.isFileExist(file)) null
        else {
            val byteArrayOutputStream = UtilKByteArrayOutputStream.get(file)
            UtilKInputStream.read_write_use(file.file2fileInputStream().inputStream2bufferedInputStream(),byteArrayOutputStream, 1024)
            byteArrayOutputStream.byteArrayOutputStream2bytes_use()
        }
}