package com.mozhimen.stackk.commons

import com.mozhimen.stackk.impls.StackKActivityLifecycleCallbacks

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
    fun addStackKActivityLifecycleCallbacks(listener: StackKActivityLifecycleCallbacks)

    /**
     * 移除栈监听器
     */
    fun removeStackKActivityLifecycleCallbacks(listener: StackKActivityLifecycleCallbacks)

    /**
     * 获取监听器集合
     */
    fun getStackKActivityLifecycleCallbacks(): List<StackKActivityLifecycleCallbacks>
}