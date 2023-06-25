package com.mozhimen.basick.statusbark

import android.app.Activity
import com.mozhimen.basick.utilk.res.UtilKRes
import com.mozhimen.basick.utilk.os.UtilKUiMode
import com.mozhimen.basick.statusbark.annors.AStatusBarK
import com.mozhimen.basick.statusbark.annors.AStatusBarKType
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBarFontIcon

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
 * class StatusBarKActivity : BaseActivity<ActivityStatusbarkBinding, BaseViewModel>() {
 *  override fun initFlag() {
 *      StatusBarK.initStatusBar(this)
 *  }}
 */
object StatusBarK {

    @JvmStatic
    fun initStatusBar(activity: Activity) {
        val statusBarAnnor: AStatusBarK =
            activity.javaClass.getAnnotation(AStatusBarK::class.java) ?: AStatusBarK(statusBarType = AStatusBarKType.FULL_SCREEN)

        when (statusBarAnnor.statusBarType) {
            AStatusBarKType.FULL_SCREEN -> UtilKStatusBar.setFullScreen(activity)//设置状态栏全屏
            AStatusBarKType.IMMERSED -> {
                UtilKStatusBar.setImmersed(activity)//设置状态栏沉浸式
                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
            }

            else -> {
                val statusBarColor = if (statusBarAnnor.isFollowSystem) {
                    if (UtilKUiMode.isOSLightMode()) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
                } else {
                    if (statusBarAnnor.isFontIconDark) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
                }
                UtilKStatusBar.setStatusBarColor(activity, UtilKRes.getColor(statusBarColor))
                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
            }
        }
    }
}