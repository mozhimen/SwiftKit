package com.mozhimen.basick.sensek.systembar

import android.app.Activity
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.androidx.appcompat.UtilKUiMode
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBar
import com.mozhimen.basick.sensek.systembar.annors.ASenseKSystemBarType
import com.mozhimen.basick.sensek.systembar.cons.CSystemBarType
import com.mozhimen.basick.utilk.android.view.UtilKStatusBar
import com.mozhimen.basick.utilk.android.view.UtilKStatusBarFontIcon
import com.mozhimen.basick.utilk.android.view.UtilKSystemBar

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
object SenseKSystemBar {

    @JvmStatic
    fun init(activity: Activity) {
        val statusBarAnnor: ASenseKSystemBar =
            activity.javaClass.getAnnotation(ASenseKSystemBar::class.java) ?: ASenseKSystemBar(systemBarType = CSystemBarType.NORMAL)
        val systemBarType = statusBarAnnor.systemBarType
        val isFollowSystem = statusBarAnnor.isFollowSystem
        val isFontIconDark = statusBarAnnor.isFontIconDark
        val bgColorLight = statusBarAnnor.bgColorLight
        val bgColorDark = statusBarAnnor.bgColorDark

        when (statusBarAnnor.systemBarType) {
            CSystemBarType.NORMAL -> {

            }

            CSystemBarType.LOW_PROFILE -> {

            }

            CSystemBarType.IMMERSED_LIGHT -> {
                UtilKSystemBar.setImmersed(activity)
            }

            CSystemBarType.IMMERSED_FORCE -> {

            }

            CSystemBarType.IMMERSED_STICKY -> {

            }

            CSystemBarType.EXPAND_STATUS_BAR -> {

            }

            CSystemBarType.EXPAND_NAVIGATION_BAR -> {

            }

            CSystemBarType.EXPAND_ALL -> {

            }

//            ASenseKSystemBarType.FULL_SCREEN -> UtilKSystemBar.setImmersed(activity)//设置状态栏全屏
//            ASenseKSystemBarType.IMMERSED -> {
//                UtilKStatusBar.setImmersed(activity)//设置状态栏沉浸式
//                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
//            }
//
//            else -> {
//                val statusBarColor = if (statusBarAnnor.isFollowSystem) {
//                    if (UtilKUiMode.isOSLightMode()) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
//                } else {
//                    if (statusBarAnnor.isFontIconDark) statusBarAnnor.bgColorLight else statusBarAnnor.bgColorDark
//                }
//                UtilKStatusBar.setStatusBarColor(activity, UtilKRes.getColor(statusBarColor))
//                UtilKStatusBarFontIcon.setStatusBarFontIcon(activity, if (statusBarAnnor.isFollowSystem) UtilKUiMode.isOSLightMode() else statusBarAnnor.isFontIconDark)//设置状态栏文字和Icon是否为深色
//            }
        }
    }
}