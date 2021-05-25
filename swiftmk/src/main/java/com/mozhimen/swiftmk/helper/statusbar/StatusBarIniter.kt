package com.mozhimen.swiftmk.helper.statusbar

import android.app.Activity
import com.mozhimen.swiftmk.utils.statusbar.StatusBarUtil

/**
 * @ClassName StatusBarIniter
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:09
 * @Version 1.0
 */
/**
 * 作用: 实现透明状态栏
 * 用法:
 */
object StatusBarIniter {

    fun initStatusBar(activity: Activity) {
        val statusBarAnnor = activity.javaClass.getAnnotation(StatusBarAnnor::class.java)
        statusBarAnnor?.let {
            if (statusBarAnnor.isImmersed) {
                StatusBarUtil.setTranslucentStatus(activity)//设置状态栏透明,沉浸式
            } else {
                if (statusBarAnnor.statusBarColor != -1) {
                    //设置状态栏背景色
                    StatusBarUtil.setStatusBarColor(activity, activity.resources.getColor(statusBarAnnor.statusBarColor))
                }
            }
            //设置状态栏文字和Icon是否为深色
            StatusBarUtil.setImmersiveStatusBar(activity, statusBarAnnor.isTextAndIconDark, statusBarAnnor.isImmersed)
        }
    }
}