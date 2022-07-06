package com.mozhimen.componentk.statusbark

import android.app.Activity
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.helpers.StatusBarKHelper

/**
 * @ClassName StatusBarK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
/**
 * 作用: 实现透明状态栏
 * 用法:
 */
object StatusBarK {

    fun initStatusBar(activity: Activity) {
        val statusBarAnnor = activity.javaClass.getAnnotation(StatusBarKAnnor::class.java) ?: return
        if (statusBarAnnor.isImmersed) {
            StatusBarKHelper.setStatusBarTranslucent(activity)//设置状态栏透明,沉浸式
        } else {
            if (statusBarAnnor.statusBarColor != -1) {
                //设置状态栏背景色
                StatusBarKHelper.setStatusBarColor(activity, UtilKRes.getColor(statusBarAnnor.statusBarColor))
            }
        }
        //设置状态栏文字和Icon是否为深色
        StatusBarKHelper.setStatusBarImmersive(activity, statusBarAnnor.isTextAndIconDark, statusBarAnnor.isImmersed)
    }
}