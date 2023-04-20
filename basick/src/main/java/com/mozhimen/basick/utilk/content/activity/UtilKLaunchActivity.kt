package com.mozhimen.basick.utilk.content.activity

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKContextStart
import com.mozhimen.basick.utilk.content.UtilKIntent

/**
 * @ClassName UtilKPermission
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/7 15:17
 * @Version 1.0
 */
object UtilKLaunchActivity {
    /**
     * 安装
     * if sdk >= 24 add provider
     * @param apkPathWithName String
     */
    @JvmStatic
    fun startInstall(context: Context, apkPathWithName: String) {
        UtilKContextStart.startContext(context, UtilKIntent.getInstall(apkPathWithName) ?: return)
    }

    /**
     * 打开包安装权限
     * @param activity Activity
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    fun startManageInstallSource(activity: Activity) {
        UtilKContextStart.startActivity(activity, UtilKIntent.getManageInstallSource(activity))
    }

    /**
     * 打开包安装权限
     * @param context Context
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_26_8_O)
    @TargetApi(CVersionCode.V_26_8_O)
    fun startManageInstallSource(context: Context) {
        UtilKContextStart.startContext(context, UtilKIntent.getManageInstallSource(context))
    }

    /**
     * 设置申请权限 当系统在23及以上
     * @param context Context
     */
    @JvmStatic
    @RequiresApi(CVersionCode.V_30_11_R)
    fun startManageOverlay(context: Context) {
        UtilKContextStart.startContext(context, UtilKIntent.getManageOverlay(context))
    }

    /**
     * 设置申请权限 当系统在11及以上
     * @param activity Activity
     */
    @JvmStatic
    @RequiresPermission(CPermission.MANAGE_EXTERNAL_STORAGE)
    fun startManageAll(activity: Activity) {
        //if (!Environment.isExternalStorageManager()) {// 没文件管理权限时申请权限
        UtilKContextStart.startActivity(activity, UtilKIntent.getManageAll(activity))
        //}
    }

    /**
     * 设置申请app权限
     * @param activity Activity
     */
    @JvmStatic
    fun startSettingAppDetails(activity: Activity) {
        UtilKContextStart.startActivity(activity, UtilKIntent.getSettingAppDetails(activity))
    }

    /**
     * 设置申请无障碍权限
     * @param activity Activity
     */
    @JvmStatic
    fun startSettingAccessibility(activity: Activity) {
        UtilKContextStart.startActivity(activity, UtilKIntent.getSettingAccessibility())
    }

    /**
     * 设置申请无障碍权限
     * @param context Context
     */
    @JvmStatic
    fun startSettingAccessibility(context: Context) {
        UtilKContextStart.startContext(context, UtilKIntent.getSettingAccessibility())
    }
}