package com.mozhimen.basick.stackk.process.helpers

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.mozhimen.basick.elemk.annors.ADescription
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackK
import com.mozhimen.basick.stackk.commons.IStackKListener
import java.lang.ref.WeakReference

/**
 * @ClassName StackKProcessProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 14:34
 * @Version 1.0
 */
internal class StackKProcessProxy : IStackK {
    private val _applicationObserver by lazy { ApplicationObserver() }
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private var _isFront = true

    /////////////////////////////////////////////////////////////////////////

    override fun init() {
        _applicationObserver.bindLifecycle(ProcessLifecycleOwner.get())
    }

    override fun getStackTopActivity(): Activity? =
        StackKCb.instance.getStackTopActivity()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        StackKCb.instance.getStackTopActivity(onlyAlive)

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
        StackKCb.instance.finishAllActivity()
    }

    override fun isFront(): Boolean =
        _isFront

    override fun getActivityRefs(): ArrayList<WeakReference<Activity>> =
        StackKCb.instance.getActivityRefs()

    override fun getStackCount(): Int =
        StackKCb.instance.getStackCount()

    override fun getLaunchCount(): Int =
        StackKCb.instance.getLaunchCount()

    /////////////////////////////////////////////////////////////////////////

    @ADescription("init by lazy")
    private inner class ApplicationObserver : BaseWakeBefDestroyLifecycleObserver() {

        /**
         * 在程序的整个生命周期中只会调用一次
         * @param owner LifecycleOwner
         */
        override fun onCreate(owner: LifecycleOwner) {
            Log.d(TAG, "onCreate")
        }

        /**
         * 当程序在前台时调用
         * @param owner LifecycleOwner
         */
        override fun onStart(owner: LifecycleOwner) {
            Log.d(TAG, "onStart")
            if (!_isFront && getLaunchCount() > 0) {
                onFrontBackChanged(true.also { _isFront = true })
            }
        }

        /**
         * 当程序在前台时调用
         * @param owner LifecycleOwner
         */
        override fun onResume(owner: LifecycleOwner) {
            Log.d(TAG, "onResume")
        }

        /**
         * 在程序在后台时调用
         * @param owner LifecycleOwner
         */
        override fun onPause(owner: LifecycleOwner) {
            Log.d(TAG, "onPause")
        }

        /**
         * 在程序在后台时调用
         * @param owner LifecycleOwner
         */
        override fun onStop(owner: LifecycleOwner) {
            Log.d(TAG, "onStop")
            if (getLaunchCount() <= 0 && _isFront) {
                onFrontBackChanged(false.also { _isFront = false })
            }
        }

        /**
         * 永远不会调用
         * @param owner LifecycleOwner
         */
        override fun onDestroy(owner: LifecycleOwner) {
            Log.d(TAG, "onDestroy")
        }

        private fun onFrontBackChanged(isFront: Boolean) {
            for (listener in _frontBackListeners) {
                listener.onChanged(isFront)
            }
        }
    }
}