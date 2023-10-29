package com.mozhimen.basick.utilk.android.net

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.DocumentsContract
import com.mozhimen.basick.elemk.android.content.cons.CContentResolver
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.withAppendedId
import com.mozhimen.basick.utilk.android.graphics.UtilKImageDecoder
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.provider.UtilKDocumentsContract
import com.mozhimen.basick.utilk.android.provider.UtilKMediaStore
import com.mozhimen.basick.utilk.android.provider.getMediaColumns
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.webkit.UtilKMimeTypeMap
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.io.inputStream2bitmapAny
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.strUri2uri
import com.mozhimen.basick.utilk.kotlin.text.isStrDigits2
import java.io.File
import java.io.InputStream
import kotlin.math.ceil

/**
 * @ClassName UtilKUriFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 0:23
 * @Version 1.0
 */
fun Uri.uri2strFilePath(): String? =
    UtilKUriFormat.uri2strFilePath(this)

fun Uri.uri2file(): File? =
    UtilKUriFormat.uri2file(this)

fun Uri.uri2bitmap(): Bitmap =
    UtilKUriFormat.uri2bitmap(this)

fun Uri.uri2bitmap2(): Bitmap? =
    UtilKUriFormat.uri2bitmap2(this)

object UtilKUriFormat : BaseUtilK() {
    @SuppressLint("Recycle")
    @JvmStatic
    fun uri2strFilePath(uri: Uri, strFilePathNameDest: String = ""): String? {
        when (uri.scheme) {
            CContentResolver.SCHEME_FILE -> uri.path
            CContentResolver.SCHEME_CONTENT -> {
                if (DocumentsContract.isDocumentUri(_context, uri)) {
                    val documentId = UtilKDocumentsContract.getDocumentId(uri)
                    val split = documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    val path = split[1]

                    when {
                        uri.isAuthorityDownloadsDocument() -> {
                            if (documentId.isStrDigits2())
                                return "content://downloads/public_downloads".strUri2uri().withAppendedId(documentId.toLong()).getMediaColumns()
                        }

                        uri.isAuthorityExternalStorageDocument() -> {
                            if (type.equals(CMediaStore.Type.PRIMARY, true))
                                return "${UtilKStrPath.Absolute.External.getEnvStorage()}/$path"
                        }

                        uri.isAuthorityMediaDocument() -> {
                            if (type.equals(CMediaStore.Type.PRIMARY, true))
                                return "${UtilKStrPath.Absolute.External.getEnvStorage()}/$path"
                            else if (type.equals(CMediaStore.Type.RAW, true))
                                return path
                            val externalContentUri = when {
                                type.equals(CMediaStore.Type.VIDEO, true) -> CMediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                type.equals(CMediaStore.Type.AUDIO, true) -> CMediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                type.equals(CMediaStore.Type.IMAGE, true) -> CMediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                else -> null
                            }
                            if (externalContentUri != null)
                                return externalContentUri.getMediaColumns("${CMediaStore.MediaColumns._ID}=?", arrayOf(path))
                        }
                    }

                    if (UtilKBuildVersion.isAfterV_29_10_Q() && strFilePathNameDest.isNotEmpty())
                        return UtilKContentResolver.openInputStream(_context, uri)
                            ?.inputStream2file("$strFilePathNameDest.${UtilKMimeTypeMap.getExtensionFromMimeType(_context, uri)}")?.absolutePath
                }

                return uri.getMediaColumns()
            }
        }
        return null
    }

    @JvmStatic
    fun uri2file(uri: Uri, strFilePathNameDest: String = ""): File? =
        uri2strFilePath(uri, strFilePathNameDest)?.let { File(it) }

    /**
     * 从相册获得图片
     */
    @JvmStatic
    fun uri2bitmap(uri: Uri): Bitmap =
        if (UtilKBuildVersion.isAfterV_28_9_P())
            UtilKImageDecoder.decodeBitmap(_context, uri)
        else UtilKMediaStore.getImagesMediaBitmap(_context, uri)

    @JvmStatic
    fun uri2bitmap2(uri: Uri): Bitmap? {
        var contentSizeInputStream: InputStream? = null
        var realInputStream: InputStream? = null
        try {
            //根据uri获取图片的流
            contentSizeInputStream = UtilKContentResolver.openInputStream(_context, uri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true            //options的in系列的设置了，injustDecodeBound只解析图片的大小，而不加载到内存中去
            //1.如果通过options.outHeight获取图片的宽高，就必须通过decodeStream解析同options赋值
            //否则options.outHeight获取不到宽高
            contentSizeInputStream?.inputStream2bitmapAny(null, options)
            //2.通过 btm.getHeight()获取图片的宽高就不需要1的解析，我这里采取第一张方式
            //Bitmap btm = BitmapFactory.decodeStream(inputStream)
            //获取图片的宽高
            val outHeight = options.outHeight.toDouble()
            val outWidth = options.outWidth.toDouble()
            //heightPixels就是要压缩后的图片高度，宽度也一样
            val a = ceil((outHeight / UtilKScreen.getCurrentHeight().toDouble())).toInt()
            val b = ceil((outWidth / UtilKScreen.getCurrentWidth().toDouble())).toInt()
            //比例计算,一般是图片比较大的情况下进行压缩
            val max = a.coerceAtLeast(b)
            if (max > 1)
                options.inSampleSize = max
            //解析到内存中去
            options.inJustDecodeBounds = false
            //根据uri重新获取流，inputStream在解析中发生改变了
            realInputStream = UtilKContentResolver.openInputStream(_context, uri)
            return realInputStream?.inputStream2bitmapAny(null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            contentSizeInputStream?.close()
            realInputStream?.close()
        }
    }
}