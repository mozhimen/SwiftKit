package com.mozhimen.basicsk.stackk


import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mozhimen.basicsk.stackk.commons.IStackKListener
import com.mozhimen.basicsk.utilk.UtilKGlobal
import java.lang.ref.WeakReference

/**
 * @ClassName StackKMgr
 * @Description 提供前后台状态监听 以及栈顶activity的服务
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/20 21:58
 * @Version 1.0
 */
class StackKMgr private constructor() {
    companion object {
        @JvmStatic
        val instance: StackKMgr by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { StackKMgr() }
    }

    private val _activityRefs = ArrayList<WeakReference<Activity>>()
    private val _frontBackCallbacks = ArrayList<IStackKListener>()
    private var _activityLaunchCount = 0
    private var _isFront = true

    /**
     * 初始化
     */
    fun init() {
        UtilKGlobal.instance.getApp()!!.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    /**
     * 获取activity集合
     * @return List<WeakReference<Activity>>
     */
    internal fun getActivityRefs(): ArrayList<WeakReference<Activity>> = _activityRefs

    /**
     * 获取监听器集合
     * @return List<StackKListener>
     */
    internal fun getListeners(): ArrayList<IStackKListener> = _frontBackCallbacks

    /**
     * 是否在前台
     * @return Boolean
     */
    internal fun isFront() = _isFront

    private inner class InnerActivityLifecycleCallbacks() : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            _activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            _activityLaunchCount++
            //_activityLaunchCount>0 说明应用处在可见状态, 也就是前台
            //!isFront 之前是不是在后台
            if (!_isFront && _activityLaunchCount > 0) {
                _isFront = true
                onFrontBackChanged(_isFront)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            _activityLaunchCount--
            if (_activityLaunchCount <= 0 && _isFront) {
                _isFront = false
                onFrontBackChanged(_isFront)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in _activityRefs) {
                if (activityRef != null && activityRef.get() == activity) {
                    _activityRefs.remove(activityRef)
                    break
                }
            }
        }
    }

    private fun onFrontBackChanged(isFront: Boolean) {
        for (callback in _frontBackCallbacks) {
            callback.onChanged(isFront)
        }
    }
}