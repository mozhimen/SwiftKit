package com.mozhimen.basick.elemk.androidx.lifecycle.commons

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName IDefaultLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
interface IDefaultLifecycleObserver : DefaultLifecycleObserver, IUtilK {
    fun bindLifecycle(owner: LifecycleOwner)

    /**
     * 在程序的整个生命周期中只会调用一次
     * @param owner LifecycleOwner
     */
    override fun onCreate(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onCreate: ")
    }

    /**
     * 当程序在前台时调用
     * @param owner LifecycleOwner
     */
    override fun onStart(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onStart: ")
    }

    /**
     * 当程序在前台时调用
     * @param owner LifecycleOwner
     */
    override fun onResume(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onResume: ")
    }

    /**
     * 在程序在后台时调用
     * @param owner LifecycleOwner
     */
    override fun onPause(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onPause: ")
    }

    /**
     * 在程序在后台时调用
     * @param owner LifecycleOwner
     */
    override fun onStop(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onStop: ")
    }

    /**
     * 永远不会调用
     * @param owner LifecycleOwner
     */
    override fun onDestroy(owner: LifecycleOwner) {
        UtilKLogWrapper.d(TAG, "onDestroy: ")
    }
}