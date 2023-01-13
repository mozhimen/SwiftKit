package com.mozhimen.componentktest.installk

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CManifest
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.utilk.UtilKAsset
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.app.UtilKAppInstall
import com.mozhimen.basick.utilk.exts.showToast
import com.mozhimen.basick.utilk.file.UtilKFile
import com.mozhimen.componentk.installk.InstallK
import com.mozhimen.componentk.installk.commons.IInstallStateChangedListener
import com.mozhimen.componentk.installk.cons.EInstallMode
import com.mozhimen.componentk.installk.cons.EPermissionType
import com.mozhimen.componentktest.databinding.ActivityInstallkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @ClassName InstallKActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/12 14:02
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET,
    CPermission.REQUEST_INSTALL_PACKAGES,
    CPermission.INSTALL_PACKAGES,
    CPermission.READ_INSTALL_SESSIONS,
    CPermission.REPLACE_EXISTING_PACKAGE,
    CPermission.BIND_ACCESSIBILITY_SERVICE,
    CManifest.PROVIDER,
    CManifest.SERVICE
)
@APermissionCheck(
    CPermission.READ_EXTERNAL_STORAGE,
    CPermission.WRITE_EXTERNAL_STORAGE,
    CPermission.INTERNET,
    CPermission.READ_INSTALL_SESSIONS,
)
class InstallKActivity : BaseActivityVB<ActivityInstallkBinding>() {
    private val _apkPathWithName by lazy { this.filesDir.absolutePath + "/installk/componentktest.apk" }
    private val _installK by lazy { InstallK() }

    override fun initView(savedInstanceState: Bundle?) {
        vb.installkBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                if (!UtilKFile.isFileExist(_apkPathWithName)) {
                    UtilKAsset.asset2File("componentktest.apk", _apkPathWithName, false)
                }
                delay(500)
                _installK.setInstallMode(EInstallMode.SMART).setInstallSmartService(InstallKService::class.java).setInstallStateChangeListener(object : IInstallStateChangedListener {
                    override fun onInstallStart() {
                        Log.d(TAG, "onInstallStart:")
                    }

                    override fun onInstallFinish() {
                        Log.d(TAG, "onInstallFinish:")
                    }

                    override fun onInstallFail(msg: String?) {
                        Log.e(TAG, "onInstallFail: ${msg ?: ""}")
                    }

                    override fun onNeedPermissions(type: EPermissionType) {
                        Log.w(TAG, "onNeedPermissions: $type")
                        when (type) {
                            EPermissionType.COMMON -> {
                                ManifestKPermission.initPermissions(this@InstallKActivity, onSuccess = { "权限申请成功".showToast() })
                            }
                            EPermissionType.INSTALL -> {
                                if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
                                    UtilKAppInstall.openSettingAppInstall(this@InstallKActivity)
                                }
                            }
                            EPermissionType.ACCESSIBILITY -> {
                                UtilKPermission.openSettingAccessibility(this@InstallKActivity)
                            }
                            else -> {}
                        }
                    }

                }).install(_apkPathWithName)
            }
        }
    }
}