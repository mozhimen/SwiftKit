package com.mozhimen.basick.utilk.android.graphics

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.graphics.drawable.toDrawable
import com.mozhimen.basick.elemk.android.media.cons.CMediaFormat
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.elemk.android.util.cons.CBase64
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKResource
import com.mozhimen.basick.utilk.android.media.UtilKMediaScannerConnection
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.byteArrayOutputStream2bytes
import com.mozhimen.basick.utilk.java.io.file2fileOutputStream
import com.mozhimen.basick.utilk.java.io.flushClose
import com.mozhimen.basick.utilk.java.io.outputStream2bufferedOutputStream
import com.mozhimen.basick.utilk.kotlin.bytes2file
import com.mozhimen.basick.utilk.kotlin.bytes2strBase64
import com.mozhimen.basick.utilk.kotlin.createFile
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream


/**
 * @ClassName UtilKBitmapFormat
 * @Description Bitmap bytes 流转换类
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 4:54
 * @Version 1.0
 */
fun Bitmap.bitmapAny2strBase64(compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 50, flags: Int = CBase64.NO_WRAP): String? =
    UtilKBitmapFormat.bitmapAny2strBase64(this,compressFormat, quality, flags)

@RequiresApi(CVersCode.V_29_10_Q)
@RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
@AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE)
fun Bitmap.bitmapAny2fileImage(strFilePathName: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.bitmapAny2fileImage(this, strFilePathName, compressFormat, quality)

fun Bitmap.bitmapAny2file(strFilePathNameDest: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.bitmapAny2file(this, strFilePathNameDest, compressFormat, quality)

fun Bitmap.bitmapAny2file(fileDest: File, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 10): File? =
    UtilKBitmapFormat.bitmapAny2file(this, fileDest, compressFormat, quality)

fun Bitmap.bitmapJpeg2fileJpeg(strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.bitmapJpeg2fileJpeg(this, strFilePathNameDest, quality)

fun Bitmap.bitmapAny2fileJpeg(strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.bitmapAny2fileJpeg(this, strFilePathNameDest, quality)

fun Bitmap.bitmapAny2filePng(strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
    UtilKBitmapFormat.bitmapAny2filePng(this, strFilePathNameDest, quality)

fun Bitmap.bitmapAny2fileBmp(strFilePathNameDest: String): File =
    UtilKBitmapFormat.bitmapAny2fileBmp(this, strFilePathNameDest)

//////////////////////////////////////////////////////////////////////////////////////////

fun Bitmap.bitmapAny2bytesAny(compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
    UtilKBitmapFormat.bitmapAny2bytesAny(this, compressFormat, quality)

fun Bitmap.bitmapAny2bytesJpeg(): ByteArray =
    UtilKBitmapFormat.bitmapAny2bytesJpeg(this)

fun Bitmap.bitmapAny2bytesPng(): ByteArray =
    UtilKBitmapFormat.bitmapAny2bytesPng(this)

fun Bitmap.bitmapAny2bytesBmp(): ByteArray =
    UtilKBitmapFormat.bitmapAny2bytesBmp(this)

//////////////////////////////////////////////////////////////////////////////////////////

fun Bitmap.bitmapAny2bitmapRgb565(): Bitmap =
    UtilKBitmapFormat.bitmapAny2bitmapRgb565(this)

fun Bitmap.bitmapAny2drawable(): Drawable =
    UtilKBitmapFormat.bitmapAny2drawable(this)

fun Bitmap.bitmapAny2bitmapDrawable(): BitmapDrawable =
    UtilKBitmapFormat.bitmapAny2bitmapDrawable(this)

object UtilKBitmapFormat : BaseUtilK() {

    /**
     * 位图转base64
     * flags参数说明
     * URL_SAFE：安全的URL编码，base64转码过程中会生成“+”，“/”，“=”这些会被URL进行转码的特殊字符，导致前后台数据不同，所以需要将这些字符替代为URL不会进行转码的字符，保证数据同步；
     * "-" -> "+"
     * "_" -> "/"
     * NO_WRAP：不换行
     * NO_PADDING："="号补齐去除，base64会对字符进行串长度余4的"="的补位，需去除"="。
     */
    @JvmStatic
    fun bitmapAny2strBase64(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 50, flags: Int = CBase64.NO_WRAP): String? {
        return sourceBitmap.bitmapAny2bytesAny(compressFormat, quality).bytes2strBase64(flags)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @RequiresPermission(CPermission.WRITE_EXTERNAL_STORAGE)
    @AManifestKRequire(CPermission.WRITE_EXTERNAL_STORAGE)
    fun bitmapAny2fileImage(sourceBitmap: Bitmap, strFilePathName: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? {
        var outputStream: OutputStream? = null
        val fileDest = strFilePathName.createFile()
        try {
            val contentValues = ContentValues().apply {
                put(CMediaStore.Images.ImageColumns.DATA, fileDest.absolutePath)
                put(CMediaStore.Images.ImageColumns.DISPLAY_NAME, strFilePathName.split("/").lastOrNull() ?: UtilKFile.getStrFileNameForStrNowDate())
                put(CMediaStore.Images.ImageColumns.MIME_TYPE, CMediaFormat.MIMETYPE_IMAGE_JPEG)
                put(CMediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis().toString())
            }
            UtilKContentResolver.insert(_context, CMediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {
                outputStream = UtilKContentResolver.openOutputStream(_context, it)
                sourceBitmap.applyBitmapAnyCompress(compressFormat, quality, outputStream!!)
                return fileDest
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        } finally {
            outputStream?.flushClose()
            try {
                UtilKMediaScannerConnection.scanFile(_context, arrayOf(fileDest.absolutePath), arrayOf(CMediaFormat.MIMETYPE_IMAGE_JPEG)) { path, uri ->
                    "bitmapAny2fileImage: path $path, uri $uri".dt(TAG)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        return null
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun bitmapAny2file(sourceBitmap: Bitmap, strFilePathNameDest: String, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bitmapAny2file(sourceBitmap, File(strFilePathNameDest), compressFormat, quality)

    /**
     * 保存图片 before 29
     */
    @JvmStatic
    fun bitmapAny2file(sourceBitmap: Bitmap, fileDest: File, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): File? {
        UtilKFile.createFile(fileDest)
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            bufferedOutputStream = fileDest.file2fileOutputStream().outputStream2bufferedOutputStream()
            sourceBitmap.applyBitmapAnyCompress(compressFormat, quality, bufferedOutputStream)
            return fileDest
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
    fun bitmapJpeg2fileJpeg(sourceBitmap: Bitmap, strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            if (UtilKPermission.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                sourceBitmap.bitmapAny2fileImage(strFilePathNameDest, CompressFormat.JPEG, quality)
            else {
                Log.d(TAG, "bitmapJpeg2fileJpeg: dont has permission")
                null
            }
        } else bitmapAny2fileJpeg(sourceBitmap, strFilePathNameDest, quality)

    @JvmStatic
    fun bitmapAny2fileJpeg(sourceBitmap: Bitmap, strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bitmapAny2file(sourceBitmap, strFilePathNameDest, CompressFormat.JPEG, quality)

    @JvmStatic
    fun bitmapAny2filePng(sourceBitmap: Bitmap, strFilePathNameDest: String, @IntRange(from = 0, to = 100) quality: Int = 100): File? =
        bitmapAny2file(sourceBitmap, strFilePathNameDest, CompressFormat.PNG, quality)

    @JvmStatic
    fun bitmapAny2fileBmp(sourceBitmap: Bitmap, strFilePathNameDest: String): File =
        sourceBitmap.bitmapAny2bytesBmp().bytes2file(strFilePathNameDest)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun bitmapAny2bytesAny(sourceBitmap: Bitmap, compressFormat: CompressFormat = CompressFormat.JPEG, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream(sourceBitmap.width * sourceBitmap.height * 4)
        sourceBitmap.applyBitmapAnyCompress(compressFormat, quality, byteArrayOutputStream)
        return byteArrayOutputStream.byteArrayOutputStream2bytes()
    }

    @JvmStatic
    fun bitmapAny2bytesJpeg(sourceBitmap: Bitmap, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
        bitmapAny2bytesAny(sourceBitmap, CompressFormat.JPEG, quality)

    @JvmStatic
    fun bitmapAny2bytesPng(sourceBitmap: Bitmap, @IntRange(from = 0, to = 100) quality: Int = 100): ByteArray =
        bitmapAny2bytesAny(sourceBitmap, CompressFormat.PNG, quality)

    @JvmStatic
    fun bitmapAny2bytesBmp(sourceBitmap: Bitmap): ByteArray {
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

    ////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun bitmapAny2bitmapRgb565(sourceBitmap: Bitmap): Bitmap =
        sourceBitmap.copy(Bitmap.Config.RGB_565, true)

    @JvmStatic
    fun bitmapAny2drawable(sourceBitmap: Bitmap): Drawable =
        sourceBitmap.toDrawable(UtilKResource.getSystemResources())

    @JvmStatic
    fun bitmapAny2bitmapDrawable(sourceBitmap: Bitmap): BitmapDrawable =
        BitmapDrawable(UtilKResource.getAppResources(_context), sourceBitmap)
}