package com.mozhimen.basick.utilk.android.net

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.android.content.cons.CContentResolver
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.provider.cons.CMediaStore
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.withAppendedId
import com.mozhimen.basick.utilk.android.graphics.UtilKImageDecoder
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.provider.UtilKDocumentsContract
import com.mozhimen.basick.utilk.android.provider.UtilKMediaStoreImages
import com.mozhimen.basick.utilk.android.provider.getMediaColumnsString
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.webkit.UtilKMimeTypeMap
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.inputStream2anyBitmap
import com.mozhimen.basick.utilk.java.io.inputStream2file
import com.mozhimen.basick.utilk.kotlin.UtilKStrPath
import com.mozhimen.basick.utilk.kotlin.text.isStrDigits2
import java.io.File
import java.io.InputStream
import kotlin.math.ceil


/**
 * @ClassName UtilKFileUri
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 18:58
 * @Version 1.0
 */
/**
 * example
uri: content://com.android.providers.media.documents/document/image%3A27391
authority: com.android.providers.media.documents
encodedAuthority: com.android.providers.media.documents
path: /document/image:27391
encodedFragment: null
encodedPath: /document/image%3A27391
encodedQuery: null
encodedSchemeSpecificPart: //com.android.providers.media.documents/document/image%3A27391
encodedUserInfo: null
fragment: null
host: com.android.providers.media.documents
isAbsolute: true
isHierarchical: true
isOpaque: false
isRelative: false
lastPathSegment: image:27391
pathSegments: [document, image:27391]
port: -1
query: null
queryParameterNames: []
scheme: content
schemeSpecificPart: //com.android.providers.media.documents/document/image:27391
userInfo: null
 */

fun String.strUri2uri(): Uri =
    UtilKUri.strUri2uri(this)

fun Uri.isAuthorityDownloadsDocument(): Boolean =
    UtilKUri.isAuthorityDownloadsDocument(this)

fun Uri.isAuthorityExternalStorageDocument(): Boolean =
    UtilKUri.isAuthorityExternalStorageDocument(this)

fun Uri.isAuthorityMediaDocument(): Boolean =
    UtilKUri.isAuthorityMediaDocument(this)

object UtilKUri : BaseUtilK() {

    /**
     * 获取PackageUri
     * @param context Context
     * @return Uri
     */
    @JvmStatic
    fun getPackageUri(context: Context): Uri =
        Uri.parse("package:${UtilKContext.getPackageName(context)}")

    /**
     * 获取PackageUri
     * @param context Context
     * @return Uri
     */
    @JvmStatic
    fun getPackageUri2(context: Context): Uri =
        Uri.fromParts("package", UtilKContext.getPackageName(context), null)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAuthorityDownloadsDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_PROVIDERS_DOWNLOADS_DOCUMENTS//"com.android.providers.downloads.documents"

    @JvmStatic
    fun isAuthorityExternalStorageDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_EXTERNALSTORAGE_DOCUMENTS//"com.android.externalstorage.documents"

    @JvmStatic
    fun isAuthorityMediaDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_PROVIDERS_MEDIA_DOCUMENTS//"com.android.providers.media.documents"

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 文件转Uri
     * @param filePathWithName String
     * @return Uri
     */
    @JvmStatic
    @ADescription(CIntent.FLAG_GRANT_READ_URI_PERMISSION.toString(), CIntent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun strFilePath2uri(filePathWithName: String): Uri? =
        file2uri(File(filePathWithName))

    @JvmStatic
    fun strUri2uri(uriStr: String): Uri =
        Uri.parse(uriStr)

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

    @SuppressLint("Recycle")
    @JvmStatic
    fun uri2strFilePath(uri: Uri, destFilePathWithName: String = ""): String? {
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
                                return "content://downloads/public_downloads".strUri2uri().withAppendedId(documentId.toLong()).getMediaColumnsString()
                        }

                        uri.isAuthorityExternalStorageDocument() -> {
                            if (type.equals(CMediaStore.Type.PRIMARY, true))
                                return "${UtilKStrPath.Absolute.External.getEnvStorageDir()}/$path"
                        }

                        uri.isAuthorityMediaDocument() -> {
                            if (type.equals(CMediaStore.Type.PRIMARY, true))
                                return "${UtilKStrPath.Absolute.External.getEnvStorageDir()}/$path"
                            else if (type.equals(CMediaStore.Type.RAW, true))
                                return path
                            when {
                                type.equals(CMediaStore.Type.VIDEO, true) -> CMediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                type.equals(CMediaStore.Type.AUDIO, true) -> CMediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                type.equals(CMediaStore.Type.IMAGE, true) -> CMediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                else -> null
                            }?.let {
                                return it.getMediaColumnsString("${CMediaStore.MediaColumns._ID}=?", arrayOf(path))
                            }
                        }
                    }

                    if (UtilKBuildVersion.isAfterV_29_10_Q() && destFilePathWithName.isNotEmpty())
                        return UtilKContentResolver.openInputStream(_context, uri)
                            ?.inputStream2file("$destFilePathWithName.${UtilKMimeTypeMap.getExtensionFromMimeType(_context, uri)}")?.absolutePath
                }

                return uri.getMediaColumnsString()
            }
        }
        return null
    }

    @JvmStatic
    fun uri2file(uri: Uri, destFilePathWithName: String = ""): File? =
        uri2strFilePath(uri, destFilePathWithName)?.let { File(it) }

    /**
     * 从相册获得图片
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun uri2bitmap(uri: Uri): Bitmap =
        if (UtilKBuildVersion.isAfterV_28_9_P())
            UtilKImageDecoder.decodeBitmap(_context, uri)
        else UtilKMediaStoreImages.getMediaBitmap(_context, uri)

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
            contentSizeInputStream?.inputStream2anyBitmap(null, options)
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
            return realInputStream?.inputStream2anyBitmap(null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            contentSizeInputStream?.close()
            realInputStream?.close()
        }
    }


}
