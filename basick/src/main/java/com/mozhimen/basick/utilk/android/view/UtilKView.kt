package com.mozhimen.basick.utilk.android.view

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.mozhimen.basick.elemk.android.view.cons.CView
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.lintk.optin.OptInApiUse_BaseApplication
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.app.UtilKActivity
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.kotlin.UtilKAny
import com.mozhimen.basick.utilk.kotlin.strColor2intColor
import com.mozhimen.basick.utilk.kotlinx.coroutines.getViewClickFlow
import com.mozhimen.basick.utilk.kotlinx.coroutines.throttleFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * @ClassName UtilKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:50
 * @Version 1.0
 */
fun View.isVisible(): Boolean =
    UtilKView.isVisible(this)

fun View.isInvisible(): Boolean =
    UtilKView.isInvisible(this)

fun View.isGone(): Boolean =
    UtilKView.isGone(this)

//////////////////////////////////////////////////////////////////////////////

fun View.applyPadding(paddingHorizontal: Int, paddingVertical: Int) {
    UtilKView.applyPadding(this, paddingHorizontal, paddingVertical)
}

fun View.applyPaddingHorizontal(padding: Int) {
    UtilKView.applyPaddingHorizontal(this, padding)
}

fun View.applyPaddingVertical(padding: Int) {
    UtilKView.applyPaddingVertical(this, padding)
}

fun View.applyLayoutParams(size: Int) {
    UtilKView.applyLayoutParams(this, size)
}

fun View.applyLayoutParams(width: Int, height: Int) {
    UtilKView.applyLayoutParams(this, width, height)
}

fun View.applyLayoutParamsMatch() {
    UtilKView.applyLayoutParamsMatch(this)
}

fun View.applyLayoutParamHeightStatusBar() {
    UtilKView.applyLayoutParamHeightStatusBar(this)
}

//////////////////////////////////////////////////////////////////////////////

fun View.applyBackgroundNull() =
    UtilKView.applyBackgroundNull(this)

fun View.applyBackground(drawable: Drawable) {
    UtilKView.applyBackground(this, drawable)
}

fun View.applyElevation(elevation: Float) {
    UtilKView.applyElevation(this, elevation)
}

fun View.applyFocusable() {
    UtilKView.applyFocusable(this)
}

fun View.applyRequestFocus() {
    UtilKView.applyRequestFocus(this)
}

fun View.applyOnGlobalLayoutObserver(invoke: I_Listener) {
    UtilKView.applyOnGlobalLayoutObserver(this, invoke)
}

fun View.applyFitSystemWindow() {
    UtilKView.applyFitSystemWindow(this)
}

//////////////////////////////////////////////////////////////////////////////

fun View.applyDebounceClickListener(thresholdMillis: Long = 500, block: IA_Listener<View>) {
    UtilKView.applyDebounceClickListener(this, block, thresholdMillis)
}

fun View.applyDebounceClickListener(scope: CoroutineScope, thresholdMillis: Long = 500, block: IA_Listener<View>) {
    UtilKView.applyDebounceClickListener(this, scope, block, thresholdMillis)
}

fun View.applySuspendDebounceClickListener(scope: CoroutineScope, thresholdMillis: Long = 500, block: suspend CoroutineScope.(View) -> Unit) {
    UtilKView.applySuspendDebounceClickListener(this, scope, block, thresholdMillis)
}

object UtilKView : BaseUtilK() {

    @JvmStatic
    fun getLongTag(view: View, key: Int, defaultValue: Long): Long =
        if (view.getTag(key) != null) view.getTag(key) as Long else defaultValue

    @JvmStatic
    fun getBundle(view: View): Bundle {
        val screenLocation = IntArray(2)
        view.getLocationOnScreen(screenLocation) //获取view在整个屏幕中的位置
        val bundle = Bundle().apply {
            putInt("propname_sreenlocation_left", screenLocation[0])
            putInt("propname_sreenlocation_top", screenLocation[1])
            putInt("propname_width", view.width)
            putInt("propname_height", view.height)
        }
        "getBundle Left: ${screenLocation[0]} Top: ${screenLocation[1]} Width: ${view.width} Height: ${view.height}".dt(TAG)
        return bundle
    }

    @JvmStatic
    fun getWindowVisibleDisplayFrame(view: View): Rect {
        val rect = Rect()
        getWindowVisibleDisplayFrame(view, rect)
        return rect
    }

    @JvmStatic
    fun getWindowVisibleDisplayFrame(view: View, rect: Rect) {
        view.getWindowVisibleDisplayFrame(rect)
    }

    /**
     * 显示占比
     */
    @JvmStatic
    fun getVisiblePercent(view: View): Int {
        if (view.isShown) {
            val rect = Rect().apply { view.getGlobalVisibleRect(this) }
            return if (rect.top > 0 && rect.left < UtilKScreen.getWidthOfDisplay())
                ((rect.width().toFloat() * rect.height().toFloat()) / (view.width.toFloat() * view.height.toFloat()) * 100).toInt()
            else 0
        }
        return 0
    }

