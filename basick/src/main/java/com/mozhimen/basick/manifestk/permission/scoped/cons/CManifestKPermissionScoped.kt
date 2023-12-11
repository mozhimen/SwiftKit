package com.mozhimen.basick.manifestk.permission.scoped.cons

/**
 * @ClassName CMainifestKPermissionScoped
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/11 0:40
 * @Version 1.0
 */
object CManifestKPermissionScoped {
    const val CODE_PERMISSION_REQUEST_SCOPED = 11

    ////////////////////////////////////////////////////////

    const val EXTRA_PERMISSION_REQUEST_SCOPED = "EXTRA_PERMISSION_REQUEST_SCOPED"

    ////////////////////////////////////////////////////////

    const val STR_ANDROID_DATA = "Android/data"
    const val STR_ANDROID_OBB = "Android/obb"
    const val STR_FILE_PATH_ANDROID_DATA = "Android/data"
    const val STR_FILE_PATH_ANDROID_OBB = "Android/obb"

    const val STR_URI_DOCUMENT = "content://com.android.externalstorage.documents/tree/primary"
    const val STR_URI_DOCUMENT_ANDROID_DATA = "${STR_URI_DOCUMENT}%3AAndroid%2Fdata"
    const val STR_URI_DOCUMENT_ANDROID_OBB = "${STR_URI_DOCUMENT}%3AAndroid%2Fobb"
}