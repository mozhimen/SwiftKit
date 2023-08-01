package com.mozhimen.basick.utilk.android.net

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.database.getString
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.kotlin.text.UtilKVerifyStr
import com.mozhimen.basick.utilk.java.io.UtilKFile
import com.mozhimen.basick.utilk.java.io.asFile
import java.io.File
import java.io.IOException
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

    @JvmStatic
    fun getDataColumn(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
            val cursor = _context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val data = cursor.getString(MediaStore.Files.FileColumns.DATA)
                    if (data != "null") {
                        return data
                    }
                }
            }
        } catch (e: Exception) {
        }
        return null
    }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isDownloadsDocument(uri: Uri) = uri.authority == "com.android.providers.downloads.documents"

    @JvmStatic
    fun isExternalStorageDocument(uri: Uri) = uri.authority == "com.android.externalstorage.documents"

    @JvmStatic
    fun isMediaDocument(uri: Uri) = uri.authority == "com.android.providers.media.documents"

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 文件转Uri
     * @param filePathWithName String
     * @return Uri
     */
    @JvmStatic
    @ADescription(Intent.FLAG_GRANT_READ_URI_PERMISSION.toString(), Intent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun strPath2uri(filePathWithName: String): Uri? {
        if (filePathWithName.isEmpty()) {
            Log.e(TAG, "file2Uri: isEmpty true")
            return null
        }
        return file2uri(File(filePathWithName))
    }

    /**
     * @param file File
     * @return Uri?
     */
    @JvmStatic
    @ADescription(Intent.FLAG_GRANT_READ_URI_PERMISSION.toString(), Intent.FLAG_GRANT_WRITE_URI_PERMISSION.toString())
    fun file2uri(file: File): Uri? {
        if (!UtilKFile.isFileExist(file)) {
            Log.e(TAG, "file2Uri: file isFileExist false")
            return null
        }
        return if (UtilKBuildVersion.isAfterV_24_7_N()) {
            val authority = "${UtilKPackage.getPackageName()}.fileProvider"
            Log.d(TAG, "file2Uri: authority $authority")
            FileProvider.getUriForFile(_context, authority, file).also {
                UtilKContext.grantUriPermission(_context, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else Uri.fromFile(file)
    }

    @JvmStatic
    fun uri2file(uri: Uri, filePathWithName: String): File? {
        //android10以上转换
        when (uri.scheme) {
            ContentResolver.SCHEME_FILE -> {
                uri.path?.let {
                    return File(it)
                }
            }

            ContentResolver.SCHEME_CONTENT -> {
                //把文件复制到沙盒目录
                val contentResolver = _context.contentResolver
                var inputStream: InputStream? = null
                try {
                    inputStream = contentResolver.openInputStream(uri) ?: return null
                    return inputStream.asFile(filePathWithName + ".${MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))}")
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                } finally {
                    inputStream?.close()
                }
            }
        }
        return null
    }

    @JvmStatic
    fun uri2strPath(uri: Uri): String? {
        if (uri.scheme == "file") return uri.path

        if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            if (UtilKVerifyStr.isAllDigits2(id)) {
                val newUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), id.toLong())
                val path = getDataColumn(newUri)
                if (path != null) {
                    return path
                }
            }
        } else if (isExternalStorageDocument(uri)) {
            val documentId = DocumentsContract.getDocumentId(uri)
            val parts = documentId.split(":")
            if (parts[0].equals("primary", true)) {
                return "${Environment.getExternalStorageDirectory().absolutePath}/${parts[1]}"
            }
        } else if (isMediaDocument(uri)) {
            val documentId = DocumentsContract.getDocumentId(uri)
            val split = documentId.split(":").dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            val contentUri = when (type) {
                "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            val path = getDataColumn(contentUri, selection, selectionArgs)
            if (path != null) {
                return path
            }
        }

        return getDataColumn(uri)
    }

    /**
     * 从相册获得图片
     * @param uri Uri
     * @return Bitmap?
     */
    @JvmStatic
    fun uri2bitmap(uri: Uri): Bitmap? {
        return try {
            if (UtilKBuildVersion.isAfterV_28_9_P()) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(UtilKContentResolver.get(_context), uri))
            } else {
                MediaStore.Images.Media.getBitmap(UtilKContentResolver.get(_context), uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    fun uri2bitmap(context: Context, uri: Uri): Bitmap? {
        var stream: InputStream? = null
        var inputStream: InputStream? = null
        try {
            //根据uri获取图片的流
            inputStream = UtilKContentResolver.get(context).openInputStream(uri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true            //options的in系列的设置了，injustDecodeBound只解析图片的大小，而不加载到内存中去
            //1.如果通过options.outHeight获取图片的宽高，就必须通过decodeStream解析同options赋值
            //否则options.outHeight获取不到宽高
            BitmapFactory.decodeStream(inputStream, null, options)
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
            if (max > 1) {
                options.inSampleSize = max
            }
            //解析到内存中去
            options.inJustDecodeBounds = false
            //根据uri重新获取流，inputStream在解析中发生改变了
            stream = UtilKContentResolver.get(context).openInputStream(uri)
            return BitmapFactory.decodeStream(stream, null, options)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            inputStream?.close()
            stream?.close()
        }
    }
}
