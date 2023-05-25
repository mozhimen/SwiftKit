package com.mozhimen.basick.utilk.content

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.exts.et
import com.mozhimen.basick.utilk.exts.getStringValue
import com.mozhimen.basick.utilk.java.datatype.regular.UtilKVerifyString
import com.mozhimen.basick.utilk.java.io.file.UtilKFile
import java.io.File
import java.io.IOException
import java.io.InputStream


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
object UtilKUri {

    private val TAG = "UtilKFileUri>>>>>"
    private val _context by lazy { UtilKApplication.instance.get() }

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


    /**
     * 文件转Uri
     * @param filePathWithName String
     * @return Uri
     */
    @JvmStatic
    fun filePathStr2Uri(filePathWithName: String): Uri? {
        if (filePathWithName.isEmpty()) {
            Log.e(TAG, "file2Uri: isEmpty true")
            return null
        }
        return file2Uri(File(filePathWithName))
    }

    /**
     * file2Uri
     * @param filePathWithName String
     * @return Uri?
     */
    @JvmStatic
    @ADescription(
        Intent.FLAG_GRANT_READ_URI_PERMISSION.toString(),
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION.toString()
    )
    fun file2Uri(filePathWithName: String): Uri? {
        return file2Uri(File(filePathWithName))
    }

    /**
     * @param file File
     * @return Uri?
     */
    @JvmStatic
    @ADescription(
        Intent.FLAG_GRANT_READ_URI_PERMISSION.toString(),
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION.toString()
    )
    fun file2Uri(file: File): Uri? {
        if (!UtilKFile.isFileExist(file)) {
            Log.e(TAG, "file2Uri: file isFileExist false")
            return null
        }
        return if (Build.VERSION.SDK_INT >= CVersionCode.V_24_7_N) {
            val authority = "${UtilKContext.getPackageName(_context)}.fileProvider"
            Log.d(TAG, "file2Uri: authority $authority")
            FileProvider.getUriForFile(_context, authority, file).also {
                UtilKContext.grantUriPermission(_context, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        } else Uri.fromFile(file)
    }

    @JvmStatic
    fun uri2File(uri: Uri, filePathWithName: String): File? {
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
                val inputStream: InputStream?
                try {
                    inputStream = contentResolver.openInputStream(uri) ?: return null
                    return UtilKFile.inputStream2File(
                        inputStream,
                        filePathWithName + ".${MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))}"
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.message?.et(TAG)
                }
            }
        }
        return null
    }

    @JvmStatic
    fun isDownloadsDocument(uri: Uri) = uri.authority == "com.android.providers.downloads.documents"

    @JvmStatic
    fun isExternalStorageDocument(uri: Uri) = uri.authority == "com.android.externalstorage.documents"

    @JvmStatic
    fun isMediaDocument(uri: Uri) = uri.authority == "com.android.providers.media.documents"

    @JvmStatic
    fun uri2filePathWithName(uri: Uri): String? {
        if (uri.scheme == "file") return uri.path

        if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            if (UtilKVerifyString.checkAllDigits2(id)) {
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

    fun getDataColumn(uri: Uri, selection: String? = null, selectionArgs: Array<String>? = null): String? {
        try {
            val projection = arrayOf(MediaStore.Files.FileColumns.DATA)
            val cursor = _context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val data = cursor.getStringValue(MediaStore.Files.FileColumns.DATA)
                    if (data != "null") {
                        return data
                    }
                }
            }
        } catch (e: Exception) {
        }
        return null
    }
}
