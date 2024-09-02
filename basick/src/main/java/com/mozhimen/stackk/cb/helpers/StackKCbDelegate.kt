package com.mozhimen.stackk.cb.helpers

import android.app.Activity
import android.os.Bundle
import com.mozhimen.kotlin.elemk.android.app.bases.BaseActivityLifecycleCallbacks
import com.mozhimen.stackk.annors.ALifecycleOpportunity
import com.mozhimen.stackk.commons.IStackK
import com.mozhimen.stackk.commons.IStackKLifecycle
import com.mozhimen.stackk.commons.IStackKListener
import com.mozhimen.stackk.cons.SLifecycleCallbackEvent
import com.mozhimen.stackk.impls.StackKActivityLifecycleCallbacks
import com.mozhimen.stackk.utils.StackKUtil
import com.mozhimen.kotlin.utilk.android.app.UtilKActivityWrapper
import com.mozhimen.kotlin.utilk.android.app.UtilKApplicationWrapper
import com.mozhimen.kotlin.utilk.android.app.isFinishingOrDestroyed
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.kotlin.collections.ifNotEmpty
import com.mozhimen.kotlin.utilk.kotlin.collections.removeBy
import com.mozhimen.kotlin.utilk.kotlin.t2weakRef
import java.lang.ref.WeakReference
import java.util.LinkedList

/**
 * @ClassName StackKCbProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 2:04
 * @Version 1.0
 */
