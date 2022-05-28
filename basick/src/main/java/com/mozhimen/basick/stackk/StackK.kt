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
        val activityRefs = StackKMgr.instance.getActivityRefs()
        if (activityRefs.size <= 0) {
            return null
        } else {
            val activityRef: WeakReference<Activity> = activityRefs[activityRefs.size - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if (activity == null || activity.isFinishing || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)) {
                    activityRefs.remove(activityRef)
                    return getStackTopActivity(onlyAlive)
                }
            }
            return activity
        }
    }

    /**
     * 增加栈监听器
     * @param listener StackKListener
     */
    fun addFrontBackListener(listener: IStackKListener) {
        val listeners = StackKMgr.instance.getListeners()
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    /**
     * 移除栈监听器
     * @param listener StackKListener
     */
    fun removeFrontBackListener(listener: IStackKListener) {
        StackKMgr.instance.getListeners().remove(listener)
    }

    /**
     * 结束所有堆栈
     */
    fun finishAllActivity() {
        val activityRefs = StackKMgr.instance.getActivityRefs()
        for (activityRef in activityRefs) {
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        activityRefs.clear()
    }

    /**
     * 是否在前台
     * @return Boolean
     */
    fun isFront() = StackKMgr.instance.isFront()

    /**
     * 获取堆栈数量
     * @return Int
     */
    fun getStackCount() = StackKMgr.instance.getActivityRefs().size
}