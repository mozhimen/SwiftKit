package com.mozhimen.basick.stackk


import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.stackk.cons.CStackKEvent
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.jetpack.lifecycle.UtilKDataBus
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.activity.UtilKActivity
import java.lang.ref.WeakReference

/**
 * @ClassName StackKMgr
 * @Description 提供前后台状态监听 以及栈顶activity的服务
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
class StackKMgr private constructor() : BaseUtilK() {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    private val _activityRefs = ArrayList<WeakReference<Activity>>()
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private var _activityLaunchCount = 0
    private var _isFront = true

    /**
     * 初始化
     */
    init {
        UtilKApplication.instance.get().registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    /**
     * 获取activity集合
     * @return List<WeakReference<Activity>>
     */
    fun getActivityRefs(): ArrayList<WeakReference<Activity>> = _activityRefs

    /**
     * 获取监听器集合
     * @return List<StackKListener>
     */
    fun getListeners(): ArrayList<IStackKListener> = _frontBackListeners

    /**
     * 是否在前台
     * @return Boolean
     */
    fun isFront() = _isFront

    /**
     * 获得栈顶
     * @param onlyAlive Boolean
     * @return Activity?
     */
    fun getStackTopActivity(onlyAlive: Boolean = true): Activity? {
        if (_activityRefs.size <= 0) {
            return null
        } else {
            val activityRef: WeakReference<Activity> = _activityRefs[_activityRefs.size - 1]
            val activity: Activity? = activityRef.get()
            if (onlyAlive) {
                if (activity == null || UtilKActivity.isFinishing(activity) || (Build.VERSION.SDK_INT >= CVersionCode.V_17_42_J1 && activity.isDestroyed)) {
                    _activityRefs.remove(activityRef)
                    return getStackTopActivity(onlyAlive)
                }
            }
            return activity
        }
    }

    /**
     * 增加监听器
     * @param listener IStackKListener
     */
    fun addFrontBackListener(listener: IStackKListener) {
        if (!_frontBackListeners.contains(listener)) {
            _frontBackListeners.add(listener)
        }
    }

    /**
     * 移除监听器
     * @param listener IStackKListener
     */
    fun removeFrontBackListener(listener: IStackKListener) {
        if (_frontBackListeners.contains(listener)) {
            _frontBackListeners.remove(listener)
        }
    }

    /**
     * 移除所有的Activity
     */
    fun finishAllActivity() {
        for (activityRef in _activityRefs) {
            if (activityRef.get()?.isFinishing == false) {
                activityRef.get()?.finish()
            }
        }
        _activityRefs.clear()
    }

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
        if (_activityRefs.size == 1) {
            UtilKDataBus.with<Boolean>(CStackKEvent.STACKK_FIRST_ACTIVITY).setValue(true)
        }
    }

    private object INSTANCE {
        val holder = StackKMgr()
    }
}