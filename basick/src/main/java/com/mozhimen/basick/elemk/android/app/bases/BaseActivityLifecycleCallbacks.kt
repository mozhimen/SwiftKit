package com.mozhimen.basick.elemk.android.app.bases

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @ClassName BaseActivityLifecycleCallbacks
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/11
 * @Version 1.0
 */
open class BaseActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}
