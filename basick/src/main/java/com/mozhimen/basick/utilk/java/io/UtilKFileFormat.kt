package com.mozhimen.basick.utilk.java.io

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKStrFile
import java.io.ByteArrayOutputStream
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
fun File.file2uri(): Uri? =
    UtilKFileFormat.file2uri(this)

fun File.file2anyBitmap(): Bitmap? =
    UtilKFileFormat.file2anyBitmap(this)

fun File.file2anyBitmap(opts: BitmapFactory.Options): Bitmap? =
    UtilKFileFormat.file2anyBitmap(this, opts)

fun File.file2strFilePath(): String =
    UtilKFileFormat.file2strFilePath(this)

////////////////////////////////////////////////////////////////////////////////////////

fun File.file2fileInputStream(): FileInputStream =
    UtilKFileFormat.file2fileInputStream(this)

fun File.file2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKFileFormat.file2fileOutputStream(this, isAppend)

////////////////////////////////////////////////////////////////////////////////////////

fun File.file2str(): String? =
    UtilKFileFormat.file2str(this)

fun File.file2bytes(): ByteArray? =
    UtilKFileFormat.file2bytes(this)

fun File.file2bytes2(): ByteArray? =
    UtilKFileFormat.file2bytes2(this)

fun File.file2bytes3(): ByteArray? =
    UtilKFileFormat.file2bytes3(this)

object UtilKFileFormat : BaseUtilK() {
    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun file2uri(file: File): Uri? {
        if (!UtilKFile.isFileExist(file)) {
            Log.e(TAG, "file2Uri: file isFileExist false")
            return null
        }
        return if (UtilKBuildVersion.isAfterV_24_7_N()) {
            val authority = "${UtilKPackage.getPackageName()}.fileProvider"
            Log.d(TAG, "file2Uri: authority $authority")
            FileProvider.getUriForFile(_context, authority, file).also {
                UtilKContext.grantUriPermission(_context, it, CIntent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else Uri.fromFile(file)
    }

    @JvmStatic
    fun file2anyBitmap(file: File): Bitmap? =
        UtilKStrFile.strFilePath2anyBitmap(file.file2strFilePath())

    @JvmStatic
    fun file2anyBitmap(file: File, opts: BitmapFactory.Options): Bitmap? =
        UtilKStrFile.strFilePath2anyBitmap(file.file2strFilePath(), opts)

    @JvmStatic
    fun file2strFilePath(file: File): String =
        file.absolutePath

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun file2fileInputStream(file: File): FileInputStream =
        FileInputStream(file)

    @JvmStatic
    fun file2fileOutputStream(file: File, isAppend: Boolean = false): FileOutputStream =
        FileOutputStream(file, isAppend)

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun file2str(file: File): String? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2str()

    @JvmStatic
    fun file2bytes(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2bytes()

    @JvmStatic
    fun file2bytes2(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else FileInputStream(file).inputStream2bytesCheck(file.length())

    @JvmStatic
    fun file2bytes3(file: File): ByteArray? =
        if (!UtilKFile.isFileExist(file)) null
        else {
            val byteArrayOutputStream = ByteArrayOutputStream(file.length().toInt())
            file.file2fileInputStream().inputStream2bufferedInputStream().inputStream2outputStream2(byteArrayOutputStream, 1024)
            byteArrayOutputStream.byteArrayOutputStream2bytes()
        }
}