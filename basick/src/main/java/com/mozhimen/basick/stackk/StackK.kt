package com.mozhimen.basick.stackk

import android.app.Activity
import android.os.Build
import com.mozhimen.basick.stackk.commons.IStackKListener
import java.lang.ref.WeakReference

/**
 * @ClassName StackK
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/21 13:00
 * @Version 1.0
 */
object StackK {
    /**
     * 找出栈顶不为空，且没有被销毁的activity
     * @param onlyAlive Boolean
     * @return Activity?
     */
    fun getStackTopActivity(onlyAlive: Boolean = true): Activity? {
        return StackKMgr.instance.getStackTopActivity(onlyAlive)
    }

    /**
     * 增加栈监听器
     * @param listener StackKListener
     */
    fun addFrontBackListener(listener: IStackKListener) {
        StackKMgr.instance.addFrontBackListener(listener)
    }

    /**
     * 移除栈监听器
     * @param listener StackKListener
     */
    fun removeFrontBackListener(listener: IStackKListener) {
        StackKMgr.instance.removeFrontBackListener(listener)
    }

    /**
     * 结束所有堆栈
     */
    fun finishAllActivity() {
        StackKMgr.instance.finishAllActivity()
    }

    /**
     * 是否在前台
     * @return Boolean
     */
    fun isFront(): Boolean =
        StackKMgr.instance.isFront()

    /**
     * 获取堆栈数量
     * @return Int
     */
    fun getStackCount(): Int =
        StackKMgr.instance.getActivityRefs().size
}