package com.mozhimen.basicsk.crashk

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @ClassName CrashKLifeCycleCallback
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/10 14:42
 * @Version 1.0
 */
class CrashKLifeCycleCallback : Application.ActivityLifecycleCallbacks {

    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun removeAllActivities() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }

    override fun onActivityCreated(activity: Activity, p1: Bundle?) {
        addActivity(activity)
    }

    override fun onActivityStarted(p0: Activity) {}

    override fun onActivityResumed(p0: Activity) {}

    override fun onActivityPaused(p0: Activity) {}

    override fun onActivityStopped(p0: Activity) {}

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }
}