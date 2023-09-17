package com.mozhimen.basick.utilk.android.graphics

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory.Options
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.IntRange
import androidx.core.graphics.drawable.toDrawable
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.content.anyBitmap2imageFile
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.file2strFilePath
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.outputStream2bufferedOutputStream
import com.mozhimen.basick.utilk.kotlin.bytes2file
import com.mozhimen.basick.utilk.kotlin.hasSpace
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File


/**
 * @ClassName UtilKBitmapFormat
 * @Description Bitmap bytes 流转换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */

//region # file

fun String.strFilePath2anyBitmap(): Bitmap? =
    UtilKBitmapFormat.strFilePath2anyBitmap(this)

fun File.file2anyBitmap(): Bitmap? =
    UtilKBitmapFormat.file2anyBitmap(this)

fun String.strFilePath2anyBitmap(opts: Options): Bitmap? =
    UtilKBitmapFormat.strFilePath2anyBitmap(this, opts)

fun File.file2anyBitmap(opts: Options): Bitmap? =
    UtilKBitmapFormat.file2anyBitmap(this, opts)

//////////////////////////////////////////////////////////////////////////////////////////

fun Bitmap.anyBitmap2file(destFilePathWithName: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.anyBitmap2file(this, destFilePathWithName, compressFormat, quality)

fun Bitmap.anyBitmap2file(destFile: File, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 10): File? =
    UtilKBitmapFormat.anyBitmap2file(this, destFile, compressFormat, quality)

fun Bitmap.jpegBitmap2jpegFile(destFilePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.jpegBitmap2jpegFile(this, destFilePathWithName, quality)

fun Bitmap.anyBitmap2jpegFile(destFilePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.anyBitmap2jpegFile(this, destFilePathWithName, quality)

fun Bitmap.anyBitmap2pngFile(destFilePathWithName: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.anyBitmap2pngFile(this, destFilePathWithName, quality)

fun Bitmap.anyBitmap2bmpFile(destFilePathWithName: String): File =
    UtilKBitmapFormat.anyBitmap2bmpFile(this, destFilePathWithName)
//endregion

//////////////////////////////////////////////////////////////////////////////////////////

fun ByteArray.anyBytes2anyBitmap(): Bitmap =
    UtilKBitmapFormat.anyBytes2anyBitmap(this)

fun Bitmap.anyBitmap2anyBytes(compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
    UtilKBitmapFormat.anyBitmap2anyBytes(this, compressFormat, quality)

fun Bitmap.anyBitmap2jpegBytes(): ByteArray =
    UtilKBitmapFormat.anyBitmap2jpegBytes(this)

fun Bitmap.anyBitmap2pngBytes(): ByteArray =
    UtilKBitmapFormat.anyBitmap2pngBytes(this)

fun Bitmap.anyBitmap2bmpBytes(): ByteArray =
    UtilKBitmapFormat.anyBitmap2bmpBytes(this)

//////////////////////////////////////////////////////////////////////////////////////////

fun Bitmap.anyBitmap2rgb565Bitmap(): Bitmap =
    UtilKBitmapFormat.anyBitmap2rgb565Bitmap(this)

fun Bitmap.anyBitmap2drawable(): Drawable =
    UtilKBitmapFormat.anyBitmap2drawable(this)

fun Bitmap.anyBitmap2bitmapDrawable(): BitmapDrawable =
    UtilKBitmapFormat.anyBitmap2bitmapDrawable(this)

object UtilKBitmapFormat : BaseUtilK() {

    //region # file

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName)

    @JvmStatic
    fun file2anyBitmap(file: File): Bitmap? =
        strFilePath2anyBitmap(file.file2strFilePath())

    @JvmStatic
    fun strFilePath2anyBitmap(filePathWithName: String, opts: Options): Bitmap? =
        if (filePathWithName.isEmpty() || filePathWithName.hasSpace()) null
        else BitmapFactory.decodeFile(filePathWithName, opts)

    @JvmStatic
    fun file2anyBitmap(file: File, opts: Options): Bitmap? =
        strFilePath2anyBitmap(file.file2strFilePath(), opts)

    //////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun anyBitmap2file(
        sourceBitmap: Bitmap, destFilePathWithName: String,
        compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? =
        anyBitmap2file(sourceBitmap, File(destFilePathWithName), compressFormat, quality)

    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun anyBitmap2file(
        sourceBitmap: Bitmap, destFile: File,
        compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? {
        UtilKFile.createFile(destFile)
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            bufferedOutputStream = destFile.file2fileOutputStream().outputStream2bufferedOutputStream()
            sourceBitmap.applyAnyBitmapCompress(compressFormat, quality, bufferedOutputStream)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            bufferedOutputStream?.flushClose()
        }
        return null
    }

    @SuppressLint("MissingPermission")
    @JvmStatic
    fun jpegBitmap2jpegFile(
        sourceBitmap: Bitmap, destFilePathWithName: String,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? =
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            if (UtilKPermission.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                sourceBitmap.anyBitmap2imageFile(destFilePathWithName, CompressFormat.JPEG, quality)
            else null
        } else anyBitmap2jpegFile(sourceBitmap, destFilePathWithName, quality)

    @JvmStatic
    fun anyBitmap2jpegFile(
        sourceBitmap: Bitmap, destFilePathWithName: String,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? =
        anyBitmap2file(sourceBitmap, destFilePathWithName, CompressFormat.JPEG, quality)

    @JvmStatic
    fun anyBitmap2pngFile(
        sourceBitmap: Bitmap, destFilePathWithName: String,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): File? =
        anyBitmap2file(sourceBitmap, destFilePathWithName, CompressFormat.PNG, quality)

    @JvmStatic
    fun anyBitmap2bmpFile(
        sourceBitmap: Bitmap, destFilePathWithName: String
    ): File =
        sourceBitmap.anyBitmap2bmpBytes().bytes2file(destFilePathWithName)

    //endregion

    //////////////////////////////////////////////////////////////////////////////

    //region # bytes

    @JvmStatic
    fun anyBytes2anyBitmap(bytes: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

    @JvmStatic
    fun anyBitmap2anyBytes(
        sourceBitmap: Bitmap,
        compressFormat: CompressFormat = CompressFormat.JPEG,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream(sourceBitmap.width * sourceBitmap.height * 4)
        sourceBitmap.applyAnyBitmapCompress(compressFormat, quality, byteArrayOutputStream)
        return byteArrayOutputStream.byteArrayOutputStream2bytes()
    }

    @JvmStatic
    fun anyBitmap2jpegBytes(
        sourceBitmap: Bitmap,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): ByteArray =
        anyBitmap2anyBytes(sourceBitmap, CompressFormat.JPEG, quality)

    @JvmStatic
    fun anyBitmap2pngBytes(
        sourceBitmap: Bitmap,
        @IntRange(from = 0, to = 100) quality: Int = 100
    ): ByteArray =
        anyBitmap2anyBytes(sourceBitmap, CompressFormat.PNG, quality)

    @JvmStatic
    fun anyBitmap2bmpBytes(
        sourceBitmap: Bitmap
    ): ByteArray {
        val width: Int = sourceBitmap.width
        val height: Int = sourceBitmap.height
        val wWidth = width * 3 + width % 4
        val bmpDateSize = height * wWidth
        val size = 14 + 40 + bmpDateSize
        val bytes = ByteArray(size)

        // 1.BMP文件头 14
        bytes[0] = 0x42 //bfType 2bytes
        bytes[1] = 0x4D
        bytes[2] = (size shr 0 and 0xFF).toByte() //bfSize 4bytes
        bytes[3] = (size shr 8 and 0xFF).toByte()
        bytes[4] = (size shr 16 and 0xFF).toByte()
        bytes[5] = (size shr 24 and 0xFF).toByte()
        bytes[6] = 0x00 //bfReserved1 2bytes
        bytes[7] = 0x00
        bytes[8] = 0x00 //bfReserved2 2bytes
        bytes[9] = 0x00
        bytes[10] = 0x36 //bfOffBits 14+40 4bytes
        bytes[11] = 0x00
        bytes[12] = 0x00
        bytes[13] = 0x00

        // 2.BMP信息头 40
        bytes[14] = 0x28 //biSize 40 4bytes
        bytes[15] = 0x00
        bytes[16] = 0x00
        bytes[17] = 0x00
        bytes[18] = (width shr 0 and 0xFF).toByte() //biWidth 4bytes
        bytes[19] = (width shr 8 and 0xFF).toByte()
        bytes[20] = (width shr 16 and 0xFF).toByte()
        bytes[21] = (width shr 24 and 0xFF).toByte()
        bytes[22] = (height shr 0 and 0xFF).toByte() //biHeight 4bytes
        bytes[23] = (height shr 8 and 0xFF).toByte()
        bytes[24] = (height shr 16 and 0xFF).toByte()
        bytes[25] = (height shr 24 and 0xFF).toByte()
        bytes[26] = 0x01 //biPlanes 2bytes
        bytes[27] = 0x00
        bytes[28] = 0x18 //biBitCount 24位位图 2bytes
        bytes[29] = 0x00
        bytes[30] = 0x00 //biCompression 4bytes
        bytes[31] = 0x00
        bytes[32] = 0x00
        bytes[33] = 0x00
        bytes[34] = 0x00 //biSizeImage 4bytes
        bytes[35] = 0x00
        bytes[36] = 0x00
        bytes[37] = 0x00
        bytes[38] = 0x00 //biXpelsPerMeter 4bytes
        bytes[39] = 0x00
        bytes[40] = 0x00
        bytes[41] = 0x00
        bytes[42] = 0x00 //biYPelsPerMeter 4bytes
        bytes[43] = 0x00
        bytes[44] = 0x00
        bytes[45] = 0x00
        bytes[46] = 0x00 //biClrUsed 4bytes
        bytes[47] = 0x00
        bytes[48] = 0x00
        bytes[49] = 0x00
        bytes[50] = 0x00 //biClrImportant 4bytes
        bytes[51] = 0x00
        bytes[52] = 0x00
        bytes[53] = 0x00

        val bmpData = ByteArray(bmpDateSize)
        var nCol: Int = 0
        var nRealCol: Int = height - 1
        while (nCol < height) {
            var wRow: Int = 0
            var wByteIdex: Int = 0
            while (wRow < width) {
                val clr: Int = sourceBitmap.getPixel(wRow, nCol)
                //clr = clr == 0 ? 0xFFFFFF : clr; //黑色背景转为白色
                bmpData[nRealCol * wWidth + wByteIdex] = Color.blue(clr).toByte()
                bmpData[nRealCol * wWidth + wByteIdex + 1] = Color.green(clr).toByte()
                bmpData[nRealCol * wWidth + wByteIdex + 2] = Color.red(clr).toByte()
                wRow++
                wByteIdex += 3
            }
            ++nCol
            --nRealCol
        }

        System.arraycopy(bmpData, 0, bytes, 54, bmpDateSize)
        return bytes
    }
    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun anyBitmap2rgb565Bitmap(sourceBitmap: Bitmap): Bitmap =
        sourceBitmap.copy(Bitmap.Config.RGB_565, true)

    @JvmStatic
    fun anyBitmap2drawable(sourceBitmap: Bitmap): Drawable =
        sourceBitmap.toDrawable(UtilKResource.getSystemResources())

    @JvmStatic
    fun anyBitmap2bitmapDrawable(sourceBitmap: Bitmap): BitmapDrawable =
        BitmapDrawable(UtilKResource.getAppResources(_context), sourceBitmap)
}