package com.mozhimen.basick.stackk.utils

import android.app.Activity
import android.app.Application
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.stackk.cons.SLifecycleCallbackEvent

/**
 * @ClassName StackKUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/7/23
 * @Version 1.0
 */
object StackKUtil {
    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun onLifecycleChangedPre(activity: Activity, event: SLifecycleCallbackEvent, callback: Application.ActivityLifecycleCallbacks) {
        when (event) {
            is SLifecycleCallbackEvent.ON_CREATE -> callback.onActivityPreCreated(activity, event.savedInstanceState)
            SLifecycleCallbackEvent.ON_START -> callback.onActivityPreStarted(activity)
            SLifecycleCallbackEvent.ON_RESUME -> callback.onActivityPreResumed(activity)
            SLifecycleCallbackEvent.ON_PAUSE -> callback.onActivityPrePaused(activity)
            SLifecycleCallbackEvent.ON_STOP -> callback.onActivityPreStopped(activity)
            SLifecycleCallbackEvent.ON_DESTROY -> callback.onActivityPreDestroyed(activity)
            is SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE -> callback.onActivityPreSaveInstanceState(activity, event.outState)
        }
    }

    @JvmStatic
    fun onLifecycleChangedAt(activity: Activity, event: SLifecycleCallbackEvent, callback: Application.ActivityLifecycleCallbacks) {
        when (event) {
            is SLifecycleCallbackEvent.ON_CREATE -> callback.onActivityCreated(activity, event.savedInstanceState)
            SLifecycleCallbackEvent.ON_START -> callback.onActivityStarted(activity)
            SLifecycleCallbackEvent.ON_RESUME -> callback.onActivityResumed(activity)
            SLifecycleCallbackEvent.ON_PAUSE -> callback.onActivityPaused(activity)
            SLifecycleCallbackEvent.ON_STOP -> callback.onActivityStopped(activity)
            SLifecycleCallbackEvent.ON_DESTROY -> callback.onActivityDestroyed(activity)
            is SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE -> callback.onActivitySaveInstanceState(activity, event.outState)
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    fun onLifecycleChangedPost(activity: Activity, event: SLifecycleCallbackEvent, callback: Application.ActivityLifecycleCallbacks) {
        when (event) {
            is SLifecycleCallbackEvent.ON_CREATE -> callback.onActivityPostCreated(activity, event.savedInstanceState)
            SLifecycleCallbackEvent.ON_START -> callback.onActivityPostStarted(activity)
            SLifecycleCallbackEvent.ON_RESUME -> callback.onActivityPostResumed(activity)
            SLifecycleCallbackEvent.ON_PAUSE -> callback.onActivityPostPaused(activity)
            SLifecycleCallbackEvent.ON_STOP -> callback.onActivityPostStopped(activity)
            SLifecycleCallbackEvent.ON_DESTROY -> callback.onActivityPostDestroyed(activity)
            is SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE -> callback.onActivityPostSaveInstanceState(activity, event.outState)
        }
    }
}