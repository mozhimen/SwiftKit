package com.mozhimen.basick.utilk

import android.content.Intent


/**
 * @ClassName UtilKIntent
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/2 11:41
 * @Version 1.0
 */
object UtilKIntent {
    /**
     * 获取启动AppIntent
     * @param packageName String
     * @return Intent?
     */
    @JvmStatic
    fun getLaunchAppIntent(packageName: String): Intent? {
        val launcherActivity: String = UtilKActivity.getLauncherActivity(packageName)
        if (UtilKString.isHasSpace(launcherActivity)) return null
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setClassName(packageName, launcherActivity)
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}