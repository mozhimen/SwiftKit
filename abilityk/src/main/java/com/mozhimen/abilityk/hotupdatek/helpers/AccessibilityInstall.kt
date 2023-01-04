package com.mozhimen.abilityk.hotupdatek.helpers

import android.content.Intent
import android.net.Uri
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.context.UtilKApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @ClassName AccessibilityInstall
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/1/4 23:01
 * @Version 1.0
 */
object AccessibilityInstall {
    private val _context = UtilKApplication.instance.get()

    @JvmStatic
    fun openAccessibility() {
        UtilKPermission.openSettingAccessibility()
    }

    /**
     * @see [SmartInstallService]
     * @param apkPathWithName String
     */
    @JvmStatic
    @ADescription("你必须先在工程app的Manifest下注册SmartInstallService,才有效")
    suspend fun installSmart(apkPathWithName: String) {
        withContext(Dispatchers.Main) {
            val uri = Uri.fromFile(File(apkPathWithName))
            val localIntent = Intent(Intent.ACTION_VIEW)
            localIntent.setDataAndType(uri, "application/vnd.android.package-archive")
            _context.startActivity(localIntent)
        }
    }
}