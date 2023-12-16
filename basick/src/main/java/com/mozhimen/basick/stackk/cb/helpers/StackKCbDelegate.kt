package com.mozhimen.basick.stackk.cb.helpers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.cons.CStackKCons
import com.mozhimen.basick.utilk.android.app.UtilKApplicationReflect
import com.mozhimen.basick.utilk.android.app.isFinishingOrDestroyed
import java.lang.ref.WeakReference

/**
 * @ClassName StackKCbProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 2:04
 * @Version 1.0
 */
internal class StackKCbDelegate : IStackK {
    private val _activityRefs = ArrayList<WeakReference<Activity>>()
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private var _activityLaunchCount = 0
    private var _isFront = true

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun init() {
        UtilKApplicationReflect.instance.get().registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
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

    override fun getStackTopActivity(): Activity? =
        getStackTopActivityRef()?.get()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        getStackTopActivityRef(onlyAlive)?.get()

    override fun getStackTopActivityRef(): WeakReference<Activity>? =
        getStackTopActivityRef(true)

    override fun getStackTopActivityRef(onlyAlive: Boolean): WeakReference<Activity>? {
        if (getStackCount() <= 0) {
            return null
        } else {
            val activityRef: WeakReference<Activity> = _activityRefs[getStackCount() - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if (activity == null || activity.isFinishingOrDestroyed()) {
                    _activityRefs.remove(activityRef)
                    return getStackTopActivityRef(onlyAlive)
                }
            }
            return WeakReference(activity)
        }
    }

    override fun getActivityRefs(): ArrayList<WeakReference<Activity>> =
        _activityRefs

    override fun getStackCount(): Int =
        getActivityRefs().size

    override fun getLaunchCount(): Int =
        _activityLaunchCount

    override fun finishAllActivity() {
        for (activityRef in _activityRefs) {
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

    override fun finishAllInvisibleActivity() {
        val stackTopActivityRef = getStackTopActivityRef()
        for (activityRef in _activityRefs) {
            if (activityRef == stackTopActivityRef) continue
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

    override fun isFront(): Boolean =
        _isFront

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
                onFrontBackChanged(true.also { _isFront = true }, activity)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            _activityLaunchCount--
            if (_activityLaunchCount <= 0 && _isFront) {
                onFrontBackChanged(false.also { _isFront = false }, activity)
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

        ////////////////////////////////////////////////////////////

        private fun onFrontBackChanged(isFront: Boolean, activity: Activity) {
            for (listener in _frontBackListeners) {
                listener.onChanged(isFront, WeakReference(activity))
            }
        }
    }

    private fun postEventFirstActivity() {
        if (getStackCount() == 1) PostKEventLiveData.instance.with<Boolean>(CStackKCons.Event.STACKK_FIRST_ACTIVITY).setValue(true)
    }
}