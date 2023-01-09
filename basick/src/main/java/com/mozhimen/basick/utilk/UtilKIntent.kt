package com.mozhimen.basick.utilk

import android.content.Intent
import com.mozhimen.basick.utilk.context.UtilKActivity
import com.mozhimen.basick.utilk.context.UtilKApplication

/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/2 11:41
 * @Version 1.0
 */
object UtilKIntent {
    private val _context = UtilKApplication.instance.get()

    /**
     * 获取启动App的Intent
     * @param packageName String
     * @return Intent?
     */
    @JvmStatic
    fun getLauncherActivityIntent(packageName: String): Intent? {
        val launcherActivityName: String = UtilKActivity.getLauncherActivityName(packageName)
        if (UtilKString.isHasSpace(launcherActivityName) || launcherActivityName.isEmpty()) return _context.packageManager.getLaunchIntentForPackage(_context.packageName)
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            setClassName(packageName, launcherActivityName)
        }
        return intent
    }
}