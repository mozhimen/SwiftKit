package com.mozhimen.basick.utilk.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.java.io.UtilKFileFormat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @ClassName UtilKStrFilePath
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/18 23:35
 * @Version 1.0
 */
fun String.strFilePath2bytes(): ByteArray? =
    UtilKStrFile.strFilePath2bytes(this)

fun String.strFilePath2bytes2(): ByteArray? =
    UtilKStrFile.strFilePath2bytes2(this)

fun String.strFilePath2bytes3(): ByteArray? =
    UtilKStrFile.strFilePath2bytes3(this)

fun String.strFilePath2str(): String? =
    UtilKStrFile.strFilePath2str(this)

fun String.strFilePath2fileOutputStream(isAppend: Boolean = false): FileOutputStream =
    UtilKStrFile.strFilePath2fileOutputStream(this, isAppend)

fun String.strFilePath2fileInputStream(): FileInputStream =
    UtilKStrFile.strFilePath2fileInputStream(this)

fun String.strFilePath2file(): File =
    UtilKStrFile.strFilePath2file(this)

fun String.strFilePath2uri(): Uri? =
    UtilKStrFile.strFilePath2uri(this)

fun String.strFilePath2anyBitmap(): Bitmap? =
    UtilKStrFile.strFilePath2anyBitmap(this)

fun String.strFilePath2anyBitmap(opts: BitmapFactory.Options): Bitmap? =
    UtilKStrFile.strFilePath2anyBitmap(this, opts)

object UtilKStrFile {

    @JvmStatic
    fun strFilePath2str(filePathWithName: String): String? =
        UtilKFileFormat.file2str(filePathWithName.strFilePath2file())

    fun strFilePath2fileOutputStream(filePathWithName: String, isAppend: Boolean = false): FileOutputStream =
        UtilKFileFormat.file2fileOutputStream(filePathWithName.strFilePath2file(), isAppend)

    fun strFilePath2fileInputStream(filePathWithName: String): FileInputStream =
        UtilKFileFormat.file2fileInputStream(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2file(destFilePathWithName: String): File =
        File(destFilePathWithName)

    ////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2bytes(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2bytes2(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes2(filePathWithName.strFilePath2file())

    @JvmStatic
    fun strFilePath2bytes3(filePathWithName: String): ByteArray? =
        UtilKFileFormat.file2bytes3(filePathWithName.strFilePath2file())

    /**
     * 文件转Uri
     */
    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun strFilePath2uri(filePathWithName: String): Uri? =
        UtilKFileFormat.file2uri(File(filePathWithName))

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName)

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String, opts: BitmapFactory.Options): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName, opts)
}