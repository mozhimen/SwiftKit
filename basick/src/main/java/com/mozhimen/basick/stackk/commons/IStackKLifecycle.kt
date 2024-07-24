package com.mozhimen.basick.stackk.commons

import android.app.Application

/**
 * @ClassName IStackKLifecycle
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/23
 * @Version 1.0
 */
interface IStackKLifecycle {
    /**
     * 增加栈监听器
     */
    fun addActivityLifecycleCallbacks(listener: Application.ActivityLifecycleCallbacks)

    /**
     * 移除栈监听器
     */
    fun removeActivityLifecycleCallbacks(listener: Application.ActivityLifecycleCallbacks)

    /**
     * 获取监听器集合
     */
    fun getActivityLifecycleCallbacks(): List<Application.ActivityLifecycleCallbacks>
}