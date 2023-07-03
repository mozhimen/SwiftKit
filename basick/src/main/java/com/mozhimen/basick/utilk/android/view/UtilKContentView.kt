package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.elemk.cons.CView
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import kotlin.math.abs

/**
 * @ClassName UtilKContentView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:47
 * @Version 1.0
 */
object UtilKContentView : BaseUtilK() {

    @JvmStatic
    fun get(activity: Activity): View? =
        get(activity.window)

    @JvmStatic
    fun get(window: Window): View? =
        UtilKWindow.getContentView(window).also { "getContentView is null ${it == null}".dt(TAG) }

    @JvmStatic
    fun getAsViewGroup(activity: Activity): ViewGroup? =
        getAsViewGroup(activity.window)

    @JvmStatic
    fun getAsViewGroup(window: Window): ViewGroup? =
        (get(window) as? ViewGroup?).also { "getAsViewGroup is null ${it == null} ${it?.childCount}".dt(TAG) }

    @JvmStatic
    fun getChild0(activity: Activity): View? =
        getChild0(activity.window)

    @JvmStatic
    fun getChild0(window: Window): View? =
        getAsViewGroup(window)?.getChildAt(0).also { "getChild0 is null ${it == null}".dt(TAG) }

    @JvmStatic
    fun getInvisibleHeight(activity: Activity): Int =
        getInvisibleHeight(UtilKWindow.get(activity))

    @JvmStatic
    fun getInvisibleHeight(window: Window): Int {
        val contentView = get(window) ?: return 0
        val outRect = Rect()
        UtilKView.getWindowVisibleDisplayFrame(contentView, outRect)
        Log.d(TAG, "getInvisibleHeight: " + (contentView.bottom - outRect.bottom))
        val delta = abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getHeight() + UtilKNavigationBar.getHeight()) 0 else delta
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带
     * @see CView.SystemUiFlag.LAYOUT_FULLSCREEN 这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     * 华为,OPPO机型在StatusUtil.setLightStatusBar后布局被顶到状态栏上去了
     *
     * 延迟加载不然getChild0为空
     */
    @JvmStatic
    fun setFitsSystemWindows(activity: Activity) {
        activity.window.decorView.post {
            getChild0(activity)?.applyFitSystemWindow() ?: "setFitsSystemWindows contentView is null".et(TAG)
        }
    }
}