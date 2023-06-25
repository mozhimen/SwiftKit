package com.mozhimen.basick.utilk.view.window

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.view.UtilKView
import com.mozhimen.basick.utilk.view.bar.UtilKNavigationBar
import com.mozhimen.basick.utilk.view.bar.UtilKStatusBar
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
        UtilKWindow.getContentView(window)

    @JvmStatic
    fun getViewGroup(activity: Activity): ViewGroup? =
        getViewGroup(activity.window)

    @JvmStatic
    fun getViewGroup(window: Window): ViewGroup? =
        get(window) as? ViewGroup?

    @JvmStatic
    fun getContent(activity: Activity): View? =
        getViewGroup(activity.window)

    @JvmStatic
    fun getContent(window: Window): View? =
        getViewGroup(window)?.getChildAt(0)

    @JvmStatic
    fun getInvisibleHeight(activity: Activity): Int =
        getInvisibleHeight(UtilKWindow.get(activity))

    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带CView.FLAG_LAYOUT_FULLSCREEN这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     */
    @JvmStatic
    fun setFitsSystemWindows(activity: Activity, fitSystemWindows: Boolean = true) {//华为,OPPO机型在StatusUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getContent(activity)?.fitsSystemWindows = fitSystemWindows
        }
    }

    /**
     * 获取其ContentView区域高度
     * @param window Window
     * @return Int
     */
    @JvmStatic
    fun getInvisibleHeight(window: Window): Int {
        val contentView = get(window) ?: return 0
        val outRect = Rect()
        UtilKView.getWindowVisibleDisplayFrame(contentView, outRect)
        Log.d(TAG, "getInvisibleHeight: " + (contentView.bottom - outRect.bottom))
        val delta = abs(contentView.bottom - outRect.bottom)
        return if (delta <= UtilKStatusBar.getStatusBarHeight() + UtilKNavigationBar.getNavigationBarHeight()) 0 else delta
    }
}