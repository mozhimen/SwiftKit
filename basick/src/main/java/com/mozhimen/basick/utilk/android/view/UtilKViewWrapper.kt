package com.mozhimen.basick.utilk.android.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.elemk.commons.I_AListener
import com.mozhimen.basick.elemk.cons.CCons
import com.mozhimen.basick.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.basick.utilk.android.app.UtilKActivityWrapper
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.UtilKLongLogWrapper
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.UtilKAny
import com.mozhimen.basick.utilk.kotlin.strColor2intColor
import com.mozhimen.basick.utilk.kotlinx.coroutines.getViewClickFlow
import com.mozhimen.basick.utilk.kotlinx.coroutines.throttleFirst
import com.mozhimen.basick.utilk.wrapper.UtilKScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * @ClassName UtilKViewFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/7 14:13
 * @Version 1.0
 */
fun View.applyDebounceClickListener(thresholdMillis: Long = 500, block: IA_Listener<View>) {
    UtilKViewWrapper.applyDebounceClickListener(this, block, thresholdMillis)
}

fun View.applyDebounceClickListener(scope: CoroutineScope, thresholdMillis: Long = 500, block: IA_Listener<View>) {
    UtilKViewWrapper.applyDebounceClickListener(this, scope, block, thresholdMillis)
}

fun View.applySuspendDebounceClickListener(scope: CoroutineScope, thresholdMillis: Long = 500, block: suspend CoroutineScope.(View) -> Unit) {
    UtilKViewWrapper.applySuspendDebounceClickListener(this, scope, block, thresholdMillis)
}

//////////////////////////////////////////////////////////////////

fun View.applyVisible() {
    UtilKViewWrapper.applyVisible(this)
}

fun View.applyInVisible() {
    UtilKViewWrapper.applyInVisible(this)
}

fun View.applyGone() {
    UtilKViewWrapper.applyGone(this)
}

//////////////////////////////////////////////////////////////////

fun View.applyVisibleIfElseGone(invoke: I_AListener<Boolean>) {
    UtilKViewWrapper.applyVisibleIfElseGone(this, invoke)
}

fun View.applyVisibleIfElseGone(boolean: Boolean) {
    UtilKViewWrapper.applyVisibleIfElseGone(this, boolean)
}

fun View.applyVisibleIfElseGoneAnimate(boolean: Boolean, durationMillis: Long) {
    UtilKViewWrapper.applyVisibleIfElseGoneAnimate(this, boolean, durationMillis)
}

fun View.applyVisibleIfElseInVisible(invoke: I_AListener<Boolean>) {
    UtilKViewWrapper.applyVisibleIfElseInVisible(this, invoke)
}

fun View.applyVisibleIfElseInVisible(boolean: Boolean) {
    UtilKViewWrapper.applyVisibleIfElseInVisible(this, boolean)
}

//////////////////////////////////////////////////////////////////

fun View.applyVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewWrapper.applyVisibleIf(this, invoke)
}

fun View.applyVisibleIf(boolean: Boolean) {
    UtilKViewWrapper.applyVisibleIf(this, boolean)
}

fun View.applyInVisibleIf(invoke: I_AListener<Boolean>) {
    UtilKViewWrapper.applyInVisibleIf(this, invoke)
}

fun View.applyInVisibleIf(boolean: Boolean) {
    UtilKViewWrapper.applyInVisibleIf(this, boolean)
}

fun View.applyGoneIf(invoke: I_AListener<Boolean>) {
    UtilKViewWrapper.applyGoneIf(this, invoke)
}

fun View.applyGoneIf(boolean: Boolean) {
    UtilKViewWrapper.applyGoneIf(this, boolean)
}

//////////////////////////////////////////////////////////////////

@OApiUse_BaseApplication
fun View.removeView_ofParent() {
    UtilKViewWrapper.removeView_ofParent(this)
}

//////////////////////////////////////////////////////////////////

object UtilKViewWrapper : IUtilK {

    //寻找父View是否匹配列举的类型
    @JvmStatic
    fun getParent_ofClazz(view: View, vararg matches: Class<*>): View? {
        var tempView: View = view
        while (tempView.parent != null && tempView.parent is View) {
            try {
                tempView = tempView.parent as View
                if (UtilKAny.isObjTypeMatch(tempView, *matches)) return tempView
            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.e(UtilKView.TAG)
            }
        }
        return null
    }

    //显示占比
    @JvmStatic
    fun getVisiblePercent(view: View): Int {
        if (view.isShown) {
            val rect = UtilKView.getGlobalVisibleRect(view)
            return if (rect.top > 0 && rect.left < UtilKScreen.getWidth_ofDisplayMetrics_ofSys())
                ((rect.width().toFloat() * rect.height().toFloat()) / (view.width.toFloat() * view.height.toFloat()) * 100).toInt()
            else 0
        }
        return 0
    }

    @JvmStatic
    fun getBitmap_ofViewBackground(view: View, fullScreen: Boolean): Bitmap? =
        getBitmap_ofViewBackground(view, 1.0f, fullScreen, 0, 0)

