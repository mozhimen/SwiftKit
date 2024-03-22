package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import kotlin.math.abs

/**
 * @ClassName UtilKDecorView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/20 15:45
 * @Version 1.0
 */
object UtilKDecorView : BaseUtilK() {
    @JvmStatic
    fun get(activity: Activity): View =
        get(activity.window)

    @JvmStatic
    fun get(window: Window): View =
        UtilKWindow.getDecorView(window)

    @Suppress(CSuppress.UNCHECKED_CAST)
    @JvmStatic
    fun <V : View> getAs(window: Window): V =
        get(window) as V

    @JvmStatic
    fun getAsViewGroup(activity: Activity): ViewGroup =
        getAsViewGroup(activity.window)

    @JvmStatic
    fun getAsViewGroup(window: Window): ViewGroup =
        getAs(window)

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun <V : View> getContentView(activity: Activity): V =
        get(activity).findViewById(CPackage.ANDROID_R_ID_CONTENT)

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getSystemUiVisibility(activity: Activity): Int =
        getSystemUiVisibility(get(activity) )

    @JvmStatic
    fun getSystemUiVisibility(window: Window): Int =
        getSystemUiVisibility(get(window))

    @JvmStatic
    fun getSystemUiVisibility(decorView: View): Int =
        decorView.systemUiVisibility

    @JvmStatic
    fun getWindowSystemUiVisibility(activity: Activity): Int =
        getWindowSystemUiVisibility(get(activity))

    @JvmStatic
    fun getWindowSystemUiVisibility(window: Window): Int =
        getWindowSystemUiVisibility(get(window))

    @JvmStatic
    fun getWindowSystemUiVisibility(decorView: View): Int =
        decorView.windowSystemUiVisibility

    ///////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getWindowVisibleDisplayFrame(activity: Activity): Rect =
        getWindowVisibleDisplayFrame(get(activity), Rect())

    @JvmStatic
    fun getWindowVisibleDisplayFrame(window: Window): Rect =
        getWindowVisibleDisplayFrame(get(window), Rect())

    @JvmStatic
    fun getWindowVisibleDisplayFrame(view: View, rect: Rect): Rect {
        UtilKView.getWindowVisibleDisplayFrame(view, rect)
        return rect
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 截屏
     */
    @JvmStatic
    fun getBitmapForDrawingCache(activity: Activity): Bitmap {
        val decorView = get(activity)
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(decorView.drawingCache, 0, 0, decorView.measuredWidth, decorView.measuredHeight - UtilKVirtualBar.getHeight(activity))
        decorView.isDrawingCacheEnabled = false
        decorView.destroyDrawingCache()
        return bitmap
    }

    ///////////////////////////////////////////////////////////////////////////

    /**
     * 采用谷歌原生状态栏文字颜色的方法进行设置,携带 [CView.SystemUiFlag.LAYOUT_FULLSCREEN] 这个flag那么默认界面会变成全屏模式,
     * 需要在根布局中设置FitSystemWindows属性为true, 所以添加Process方法中加入如下的代码
     * 或者在xml中添加android:fitSystemWindows="true"
     * 华为,OPPO机型在StatusUtil.setLightStatusBar后布局被顶到状态栏上去了
     *
     * 延迟加载不然getChild0为空
     */
    @JvmStatic
    fun applyFitsSystemWindows(activity: Activity) {
        get(activity).post {
            UtilKContentView.getChildAt0_ofPac(activity)?.applyFitSystemWindow() ?: "setFitsSystemWindows contentView is null".e(TAG)
        }
    }

    @JvmStatic
    fun applySystemUiVisibility(activity: Activity, visibility: Int) {
        applySystemUiVisibility(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibility(window: Window, visibility: Int) {
        applySystemUiVisibility(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibility(decorView: View, visibility: Int) {
        decorView.systemUiVisibility = visibility
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(activity: Activity, visibility: Int) {
        applySystemUiVisibilityOr(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(window: Window, visibility: Int) {
        applySystemUiVisibilityOr(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityOr(decorView: View, visibility: Int) {
        applySystemUiVisibility(decorView, getSystemUiVisibility(decorView) or visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(activity: Activity, visibility: Int) {
        applySystemUiVisibilityAnd(activity.window, visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(window: Window, visibility: Int) {
        applySystemUiVisibilityAnd(get(window), visibility)
    }

    @JvmStatic
    fun applySystemUiVisibilityAnd(decorView: View, visibility: Int) {
        applySystemUiVisibility(decorView, getSystemUiVisibility(decorView) and visibility)
    }

    @JvmStatic
    fun applyLayoutStable(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.LAYOUT_STABLE)
    }

    @JvmStatic
    fun applyImmersedHard(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.IMMERSIVE)
    }

    @JvmStatic
    fun applyImmersedSticky(activity: Activity) {
        applySystemUiVisibilityOr(activity, CView.SystemUiFlag.IMMERSIVE_STICKY)
    }

//    @JvmStatic
//    fun applyFullScreen(activity: Activity) {
//        setFullScreen(activity.window)
//    }
//
//    @JvmStatic
//    fun applyFullScreen(window: Window) {
//        setFullScreen(get(window))
//    }
//
//    /**
//     * 设置全屏
//     * @param decorView View
//     */
//    @JvmStatic
//    fun applyFullScreen(decorView: View) {
//        setSystemUiVisibility(
//            decorView, (CView.SystemUiFlag.LOW_PROFILE or
//                    CView.SystemUiFlag.FULLSCREEN or
//                    CView.SystemUiFlag.LAYOUT_STABLE or
//                    CView.SystemUiFlag.IMMERSIVE_STICKY or
//                    CView.SystemUiFlag.LAYOUT_HIDE_NAVIGATION or
//                    CView.SystemUiFlag.HIDE_NAVIGATION)
//        )
//    }
}