internal class StackKCbDelegate : IStackK, IStackKLifecycle {
    private val _activityRefs = LinkedList<WeakReference<Activity>>()
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private val _stackKActivityLifecycleCallbacks = ArrayList<StackKActivityLifecycleCallbacks>()
    private var _activityLaunchCount = 0
    private var _isFront = true

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun init() {
        UtilKApplicationWrapper.instance.get().registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    override fun addFrontBackListener(listener: IStackKListener) {
        if (!_frontBackListeners.contains(listener))
            _frontBackListeners.add(listener)
    }

    override fun removeFrontBackListener(listener: IStackKListener) {
        if (_frontBackListeners.contains(listener))
            _frontBackListeners.remove(listener)
    }

    override fun getFrontBackListeners(): ArrayList<IStackKListener> =
        _frontBackListeners

    override fun addStackKActivityLifecycleCallbacks(listener: StackKActivityLifecycleCallbacks) {
        if (!_stackKActivityLifecycleCallbacks.contains(listener))
            _stackKActivityLifecycleCallbacks.add(listener)
    }

    override fun removeStackKActivityLifecycleCallbacks(listener: StackKActivityLifecycleCallbacks) {
        if (_stackKActivityLifecycleCallbacks.contains(listener))
            _stackKActivityLifecycleCallbacks.remove(listener)
    }

    override fun getStackKActivityLifecycleCallbacks(): List<StackKActivityLifecycleCallbacks> =
        _stackKActivityLifecycleCallbacks

    override fun getStackTopActivity(): Activity? =
        getStackTopActivityRef()?.get()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        getStackTopActivityRef(onlyAlive)?.get() ?: UtilKActivityWrapper.getTop_ofReflect()

    override fun getStackTopActivityRef(): WeakReference<Activity>? =
        getStackTopActivityRef(true)

    override fun getStackTopActivityRef(onlyAlive: Boolean): WeakReference<Activity>? {
        if (getStackCount() <= 0 || _activityRefs.isEmpty()) {
            return null
        } else {
            val activityRef: WeakReference<Activity>? = _activityRefs.last
            val activity: Activity? = activityRef?.get()
            if (onlyAlive) {
                if (activity == null || activity.isFinishingOrDestroyed()) {
                    _activityRefs.remove(activityRef)
                    return getStackTopActivityRef(onlyAlive)
                }
            }
            return activityRef
        }
    }

    override fun getActivityRefs(): List<WeakReference<Activity>> =
        _activityRefs

    override fun getStackCount(): Int =
        getActivityRefs().size

    override fun getLaunchCount(): Int =
        _activityLaunchCount

    override fun finishAllActivity() {
        for (activityRef in _activityRefs) {
            if (activityRef.get()?.isFinishingOrDestroyed() == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

    override fun finishAllInvisibleActivity() {
        val stackTopActivityRef = getStackTopActivityRef()
        for (activityRef in _activityRefs) {
            if (activityRef == stackTopActivityRef) continue
            if (activityRef.get()?.isFinishingOrDestroyed() == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

    override fun isFront(): Boolean =
        _isFront

    //////////////////////////////////////////////////////////////////////////////////////////////

    private fun postEvent_FirstActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (getStackCount() == 1)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CTRATE_FIRST(savedInstanceState), ALifecycleOpportunity.PRE)
//            com.mozhimen.postk.livedata.PostKEventLiveData.instance.with<Boolean>(CStackKCons.Event.STACKK_FIRST_ACTIVITY).setValue(true)
    }

    private fun postEvent_FirstActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (getStackCount() == 1)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CTRATE_FIRST(savedInstanceState), ALifecycleOpportunity.AT)
    }

    private fun postEvent_FirstActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (getStackCount() == 1)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CTRATE_FIRST(savedInstanceState), ALifecycleOpportunity.POST)
    }

    private fun onFrontBackChanged(isFront: Boolean, activity: Activity) {
        for (listener in _frontBackListeners) {
            listener.onChanged(isFront, activity/*.t2weakRef()*/)
        }
    }

    private fun onLifecycleChanged(activity: Activity, event: SLifecycleCallbackEvent, @ALifecycleOpportunity opportunity: Int) {
        _stackKActivityLifecycleCallbacks.ifNotEmpty { callbacks ->
            callbacks.forEach { callback ->
                if (opportunity == ALifecycleOpportunity.AT) {
                    StackKUtil.onLifecycleChangedAt(activity, event, callback)
                } else if (opportunity == ALifecycleOpportunity.PRE && UtilKBuildVersion.isAfterV_29_10_Q()) {
                    StackKUtil.onLifecycleChangedPre(activity, event, callback)
                } else if (opportunity == ALifecycleOpportunity.POST && UtilKBuildVersion.isAfterV_29_10_Q()) {
                    StackKUtil.onLifecycleChangedPost(activity, event, callback)
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private inner class InnerActivityLifecycleCallbacks : BaseActivityLifecycleCallbacks() {
        override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
            super.onActivityPreCreated(activity, savedInstanceState)
            postEvent_FirstActivityPreCreated(activity, savedInstanceState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CREATE(savedInstanceState), ALifecycleOpportunity.PRE)
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            super.onActivityCreated(activity, savedInstanceState)
            _activityRefs.add(activity.t2weakRef())
            postEvent_FirstActivityCreated(activity, savedInstanceState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CREATE(savedInstanceState), ALifecycleOpportunity.AT)
        }

        override fun onActivityPostCreated(activity: Activity, savedInstanceState: Bundle?) {
            super.onActivityPostCreated(activity, savedInstanceState)
            postEvent_FirstActivityPostCreated(activity, savedInstanceState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_CREATE(savedInstanceState), ALifecycleOpportunity.POST)
        }

        override fun onActivityPreStarted(activity: Activity) {
            super.onActivityPreStarted(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_START, ALifecycleOpportunity.PRE)
        }

        override fun onActivityStarted(activity: Activity) {
            super.onActivityStarted(activity)
            _activityLaunchCount++
            //_activityLaunchCount>0 说明应用处在可见状态, 也就是前台
            //!isFront 之前是不是在后台
            if (!_isFront && _activityLaunchCount > 0) {
                onFrontBackChanged(true.also { _isFront = true }, activity)
            }
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_START, ALifecycleOpportunity.AT)
        }

        override fun onActivityPostStarted(activity: Activity) {
            super.onActivityPostStarted(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_START, ALifecycleOpportunity.POST)
        }

        override fun onActivityPreResumed(activity: Activity) {
            super.onActivityPreResumed(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_RESUME, ALifecycleOpportunity.PRE)
        }

        override fun onActivityResumed(activity: Activity) {
            super.onActivityResumed(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_RESUME, ALifecycleOpportunity.AT)
        }

        override fun onActivityPostResumed(activity: Activity) {
            super.onActivityPostResumed(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_RESUME, ALifecycleOpportunity.POST)
        }

        override fun onActivityPrePaused(activity: Activity) {
            super.onActivityPrePaused(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.PRE)
        }

        override fun onActivityPaused(activity: Activity) {
            super.onActivityPaused(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.AT)
        }

        override fun onActivityPostPaused(activity: Activity) {
            super.onActivityPostPaused(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.POST)
        }

        override fun onActivityPreStopped(activity: Activity) {
            super.onActivityPreStopped(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.PRE)
        }

        override fun onActivityStopped(activity: Activity) {
            super.onActivityStopped(activity)
            _activityLaunchCount--
            if (_activityLaunchCount <= 0 && _isFront) {
                onFrontBackChanged(false.also { _isFront = false }, activity)
            }
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.AT)
        }

        override fun onActivityPostStopped(activity: Activity) {
            super.onActivityPostStopped(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_PAUSE, ALifecycleOpportunity.POST)
        }

        override fun onActivityPreDestroyed(activity: Activity) {
            super.onActivityPreDestroyed(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_DESTROY, ALifecycleOpportunity.PRE)
        }

        override fun onActivityDestroyed(activity: Activity) {
            super.onActivityDestroyed(activity)
            _activityRefs.removeBy { it.get() == activity }
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_DESTROY, ALifecycleOpportunity.AT)
        }

        override fun onActivityPostDestroyed(activity: Activity) {
            super.onActivityPostDestroyed(activity)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_DESTROY, ALifecycleOpportunity.POST)
        }

        override fun onActivityPreSaveInstanceState(activity: Activity, outState: Bundle) {
            super.onActivityPreSaveInstanceState(activity, outState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE(outState), ALifecycleOpportunity.PRE)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            super.onActivitySaveInstanceState(activity, outState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE(outState), ALifecycleOpportunity.AT)
        }

        override fun onActivityPostSaveInstanceState(activity: Activity, outState: Bundle) {
            super.onActivityPostSaveInstanceState(activity, outState)
            onLifecycleChanged(activity, SLifecycleCallbackEvent.ON_SAVEINSTANCESTATE(outState), ALifecycleOpportunity.POST)
        }
    }
}