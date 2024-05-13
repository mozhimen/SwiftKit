package com.mozhimen.basick.utilk.android.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz

/**
 * @ClassName UtilKWindowManagerGlobal
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/13
 * @Version 1.0
 */
object UtilKWindowManagerWrapper : IUtilK {

    private val _windowManagerClazz by lazy(LazyThreadSafetyMode.NONE) {
        try {
            "android.view.WindowManagerGlobal".strPackage2clazz()
        } catch (ignored: Throwable) {
            Log.w(TAG, ignored)
            null
        }
    }

    private val _windowManagerInstance by lazy(LazyThreadSafetyMode.NONE) {
        _windowManagerClazz?.getMethod("getInstance")?.invoke(null)
    }

    private val _viewsField by lazy(LazyThreadSafetyMode.NONE) {
        _windowManagerClazz?.let { windowManagerClass ->
            windowManagerClass.getDeclaredField("mViews").apply { isAccessible = true }
        }
    }

    private val _paramsField by lazy(LazyThreadSafetyMode.NONE) {
        _windowManagerClazz?.let { windowManagerClass ->
            windowManagerClass.getDeclaredField("mParams").apply { isAccessible = true }
        }
    }

    @JvmStatic
    @SuppressLint("PrivateApi", "ObsoleteSdkInt", "DiscouragedPrivateApi")
    fun getViews(): List<View> {
        try {
            _windowManagerInstance?.let { windowManagerInstance ->
                _viewsField?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<View>
                }
            }
        } catch (ignored: Throwable) {
            Log.w(TAG, ignored)
        }
        return emptyList()
    }

    @JvmStatic
    @SuppressLint("PrivateApi", "ObsoleteSdkInt", "DiscouragedPrivateApi")
    fun getParams(): List<WindowManager.LayoutParams> {
        try {
            _windowManagerInstance?.let { windowManagerInstance ->
                _paramsField?.let { mViewsField ->
                    return mViewsField[windowManagerInstance] as ArrayList<WindowManager.LayoutParams>
                }
            }
        } catch (ignored: Throwable) {
            Log.w(TAG, ignored)
        }
        return emptyList()
    }
}