    /**
     * 寻找父View是否匹配列举的类型
     */
    @JvmStatic
    fun getParentViewMatch(view: View, vararg matches: Class<*>): View? {
        var tempView: View = view
        while (tempView.parent != null && tempView.parent is View) {
            try {
                tempView = tempView.parent as View
                if (UtilKAny.isObjTypeMatch(tempView, *matches)) return tempView
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }
        return null
    }

    @JvmStatic
    fun getBitmapForViewBackground(view: View, fullScreen: Boolean): Bitmap? =
        getBitmapForViewBackground(view, 1.0f, fullScreen, 0, 0)

    @JvmStatic
    fun getBitmapForViewBackground(view: View, scaledRatio: Float, fullScreen: Boolean, cutoutX: Int, cutoutY: Int): Bitmap? {
        if (view.width <= 0 || view.height <= 0) {
            UtilKLogWrapper.e("getViewBitmap  >>  宽或者高为空")
            return null
        }
        val statusBarHeight = UtilKStatusBar.getHeight(false)
        var tempBitmap: Bitmap
        UtilKLogWrapper.i("getViewBitmap 模糊原始图像分辨率 [" + view.width + " x " + view.height + "]")
        tempBitmap = try {
            Bitmap.createBitmap((view.width * scaledRatio).toInt(), (view.height * scaledRatio).toInt(), Bitmap.Config.ARGB_8888)
        } catch (error: OutOfMemoryError) {
            System.gc()
            return null
        }
        val canvas = Canvas(tempBitmap)
        val matrix = Matrix()
        matrix.preScale(scaledRatio, scaledRatio)
        canvas.setMatrix(matrix)
        val bgDrawable = view.background
        if (bgDrawable == null)
            canvas.drawColor("#FAFAFA".strColor2intColor())
        else
            bgDrawable.draw(canvas)
        if (fullScreen) {
            if (statusBarHeight > 0 && UtilKBuildVersion.isAfterV_21_5_L() && view.context is Activity) {
                val statusBarColor = (view.context as Activity).window.statusBarColor
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                paint.color = statusBarColor
                val rect = Rect(0, 0, view.width, statusBarHeight)
                canvas.drawRect(rect, paint)
            }
        }
        view.draw(canvas)
        UtilKLogWrapper.i("getViewBitmap 模糊缩放图像分辨率 [" + tempBitmap.width + " x " + tempBitmap.height + "]")
        if (cutoutX > 0 || cutoutY > 0) {
            try {
                val cutLeft = (cutoutX * scaledRatio).toInt()
                val cutTop = (cutoutY * scaledRatio).toInt()
                val cutWidth = tempBitmap.width - cutLeft
                val cutHeight = tempBitmap.height - cutTop
                tempBitmap = Bitmap.createBitmap(tempBitmap, cutLeft, cutTop, cutWidth, cutHeight, null, false)
            } catch (e: Exception) {
                System.gc()
            }
        }
        return tempBitmap
    }

    //////////////////////////////////////////////////////////////////////////////

    private const val DEBOUNCE_THRESHOLD_MILLIS = 1123460103
    private const val DEBOUNCE_LAST_CLICK_TIME = 1123461123
    private fun <V : View> isDebounceClickable(view: V, thresholdMillis: Long = 500): Boolean {
        var isClickable = false
        val currentClickTime = System.currentTimeMillis()
        val lastClickTime = getLongTag(view, DEBOUNCE_LAST_CLICK_TIME, currentClickTime)
        if (currentClickTime - lastClickTime >= getLongTag(view, DEBOUNCE_THRESHOLD_MILLIS, thresholdMillis)) {
            isClickable = true
            view.setTag(DEBOUNCE_LAST_CLICK_TIME, currentClickTime)
        }
        return isClickable
    }

    /**
     * 逐层在父View中查找View
     */
    @JvmStatic
    fun findViewForParentById(view: View, viewId: Int): View? {
        var tempView: View = view
        while (tempView.parent is View) {
            //逐层在父View中查找，是为了查找离自己最近的目标对象，因为ID可能重复
            tempView = tempView.parent as View
            val targetView = tempView.findViewById<View>(viewId)
            if (targetView != null)
                return targetView
        }
        return null
    }

    /**
     * 逐层在父View中查找View
     */
    @JvmStatic
    fun findViewForParentByView(destView: View, sourceView: View): View? {
        var tempView: View = sourceView
        while (tempView.parent is View) {
            //需要从content一直遍历往前找到decorView下的第一个child，那个为准
            tempView = tempView.parent as View
            if (tempView == destView)
                return tempView
        }
        return null
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 是否是DecorView
     */
    @JvmStatic
    fun isContentOrDecorView(view: View): Boolean =
        if (view.id == CPackage.ANDROID_R_ID_CONTENT) true
        else TextUtils.equals(view.javaClass.name, "com.android.internal.policy.DecorView")

    /**
     * 寻找父View是否匹配列举的类型
     */
    @JvmStatic
    fun isParentViewMatch(view: View, vararg matches: Class<*>): Boolean =
        getParentViewMatch(view, *matches) != null

    @JvmStatic
    fun isVisible(view: View): Boolean =
        view.visibility == CView.VISIBLE

    @JvmStatic
    fun isInvisible(view: View): Boolean =
        view.visibility == CView.INVISIBLE

    @JvmStatic
    fun isGone(view: View): Boolean =
        view.visibility == CView.GONE

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyDebounceClickListener(view: View, block: IA_Listener<View>, thresholdMillis: Long = 500) {
        view.setTag(DEBOUNCE_THRESHOLD_MILLIS, thresholdMillis)
        view.setOnClickListener {
            if (isDebounceClickable(view, thresholdMillis)) {
                block.invoke(view)
            }
        }
    }

    @JvmStatic
    fun applyDebounceClickListener(view: View, scope: CoroutineScope, block: IA_Listener<View>, thresholdMillis: Long = 500) {
        view.getViewClickFlow().throttleFirst(thresholdMillis).onEach { block.invoke(view) }.launchIn(scope)
    }

    @JvmStatic
    fun applySuspendDebounceClickListener(view: View, scope: CoroutineScope, block: suspend CoroutineScope.(View) -> Unit, thresholdMillis: Long = 500) {
        view.getViewClickFlow().throttleFirst(thresholdMillis).onEach { scope.block(view) }.launchIn(scope)
    }

    @JvmStatic
    fun applyFitSystemWindow(view: View) {
        view.fitsSystemWindows = true
    }

    @JvmStatic
    fun applyElevation(view: View, elevation: Float) {
        if (UtilKBuildVersion.isAfterV_21_5_L())
            view.elevation = elevation
    }

    @JvmStatic
    fun applyFocusable(view: View) {
        if (UtilKBuildVersion.isAfterV_26_8_O())
            view.focusable = CView.FOCUSABLE
    }

    @JvmStatic
    fun applyBackgroundNull(view: View) {
        applyBackground(view, null)
    }

    /**
     * 设置背景
     */
    @JvmStatic
    fun applyBackground(view: View, background: Drawable?) {
        if (UtilKBuildVersion.isAfterV_16_41_J())
            view.background = background
        else
            view.setBackgroundDrawable(background)
    }

    @JvmStatic
    fun applyOnGlobalLayoutObserver(view: View, invoke: I_Listener) {
        view.viewTreeObserver?.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (view.viewTreeObserver != null) {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    invoke.invoke()
                }
            }
        })
    }

    /**
     * 根据View的高度和宽高比, 设置高度
     */
    @JvmStatic
    fun applyViewRatio(view: View, ratio: Float) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (view.height > 0) {
                    view.layoutParams.width = (view.height * ratio).toInt()
                    view.postInvalidate()
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })
    }

    /**
     * 四周内边距
     */
    @JvmStatic
    fun applyPadding(view: View, paddingHorizontal: Int, paddingVertical: Int) {
        view.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

    /**
     * 左右内边距
     */
    @JvmStatic
    fun applyPaddingHorizontal(view: View, padding: Int) {
        view.setPadding(padding, 0, padding, 0)
    }

    /**
     * 上下内边距
     */
    @JvmStatic
    fun applyPaddingVertical(view: View, padding: Int) {
        view.setPadding(0, padding, 0, padding)
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 重置大小
     */
    @JvmStatic
    fun applyLayoutParams(view: View, width: Int, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        view.layoutParams = layoutParams
    }

    /**
     * 重置大小
     */
    @JvmStatic
    fun applyLayoutParams(view: View, size: Int) {
        applyLayoutParams(view, size, size)
    }

    @JvmStatic
    fun applyLayoutParamsMatch(view: View) {
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    @JvmStatic
    fun applyLayoutParamHeightStatusBar(view: View) {
        view.layoutParams = view.layoutParams.apply {
            height = UtilKStatusBar.getHeight()
        }
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 添加全局监听
     */
    @JvmStatic
    fun applySafeAddOnGlobalLayoutObserver(view: View, listener: OnGlobalLayoutListener) {
        try {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    /**
     * 删除全局监听
     */
    @JvmStatic
    fun applySafeRemoveOnGlobalLayoutObserver(view: View, listener: OnGlobalLayoutListener?) {
        try {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    /**
     * 获取焦点
     */
    @JvmStatic
    fun applyRequestFocus(view: View) {
        if (view.isInTouchMode) view.requestFocusFromTouch()
        else view.requestFocus()
    }

    //////////////////////////////////////////////////////////////////////////////

    /**
     * 从父布局删除View
     */
    @OptInApiUse_BaseApplication
    @JvmStatic
    fun removeViewFromParent(view: View): View {
        val viewParent: ViewParent = view.parent
        if (viewParent is ViewGroup && !UtilKActivity.isDestroyed(view.context)) viewParent.removeView(viewParent)
        return view
    }
}