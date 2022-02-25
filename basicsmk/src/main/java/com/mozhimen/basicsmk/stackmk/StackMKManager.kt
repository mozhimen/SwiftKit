package com.mozhimen.basicsmk.stackmk


import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.mozhimen.basicsmk.utilmk.UtilMKGlobal
import java.lang.ref.WeakReference
import java.util.*

class StackMKManager private constructor() {
    companion object {
        val instance = StackMKManagerHolder.holder
    }

    private object StackMKManagerHolder {
        val holder = StackMKManager()
    }

    interface FrontBackCallback {
        fun onChanged(isFront: Boolean)
    }

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontBackCallback = ArrayList<FrontBackCallback>()
    private var activityStartCount = 0
    private var isFront = true

    fun init() {
        UtilMKGlobal.instance.getApp()!!
            .registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     */
    fun getTopActivity(onlyAlive: Boolean = true): Activity? {
        if (activityRefs.size <= 0) {
            return null
        } else {
            val activityRef = activityRefs[activityRefs.size - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if (onlyAlive) {
                    if (activity == null || activity.isFinishing || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)) {
                        activityRefs.remove(activityRef)
                        return getTopActivity(onlyAlive)
                    }
                }
            }
            return activity
        }
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        if (!frontBackCallback.contains(callback)) {
            frontBackCallback.add(callback)
        }
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallback.remove(callback)
    }

    fun finishAll() {
        for (activityRef in activityRefs) {
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        activityRefs.clear()
    }

    //region # private function
    inner class InnerActivityLifecycleCallbacks() : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //activityStartCount>0 说明应用处在可见状态, 也就是前台
            //!isFront 之前是不是在后台
            if (!isFront && activityStartCount > 0) {
                isFront = true
                onFrontBackChanged(isFront)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && isFront) {
                isFront = false
                onFrontBackChanged(isFront)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef != null && activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }
    }

    private fun onFrontBackChanged(isFront: Boolean) {
        for (callback in frontBackCallback) {
            callback.onChanged(isFront)
        }
    }
    //endregion
}