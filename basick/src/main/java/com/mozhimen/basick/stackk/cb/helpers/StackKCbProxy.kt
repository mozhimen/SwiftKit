package com.mozhimen.basick.stackk.cb.helpers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.cons.CStackKEvent
import com.mozhimen.basick.utilk.android.app.UtilKApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.app.isFinishingOrDestroyed
import com.mozhimen.basick.utilk.android.os.UtilKBuildVers
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKDataBus
import java.lang.ref.WeakReference

/**
 * @ClassName StackKCbProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 2:04
 * @Version 1.0
 */
internal class StackKCbProxy : IStackK {
    private val _activityRefs = ArrayList<WeakReference<Activity>>()
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private var _activityLaunchCount = 0
    private var _isFront = true

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun init() {
        UtilKApplication.instance.get().registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    override fun getStackTopActivity(): Activity? =
        getStackTopActivity(true)

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? {
        if (getStackCount() <= 0) {
            return null
        } else {
            val activityRef: WeakReference<Activity> = _activityRefs[getStackCount() - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if (activity == null || activity.isFinishingOrDestroyed()) {
                    _activityRefs.remove(activityRef)
                    return getStackTopActivity(onlyAlive)
                }
            }
            return activity
        }
    }

    override fun addFrontBackListener(listener: IStackKListener) {
        if (!_frontBackListeners.contains(listener)) {
            _frontBackListeners.add(listener)
        }
    }

    override fun removeFrontBackListener(listener: IStackKListener) {
        if (_frontBackListeners.contains(listener)) {
            _frontBackListeners.remove(listener)
        }
    }

    override fun getFrontBackListeners(): ArrayList<IStackKListener> =
        _frontBackListeners

    override fun finishAllActivity() {
        for (activityRef in _activityRefs) {
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

    override fun isFront(): Boolean =
        _isFront

    override fun getActivityRefs(): ArrayList<WeakReference<Activity>> =
        _activityRefs

    override fun getStackCount(): Int =
        getActivityRefs().size

    override fun getLaunchCount(): Int =
        _activityLaunchCount

    //////////////////////////////////////////////////////////////////////////////////////////////

    private inner class InnerActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            _activityRefs.add(WeakReference(activity))
            postEventFirstActivity()
        }

        override fun onActivityStarted(activity: Activity) {
            _activityLaunchCount++
            //_activityLaunchCount>0 说明应用处在可见状态, 也就是前台
            //!isFront 之前是不是在后台
            if (!_isFront && _activityLaunchCount > 0) {
                onFrontBackChanged(true.also { _isFront = true })
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            _activityLaunchCount--
            if (_activityLaunchCount <= 0 && _isFront) {
                onFrontBackChanged(false.also { _isFront = false })
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in _activityRefs) {
                if (activityRef.get() == activity) {
                    _activityRefs.remove(activityRef)
                    break
                }
            }
        }

        private fun onFrontBackChanged(isFront: Boolean) {
            for (listener in _frontBackListeners) {
                listener.onChanged(isFront)
            }
        }
    }

    private fun postEventFirstActivity() {
        if (getStackCount() == 1) {
            UtilKDataBus.with<Boolean>(CStackKEvent.STACKK_FIRST_ACTIVITY).setValue(true)
        }
    }
}