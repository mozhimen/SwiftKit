package com.mozhimen.componentk.statusbark

import android.app.Activity
import android.app.StatusBarManager
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKTheme
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.annors.StatusBarKType
import com.mozhimen.componentk.statusbark.helpers.StatusBarKHelper

/**
 * @ClassName StatusBarK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
/**
 * 作用: 状态栏管理
 * 用法:
 * @StatusBarKAnnor(statusBarType = StatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
 * class StatusBarKActivity : BaseKActivity<ActivityStatusbarkBinding, BaseKViewModel>(R.layout.activity_statusbark) {
 *  override fun initFlag() {
 *      StatusBarK.initStatusBar(this)
 *  }}
 */
object StatusBarK {

    fun initStatusBar(activity: Activity) {
        val statusBarAnnor =
            activity.javaClass.getAnnotation(StatusBarKAnnor::class.java) ?: throw Exception("you need add annotation StatusBarAnnor")

        when (statusBarAnnor.statusBarType) {
            StatusBarKType.FULL_SCREEN -> {
                StatusBarKHelper.setStatusBarFullScreen(activity)//设置状态栏全屏
            }
            StatusBarKType.IMMERSED -> {
                StatusBarKHelper.setStatusBarImmersed(activity)//设置状态栏沉浸式
                StatusBarKHelper.setStatusBarFontIcon(activity, statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
            }
            else -> {
                val statusBarColor = if (statusBarAnnor.isFollowSystem) {
                    if (UtilKTheme.isOSLightMode()) statusBarAnnor.bgColorLight
                    else statusBarAnnor.bgColorDark
                } else statusBarAnnor.bgColorLight
                val isFontIconDark = if (statusBarAnnor.isFollowSystem) UtilKTheme.isOSLightMode() else statusBarAnnor.isFontIconDark
                StatusBarKHelper.setStatusBarColor(activity, UtilKRes.getColor(statusBarColor))
                StatusBarKHelper.setStatusBarFontIcon(activity, isFontIconDark)//设置状态栏文字和Icon是否为深色
            }
        }
    }
}