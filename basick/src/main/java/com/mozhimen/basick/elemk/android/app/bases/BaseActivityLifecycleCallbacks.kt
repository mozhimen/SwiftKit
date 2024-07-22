package com.mozhimen.basick.elemk.android.app.bases

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName BaseActivityLifecycleCallbacks
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/11
 * @Version 1.0
 */
open class BaseActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks, IUtilK {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.d(TAG,"onActivityCreated activity ${activity.javaClass.simpleName} savedInstanceState $savedInstanceState" )
    }

    override fun onActivityStarted(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityStarted activity ${activity.javaClass.simpleName}")
    }

    override fun onActivityResumed(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityResumed activity ${activity.javaClass.simpleName}")
    }

    override fun onActivityPaused(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityPaused activity ${activity.javaClass.simpleName}")
    }

    override fun onActivityStopped(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityStopped activity ${activity.javaClass.simpleName}")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        UtilKLogWrapper.d(TAG,"onActivitySaveInstanceState activity ${activity.javaClass.simpleName} outState $outState")

    }

    override fun onActivityDestroyed(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityDestroyed activity ${activity.javaClass.simpleName}")
    }
}
