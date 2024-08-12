package com.mozhimen.basick.stackk.impls

import android.app.Activity
import android.os.Bundle
import com.mozhimen.basick.elemk.android.app.bases.BaseActivityLifecycleCallbacks
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper

/**
 * @ClassName StackKActivityLifecycleCallbacks
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/12
 * @Version 1.0
 */
open class StackKActivityLifecycleCallbacks : BaseActivityLifecycleCallbacks() {
    fun onFirstActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.v(TAG, "onFirstActivityPreCreated______ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState")
    }

    fun onFirstActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.d(TAG, "onFirstActivityCreated_________ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState")
    }

    fun onFirstActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        UtilKLogWrapper.v(TAG, "onFirstActivityPostCreated_____ activity ${activity.javaClass.simpleName} ${activity.javaClass.name} savedInstanceState $savedInstanceState")
    }
}