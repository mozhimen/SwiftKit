package com.mozhimen.basick.elemk.android.content.cons

import android.content.pm.PackageInfo
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CPackageInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
object CPackageInfo {
    @RequiresApi(CVersCode.V_21_5_L)
    const val INSTALL_LOCATION_AUTO = PackageInfo.INSTALL_LOCATION_AUTO
}