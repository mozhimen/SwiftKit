package com.mozhimen.basicktest.elemk.android

import com.mozhimen.kotlin.elemk.android.content.bases.BaseDownloadInstallBroadcastReceiver
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_READ_INSTALL_SESSIONS
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_REPLACE_EXISTING_PACKAGE
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_REQUEST_INSTALL_PACKAGES


/**
 * @ClassName ElemKDownloadInstallReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 12:46
 * @Version 1.0
 */
@OptIn(OPermission_REQUEST_INSTALL_PACKAGES::class, OPermission_READ_INSTALL_SESSIONS::class, OPermission_REPLACE_EXISTING_PACKAGE::class)
class ElemKDownloadInstallReceiver : BaseDownloadInstallBroadcastReceiver("")