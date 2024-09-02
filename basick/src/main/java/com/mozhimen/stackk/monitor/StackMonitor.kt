package com.mozhimen.stackk.monitor

import android.app.Activity
import android.content.Context
import android.os.Process
import java.util.Stack
import kotlin.system.exitProcess


/**
 * @ClassName StackMonitor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/18 11:34
 * @Version 1.0
 */
class StackMonitor {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    ////////////////////////////////////////////////////////////////////////////

    private var _activityStack: Stack<Activity> = Stack()

    /**
     * 获取栈顶activity
     * @return 得到栈顶的activity
     */
    fun getTopActivity(): Activity? =
        if (_activityStack.size > 0)
            _activityStack.lastElement()
        else null

    /**
     * 弹出栈顶
     */
    fun popTopActivity() {
        _activityStack.lastElement()?.finish()
    }

    /**
     * 弹出指定activity
     * @param activity 指定activity
     */
    fun popActivity(activity: Activity) {
        activity.finish()
        _activityStack.remove(activity)
    }

    /**
     * 将activity放入堆栈
     * @param activity 要放入的activity
     */
    fun pushActivity(activity: Activity) {
        _activityStack.add(activity)
    }

    /**
     * 弹出至某个activity，用于批量关闭顶层activity
     * @param clazz
     */
    fun popAllActivityUntil(clazz: Class<*>) {
        while (true) {
            val activity = getTopActivity() ?: break
            if (activity.javaClass == clazz) break
            popActivity(activity)
        }
    }

    /**
     * 弹出所有activity
     */
    fun popAllActivity() {
        while (true) {
            val currentActivity = getTopActivity()
            if (currentActivity != null)
                popActivity(currentActivity)
            else return
        }
    }

    /**
     * 得到栈内某个activity
     * @param clazz activity名字
     * @return 得到的activity
     */
    fun getActivity(clazz: Class<*>): Activity? {
        for (activity in _activityStack) {
            if (activity.javaClass == clazz)
                return activity
        }
        return null
    }

    /**
     * 退出应用程序
     * @param context context
     */
    fun killAll(context: Context?) {
        popAllActivity()
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    ////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = StackMonitor()
    }
}