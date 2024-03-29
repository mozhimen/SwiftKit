package com.mozhimen.basick.manifestk.permission.scoped.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import com.mozhimen.basick.elemk.android.content.cons.CIntent
import com.mozhimen.basick.elemk.android.provider.cons.CDocumentsContract
import com.mozhimen.basick.manifestk.permission.scoped.cons.CManifestKPermissionScoped
import com.mozhimen.basick.utilk.android.content.UtilKContentResolver
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.containStr
import com.mozhimen.basick.utilk.kotlin.strUri2uri
import com.mozhimen.basick.utilk.kotlin.text.removeEnd_ofSeparator


/**
 * @ClassName ScopedPermissionUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/11 0:56
 * @Version 1.0
 */
object ManifestKPermissionScopedUtil : IUtilK {
    @JvmStatic
    fun requestPermissionOfStrUriDocumentAndroidData(activity: Activity, requestCode: Int) {
        requestPermissionForStrFilePathOfStrUriDocumentAndroid(activity, CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA, requestCode)
    }

    @JvmStatic
    fun requestPermissionOfStrUriDocumentAndroidObb(activity: Activity, requestCode: Int) {
        requestPermissionForStrFilePathOfStrUriDocumentAndroid(activity, CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB, requestCode)
    }

    //直接获取data权限or obb，推荐使用这种方案
    @JvmStatic
    fun requestPermissionForStrFilePathOfStrUriDocumentAndroid(activity: Activity, strFilePath: String, requestCode: Int) {
        val uriDocumentAndroid: Uri = getStrUriDocumentAndroidForStrFilePath(strFilePath).strUri2uri() //调用方法，把path转换成可解析的uri文本
        val documentFile: DocumentFile? = DocumentFile.fromTreeUri(activity, uriDocumentAndroid)
        val intent = Intent(CIntent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(
                CIntent.FLAG_GRANT_READ_URI_PERMISSION
                        or CIntent.FLAG_GRANT_WRITE_URI_PERMISSION
                        or CIntent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                        or CIntent.FLAG_GRANT_PREFIX_URI_PERMISSION
            )
        }
        if (UtilKBuildVersion.isAfterV_26_8_O()) {
            intent.putExtra(CDocumentsContract.EXTRA_INITIAL_URI, documentFile!!.uri)
        }
        activity.startActivityForResult(intent, requestCode) //开始授权
    }

