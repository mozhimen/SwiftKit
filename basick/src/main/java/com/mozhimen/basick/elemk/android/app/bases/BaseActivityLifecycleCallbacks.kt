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
    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.v(TAG,"onActivityPreCreated___________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState" )
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.d(TAG,"onActivityCreated______________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState" )
    }

    override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.v(TAG,"onActivityPostCreated__________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState" )
    }

    override fun onActivityPreStarted(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPreStarted___________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityStarted(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityStarted______________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPostStarted(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPostStarted__________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPreResumed(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPreResumed___________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityResumed(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityResumed______________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPostResumed(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPostResumed__________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPrePaused(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPrePaused____________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPaused(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityPaused_______________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPostPaused(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPostPaused___________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPreStopped(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPreStopped___________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityStopped(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityStopped______________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPostStopped(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPostStopped__________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
        UtilKLogWrapper.v(TAG,"onActivityPreSaveInstanceState_ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} outState $outState")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        UtilKLogWrapper.d(TAG,"onActivitySaveInstanceState____ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} outState $outState")
    }

    override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
        UtilKLogWrapper.v(TAG,"onActivityPostSaveInstanceState activity ${activity.javaClass.simpleName} ${activity.javaClass.name} outState $outState")
    }

    override fun onActivityPreDestroyed(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPreDestroyed_________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityDestroyed(activity: Activity) {
        UtilKLogWrapper.d(TAG,"onActivityDestroyed____________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }

    override fun onActivityPostDestroyed(activity: Activity) {
        UtilKLogWrapper.v(TAG,"onActivityPostDestroyed________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name}")
    }
}
