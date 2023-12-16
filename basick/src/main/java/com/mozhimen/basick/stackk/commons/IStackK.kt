package com.mozhimen.basick.stackk.commons

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * @ClassName IStackK
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 1:21
 * @Version 1.0
 */
interface IStackK {
    fun init()

    /////////////////////////////////////////////////////////////////

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     * @return Activity?
     */
    fun getStackTopActivity(): Activity?

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     * @param onlyAlive Boolean
     * @return Activity?
     */
    fun getStackTopActivity(onlyAlive: Boolean): Activity?

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     * @return Activity?
     */
    fun getStackTopActivityRef(): WeakReference<Activity>?

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     * @param onlyAlive Boolean
     * @return Activity?
     */
    fun getStackTopActivityRef(onlyAlive: Boolean): WeakReference<Activity>?

    /**
     * 获取activity集合
     * @return List<WeakReference<Activity>>
     */
    fun getActivityRefs(): ArrayList<WeakReference<Activity>>

    /////////////////////////////////////////////////////////////////

    /**
     * 增加栈监听器
     * @param listener StackKListener
     */
    fun addFrontBackListener(listener: IStackKListener)

    /**
     * 移除栈监听器
     * @param listener StackKListener
     */
    fun removeFrontBackListener(listener: IStackKListener)

    /**
     * 获取监听器集合
     * @return List<StackKListener>
     */
    fun getFrontBackListeners(): ArrayList<IStackKListener>

    /////////////////////////////////////////////////////////////////

    /**
     * 结束所有堆栈
     */
    fun finishAllActivity()

    /**
     * 结束除顶部的堆栈
     */
    fun finishAllInvisibleActivity()

    /////////////////////////////////////////////////////////////////

    /**
     * 是否在前台
     * @return Boolean
     */
    fun isFront(): Boolean


    /**
     * 获取堆栈数量
     * @return Int
     */
    fun getStackCount(): Int

    /**
     * 获取启动数量
     * @return Int
     */
    fun getLaunchCount(): Int
}