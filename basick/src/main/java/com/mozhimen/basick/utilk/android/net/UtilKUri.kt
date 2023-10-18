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

fun Uri.isAuthorityDownloadsDocument(): Boolean =
    UtilKUri.isAuthorityDownloadsDocument(this)

fun Uri.isAuthorityExternalStorageDocument(): Boolean =
    UtilKUri.isAuthorityExternalStorageDocument(this)

fun Uri.isAuthorityMediaDocument(): Boolean =
    UtilKUri.isAuthorityMediaDocument(this)

object UtilKUri : BaseUtilK() {

    /**
     * 获取PackageUri
     */
    @JvmStatic
    fun getPackageUri(context: Context): Uri =
        Uri.parse("package:${UtilKContext.getPackageName(context)}")

    /**
     * 获取PackageUri
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
}