    ////获取指定目录的权限
    @JvmStatic
    fun requestPermissionForStrFilePath(activity: Activity, strFilePath: String, requestCode: Int) {
        val strUriDocument: String = strFilePath2strUriDocumentAndroid(strFilePath)
        val uriDocument = strUriDocument.strUri2uri()
        val intent = Intent(CIntent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                        or Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
            )
        }
        if (UtilKBuildVersion.isAfterV_26_8_O()) {
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriDocument)
        }
        activity.startActivityForResult(intent, requestCode)
    }

    @JvmStatic
    fun isPermissionStrFilePathProtectedGranted(context: Context, strFilePath: String): Boolean {
        val strUriDocument: String = getStrUriDocumentAndroidForStrFilePath(strFilePath)
        for (persistedUriPermission in UtilKContentResolver.getPersistedUriPermissions(context)) {
            if (persistedUriPermission.isReadPermission && persistedUriPermission.uri.toString() == strUriDocument) {
                return true
            }
        }
        return false
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrUriDocumentAndroidForStrFilePath(strFilePath: String): String =
        if (strFilePath.containStr(CManifestKPermissionScoped.STR_ANDROID_DATA))
            CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA
        else if (strFilePath.containStr(CManifestKPermissionScoped.STR_ANDROID_OBB))
            CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB
        else
            CManifestKPermissionScoped.STR_URI_DOCUMENT

    //根据路径获得document文件
    @JvmStatic
    fun getDocumentFile(activity: Activity, strFilePath: String): DocumentFile? =
        DocumentFile.fromSingleUri(activity, strFilePath2uriDocumentAndroid(strFilePath))

    //////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2strUriDocumentAndroid(strFilePath: String): String =
        if (strFilePath.containStr(CManifestKPermissionScoped.STR_ANDROID_DATA))
            strFilePath2strUriDocumentAndroidData(strFilePath)
        else if (strFilePath.containStr(CManifestKPermissionScoped.STR_ANDROID_OBB))
            strFilePath2strUriDocumentAndroidObb(strFilePath)
        else
            strFilePath2strUriDocument(strFilePath)

    @JvmStatic
    fun strFilePath2strUriDocumentAndroidData(strFilePath: String): String {
        val tempStrUri = strFilePath.removeEnd_ofSeparator().replace("/storage/emulated/0/", "").replace("/", "%2F")
        return "${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA}/document/primary%3A$tempStrUri"
    }

    @JvmStatic
    fun strFilePath2strUriDocumentAndroidObb(strFilePath: String): String {
        val tempStrUri = strFilePath.removeEnd_ofSeparator().replace("/storage/emulated/0/", "").replace("/", "%2F")
        return "${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB}/document/primary%3A$tempStrUri"
    }

    @JvmStatic
    fun strFilePath2strUriDocument(strFilePath: String): String {
        val tempStrUri = strFilePath.removeEnd_ofSeparator().replace("/storage/emulated/0/", "").replace("/", "%2F")
        return "${CManifestKPermissionScoped.STR_URI_DOCUMENT}%3A$tempStrUri"
    }

    //    fun strFilePath2strUriDocument1(strFilePath: String): String {
//        val tempStrUri = strFilePath.replaceEndSeparator().replace("/storage/emulated/0/", "").strUriEncode().replace("/", "%2F")
//        return "${CManifestKPermissionScoped.STR_URI_DOCUMENT}%3A$tempStrUri"
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun strFilePath2uriDocumentAndroid(strFilePath: String): Uri =
        strFilePath2strUriDocumentAndroid(strFilePath).strUri2uri()

//    fun getDocumentFilePath(context: Context, strFilePath: String): DocumentFile? {
//        var strFilePathTemp = strFilePath
//        var strFilePathPattern = "/storage/emulated/0"
//        var strUriDocument = "${CManifestKPermissionScoped.STR_URI_DOCUMENT}%3A"
//        if (strFilePathTemp.contains("/Android/data")) {
//            strFilePathPattern = "/storage/emulated/0/Android/data"
//            strUriDocument = "${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA}"
//        }
//        if (strFilePathTemp.contains("/Android/obb")) {
//            strFilePathPattern = "/storage/emulated/0/Android/obb"
//            strUriDocument = "${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB}"
//        }
//        //String treeUri = "${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA}/document/primary%3AAndroid%2Fdata";
//        var documentFile = UtilKDocumentFile.get(context, strUriDocument)
//        strFilePathTemp = strFilePathTemp.replace(strFilePathPattern, "")
//        val parts = strFilePathTemp.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        for (i in parts.indices) {
//            if (parts[i].isEmpty()) continue
//            val strFilePathEncoded = parts[i].strUriDecode()
//            if (documentFile == null) break
//            documentFile = documentFile.findFile(strFilePathEncoded)
//        }
//        return documentFile
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////
//
//    fun strUriDocument2strFilePath(strUri: String): String {
//        var strFilePathTemp = strUri
//        if (strUri.contains("${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA}"))
//            strFilePathTemp = strUri
//                .replace("${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_DATA}/document/primary%3A", "")
//                .replace("%2F", "/")
//        else if (strUri.contains("${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB}"))
//            strFilePathTemp = strUri
//                .replace("${CManifestKPermissionScoped.STR_URI_DOCUMENT_ANDROID_OBB}/document/primary%3A", "")
//                .replace("%2F", "/")
//        strFilePathTemp = Uri.decode(strFilePathTemp)
//        return "/storage/emulated/0/$strFilePathTemp"
//    }
}