package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.Uri
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.bases.BaseUtilK


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
    @JvmStatic
    fun get(strUri: String): Uri =
        parse(strUri)

    @JvmStatic
    fun get(scheme: String, ssp: String, fragment: String): Uri =
        fromParts(scheme, ssp, fragment)

    //获取PackageUri
    @JvmStatic
    fun getPackage(context: Context): Uri =
        Uri.parse("package:${UtilKContext.getPackageName(context)}")

    //获取PackageUri
    @JvmStatic
    fun getPackage_ofParts(context: Context): Uri =
        Uri.fromParts("package", UtilKContext.getPackageName(context), null)

    @JvmStatic
    fun fromParts(scheme: String, ssp: String, fragment: String): Uri =
        Uri.fromParts(scheme, ssp, fragment)

    @JvmStatic
    fun parse(strUri: String): Uri =
        Uri.parse(strUri)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAuthorityDownloadsDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_PROVIDERS_DOWNLOADS_DOCUMENTS//"com.android.providers.downloads.documents"

    @JvmStatic
    fun isAuthorityExternalStorageDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_EXTERNALSTORAGE_DOCUMENTS//"com.android.externalstorage.documents"

    @JvmStatic
    fun isAuthorityMediaDocument(uri: Uri): Boolean =
        uri.authority == CStrPackage.COM_ANDROID_INTERNAL_POLICY_DECORVIEW//"com.android.providers.media.documents"
}
