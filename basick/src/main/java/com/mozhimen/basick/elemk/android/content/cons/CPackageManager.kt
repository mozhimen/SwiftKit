package com.mozhimen.basick.elemk.android.content.cons

import android.content.pm.PackageManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CPackageManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 17:35
 * @Version 1.0
 */
object CPackageManager {

    @RequiresApi(CVersCode.V_28_9_P)
    const val GET_SIGNING_CERTIFICATES = PackageManager.GET_SIGNING_CERTIFICATES
    const val GET_SIGNATURES = PackageManager.GET_SIGNATURES
    const val GET_ACTIVITIES = PackageManager.GET_ACTIVITIES
    const val GET_CONFIGURATIONS = PackageManager.GET_CONFIGURATIONS
    const val MATCH_DEFAULT_ONLY = PackageManager.MATCH_DEFAULT_ONLY

    const val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED

    const val COMPONENT_ENABLED_STATE_DISABLED = PackageManager.COMPONENT_ENABLED_STATE_DISABLED
    const val COMPONENT_ENABLED_STATE_DISABLED_USER = PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
    const val COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED = PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED

    const val FEATURE_CAMERA_FRONT = PackageManager.FEATURE_CAMERA_FRONT
    const val FEATURE_CAMERA = PackageManager.FEATURE_CAMERA
}