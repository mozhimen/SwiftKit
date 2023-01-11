package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.elemk.receiver.bases.BaseDownloadInstallReceiver
import com.mozhimen.basick.permissionk.annors.APermissionKRequire


/**
 * @ClassName ElemKDownloadInstallReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 12:46
 * @Version 1.0
 */
@APermissionKRequire(CPermission.INSTALL_PACKAGES, CPermission.REQUEST_INSTALL_PACKAGES, CPermission.READ_INSTALL_SESSIONS, CPermission.REPLACE_EXISTING_PACKAGE)
class ElemKDownloadInstallReceiver : BaseDownloadInstallReceiver("")