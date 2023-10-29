package com.mozhimen.basick.utilk.android.provider

import android.content.Context
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.provider.cons.CSettings
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.IUtilK


/**
 * @ClassName UtilKSettings
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/3 13:48
 * @Version 1.0
 */
object UtilKSettings : IUtilK {

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.SYSTEM_ALERT_WINDOW)
    @ADescription(CSettings.ACTION_MANAGE_OVERLAY_PERMISSION)
    fun canDrawOverlays(context: Context): Boolean =
        Settings.canDrawOverlays(context)
}