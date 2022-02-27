package com.mozhimen.basicsmk.statusbarmk

import android.app.Activity

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
object StatusBarMK {

    fun initStatusBar(activity: Activity) {
        val statusBarAnnor = activity.javaClass.getAnnotation(StatusBarMKAnnor::class.java) ?: return
        if (statusBarAnnor.isImmersed) {
            StatusBarMKHelper.setTranslucentStatus(activity)//设置状态栏透明,沉浸式
        } else {
            if (statusBarAnnor.statusBarColor != -1) {
                //设置状态栏背景色
                StatusBarMKHelper.setStatusBarColor(
                    activity,
                    activity.resources.getColor(statusBarAnnor.statusBarColor)
                )
            }
        }
        //设置状态栏文字和Icon是否为深色
        StatusBarMKHelper.setImmersiveStatusBar(
            activity,
            statusBarAnnor.isTextAndIconDark,
            statusBarAnnor.isImmersed
        )
    }
}