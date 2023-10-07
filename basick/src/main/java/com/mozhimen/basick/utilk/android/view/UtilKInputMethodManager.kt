package com.mozhimen.basick.utilk.android.view

import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.mozhimen.basick.lintk.optin.OptInApiDeprecated_Official_AfterV_31_11_S
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKInputMethodManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/7 15:11
 * @Version 1.0
 */
object UtilKInputMethodManager {
    @JvmStatic
    fun get(context: Context): InputMethodManager =
        UtilKContext.getInputMethodManager(context)

    @JvmStatic
    fun get(view: View): InputMethodManager =
        UtilKContext.getInputMethodManager(view.context)

    //////////////////////////////////////////////////////////////////

    /**
     * 是否激活
     */
    @JvmStatic
    fun isActive(context: Context): Boolean =
        get(context).isActive

    /**
     * 是否激活
     */
    @JvmStatic
    fun isActive(view: View): Boolean =
        get(view).isActive(view)

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showSoftInput(view: View) {
        showSoftInput(view, 0)
    }

    @JvmStatic
    fun showSoftInput(view: View, flags: Int) {
        get(view).showSoftInput(view, flags)
    }

    @JvmStatic
    fun hideSoftInputFromWindow(view: View) {
        hideSoftInputFromWindow(view, view.windowToken)
    }

    @JvmStatic
    fun hideSoftInputFromWindow(view: View, windowToken: IBinder) {
        hideSoftInputFromWindow(view, windowToken, 0)
    }

    @JvmStatic
    fun hideSoftInputFromWindow(view: View, windowToken: IBinder, flags: Int) {
        get(view).hideSoftInputFromWindow(windowToken, flags)
    }

    @JvmStatic
    fun toggleSoftInput(context: Context, showFlags: Int, hideFlags: Int) {
        get(context).toggleSoftInput(showFlags, hideFlags)
    }

    @OptInApiDeprecated_Official_AfterV_31_11_S
    @JvmStatic
    fun toggleSoftInput(context: Context) {
        toggleSoftInput(context, 0, 0)
    }
}