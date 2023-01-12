package com.mozhimen.basicktest.elemk.receiver

import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.receiver.bases.BaseDownloadInstallReceiver
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest


/**
 * @ClassName ElemKDownloadInstallReceiver
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/6 12:46
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INSTALL_PACKAGES, CPermission.REQUEST_INSTALL_PACKAGES, CPermission.READ_INSTALL_SESSIONS, CPermission.REPLACE_EXISTING_PACKAGE, CManifest.PROVIDER)
class ElemKDownloadInstallReceiver : BaseDownloadInstallReceiver("")