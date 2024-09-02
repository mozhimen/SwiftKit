package com.mozhimen.stackk.process.helpers

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.stackk.cb.StackKCb
import com.mozhimen.stackk.commons.IStackK
import com.mozhimen.stackk.commons.IStackKListener
import java.lang.ref.WeakReference

/**
 * @ClassName StackKProcessProxy
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/11 14:34
 * @Version 1.0
 */
@OApiInit_InApplication
internal class StackKProcessDelegate : IStackK {
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _applicationLifecycleProxy by lazy { ApplicationLifecycleProxy() }
    private val _frontBackListeners = ArrayList<IStackKListener>()
    private var _isFront = true

    /////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun init() {
        _applicationLifecycleProxy.bindLifecycle(ProcessLifecycleOwner.get())
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
        StackKCb.instance.getStackTopActivity()

    override fun getStackTopActivity(onlyAlive: Boolean): Activity? =
        StackKCb.instance.getStackTopActivity(onlyAlive)

    override fun getStackTopActivityRef(): WeakReference<Activity>? =
        StackKCb.instance.getStackTopActivityRef()

    override fun getStackTopActivityRef(onlyAlive: Boolean): WeakReference<Activity>? =
        StackKCb.instance.getStackTopActivityRef(onlyAlive)

    override fun getActivityRefs(): List<WeakReference<Activity>> =
        StackKCb.instance.getActivityRefs()

    override fun getStackCount(): Int =
        StackKCb.instance.getStackCount()

    override fun getLaunchCount(): Int =
        StackKCb.instance.getLaunchCount()

    override fun finishAllActivity() {
        StackKCb.instance.finishAllActivity()
    }

    override fun finishAllInvisibleActivity() {
        StackKCb.instance.finishAllInvisibleActivity()
    }

    override fun isFront(): Boolean =
        _isFront

    /////////////////////////////////////////////////////////////////////////

    private fun onFrontBackChanged(isFront: Boolean, activity: Activity) {
        for (listener in _frontBackListeners) {
            listener.onChanged(isFront, /*WeakReference(*/activity)
        }
    }

    //////////////////////////////////////////////////////////////////////////

    @OApiCall_BindLifecycle
    @OApiInit_ByLazy
    private inner class ApplicationLifecycleProxy : BaseWakeBefDestroyLifecycleObserver() {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            if (getLaunchCount() > 0 && !_isFront && owner is Activity) {
                onFrontBackChanged(true.also { _isFront = true }, owner)
            }
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            if (getLaunchCount() <= 0 && _isFront && owner is Activity) {
                onFrontBackChanged(false.also { _isFront = false }, owner)
            }
        }
    }
}