    @JvmStatic
    fun getBitmap_ofViewBackground(view: View, scaledRatio: Float, fullScreen: Boolean, cutoutX: Int, cutoutY: Int): Bitmap? {
        if (view.width <= 0 || view.height <= 0) {
            UtilKLongLogWrapper.e("getViewBitmap  >>  宽或者高为空")
            return null
        }
        val statusBarHeight = UtilKStatusBar.getHeight(false)
        var tempBitmap: Bitmap
        UtilKLongLogWrapper.i("getViewBitmap 模糊原始图像分辨率 [" + view.width + " x " + view.height + "]")
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
        UtilKLongLogWrapper.i("getViewBitmap 模糊缩放图像分辨率 [" + tempBitmap.width + " x " + tempBitmap.height + "]")
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

    //////////////////////////////////////////////////////////////////

    //寻找父View是否匹配列举的类型
    @JvmStatic
    fun isParentMatchClazz(view: View, vararg matches: Class<*>): Boolean =
        getParent_ofClazz(view, *matches) != null

    fun <V : View> isDebounceClickable(view: V, thresholdMillis: Long = 500): Boolean {
        var isClickable = false
        val currentClickTime = System.currentTimeMillis()
        val lastClickTime = UtilKView.getLongTag(view, CCons.DEBOUNCE_LAST_CLICK_TIME, currentClickTime)
        if (currentClickTime - lastClickTime >= UtilKView.getLongTag(view, CCons.DEBOUNCE_THRESHOLD_MILLIS, thresholdMillis)) {
            isClickable = true
            view.setTag(CCons.DEBOUNCE_LAST_CLICK_TIME, currentClickTime)
        }
        return isClickable
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyDebounceClickListener(view: View, scope: CoroutineScope, block: IA_Listener<View>, thresholdMillis: Long = 500) {
        view.getViewClickFlow().throttleFirst(thresholdMillis).onEach { block.invoke(view) }.launchIn(scope)
    }

    @JvmStatic
    fun applySuspendDebounceClickListener(view: View, scope: CoroutineScope, block: suspend CoroutineScope.(View) -> Unit, thresholdMillis: Long = 500) {
        view.getViewClickFlow().throttleFirst(thresholdMillis).onEach { scope.block(view) }.launchIn(scope)
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyDebounceClickListener(view: View, block: IA_Listener<View>, thresholdMillis: Long = 500) {
        view.setTag(CCons.DEBOUNCE_THRESHOLD_MILLIS, thresholdMillis)
        view.setOnClickListener {
            if (isDebounceClickable(view, thresholdMillis)) {
                block.invoke(view)
            }
        }
    }

    //////////////////////////////////////////////////////////////////

    // 根据View的高度和宽高比, 设置高度
    @JvmStatic
    fun applyViewRatio(view: View, ratio: Float) {
        view.addAndRemoveOnGlobalLayoutListener {
            if (view.height > 0) {
                view.layoutParams.width = (view.height * ratio).toInt()
                view.postInvalidate()
            }
        }
    }

    @JvmStatic
    fun applyVisible(view: View) {
        if (!UtilKView.isVisible(view))
            view.visibility = View.VISIBLE
    }

    @JvmStatic
    fun applyInVisible(view: View) {
        if (!UtilKView.isInvisible(view))
            view.visibility = View.INVISIBLE
    }

    @JvmStatic
    fun applyGone(view: View) {
        if (!UtilKView.isGone(view))
            view.visibility = View.GONE
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyVisibleIfElseGone(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
        else applyGone(view)
    }

    @JvmStatic
    fun applyVisibleIfElseGone(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
        else applyGone(view)
    }

    @JvmStatic
    fun applyVisibleIfElseGoneAnimate(view: View, boolean: Boolean, durationMillis: Long) {
        val alpha = if (boolean) 1.0f else 0.0f
        ObjectAnimator.ofFloat(view, "alpha", alpha).apply {
            duration = durationMillis
            setAutoCancel(true)
            addListener(
                onStart = {
                    if (boolean)
                        view.isVisible = true
                },
                onEnd = {
                    if (!boolean)
                        view.isVisible = false
                }
            )
            start()
        }
    }

    @JvmStatic
    fun applyVisibleIfElseInVisible(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
        else applyInVisible(view)
    }

    @JvmStatic
    fun applyVisibleIfElseInVisible(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
        else applyInVisible(view)
    }

    //////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyVisible(view)
    }

    @JvmStatic
    fun applyVisibleIf(view: View, boolean: Boolean) {
        if (boolean) applyVisible(view)
    }

    @JvmStatic
    fun applyInVisibleIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyInVisible(view)
    }

    @JvmStatic
    fun applyInVisibleIf(view: View, boolean: Boolean) {
        if (boolean) applyInVisible(view)
    }

    @JvmStatic
    fun applyGoneIf(view: View, invoke: I_AListener<Boolean>) {
        if (invoke.invoke()) applyGone(view)
    }

    @JvmStatic
    fun applyGoneIf(view: View, boolean: Boolean) {
        if (boolean) applyGone(view)
    }

    //////////////////////////////////////////////////////////////////

    //逐层在父View中查找View
    @JvmStatic
    fun findViewById_ofParent(view: View, viewId: Int): View? {
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

    //逐层在父View中查找View
    @JvmStatic
    fun findViewByView_ofParent(destView: View, sourceView: View): View? {
        var tempView: View = sourceView
        while (tempView.parent is View) {
            //需要从content一直遍历往前找到decorView下的第一个child，那个为准
            tempView = tempView.parent as View
            if (tempView == destView)
                return tempView
        }
        return null
    }

    //从父布局删除View
    @JvmStatic
    @OApiUse_BaseApplication
    fun removeView_ofParent(view: View): View {
        val viewParent: ViewParent? = view.parent
        if (viewParent != null && viewParent is ViewGroup && !UtilKActivityWrapper.isFinishingOrDestroyed(view.context)) {
            UtilKLogWrapper.w(TAG,"removeView_ofParent")
            viewParent.removeView(view)
        }
        return view
    }
}