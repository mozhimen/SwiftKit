package com.mozhimen.basick.utilk.view

import android.R
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.content.UtilKActivity
import com.mozhimen.basick.utilk.datatype.UtilKDataType
import java.util.*


/**
 * @ClassName UtilKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:50
 * @Version 1.0
 */
object UtilKView {
    /**
     * 是否是DecorView
     * @param view View
     * @return Boolean
     */
    @JvmStatic
    fun isDecorView(view: View): Boolean {
        return if (view.id == R.id.content) true
        else TextUtils.equals(view.javaClass.name, "com.android.internal.policy.DecorView")
    }

    /**
     * 获取View绘制区域TOP高度
     * 注: 在Activity的回调方法onWindowFocusChanged()执行后,才能得到预期结果
     * @param activity Activity
     * @return Int
     */
    @JvmStatic
    fun getViewDrawHeight(activity: Activity) =
        activity.window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

    /**
     * 设置背景
     * @param view View
     * @param background Drawable
     */
    @JvmStatic
    fun setBackground(view: View, background: Drawable) {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_16_41_J) {
            view.background = background
        } else {
            view.setBackgroundDrawable(background)
        }
    }

    /**
     * 添加全局监听
     * @param view View
     * @param listener OnGlobalLayoutListener
     */
    @JvmStatic
    fun safeAddGlobalLayoutListener(view: View, listener: OnGlobalLayoutListener) {
        try {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 删除全局监听
     * @param view View
     * @param listener OnGlobalLayoutListener?
     */
    @JvmStatic
    fun safeRemoveGlobalLayoutListener(view: View, listener: OnGlobalLayoutListener?) {
        try {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取焦点
     * @param view View
     */
    @JvmStatic
    fun requestFocus(view: View) {
        if (view.isInTouchMode) {
            view.requestFocusFromTouch()
        } else {
            view.requestFocus()
        }
    }

    /**
     * 寻找父View是否匹配列举的类型
     * @param currentView View
     * @param matches Array<out Class<*>>
     */
    @JvmStatic
    fun getParentViewMatch(currentView: View, vararg matches: Class<*>): View? {
        var view: View = currentView
        while (view.parent != null && view.parent is View) {
            try {
                view = view.parent as View
                if (UtilKDataType.isTypeMatch(view, *matches)) return view
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 寻找父View是否匹配列举的类型
     * @param currentView View
     * @param matches Array<out Class<*>>
     */
    @JvmStatic
    fun isParentViewMatch(currentView: View, vararg matches: Class<*>): Boolean =
        getParentViewMatch(currentView, *matches) != null

    /**
     * 获取指定类型的子View
     * @param group ViewGroup?
     * @param clazz Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> findTypeChildView(group: ViewGroup, clazz: Class<T>): T? {
        val deque: Deque<View> = ArrayDeque()
        deque.add(group)
        while (!deque.isEmpty()) {
            val node = deque.removeFirst()
            if (clazz.isInstance(node)) {
                return clazz.cast(node)
            } else if (node is ViewGroup) {
                var i = 0
                val count = node.childCount
                while (i < count) {
                    deque.add(node.getChildAt(i))
                    i++
                }
            }
        }
        return null
    }

    /**
     * 逐层在父View中查找View
     * @param viewId Int
     * @param sourceView View
     * @return View?
     */
    @JvmStatic
    fun findViewFromParentById(viewId: Int, sourceView: View): View? {
        var tempView: View = sourceView
        while (tempView.parent is View) {
            //逐层在父View中查找，是为了查找离自己最近的目标对象，因为ID可能重复
            tempView = tempView.parent as View
            val targetView = tempView.findViewById<View>(viewId)
            if (targetView != null) return targetView
        }
        return null
    }

    /**
     * 逐层在父View中查找View
     * @param destView View
     * @param sourceView View
     * @return View?
     */
    @JvmStatic
    fun findViewFromParentByView(destView: View, sourceView: View): View? {
        var tempView: View = sourceView
        while (tempView.parent is View) {
            //需要从content一直遍历往前找到decorView下的第一个child，那个为准
            tempView = tempView.parent as View
            if (tempView == destView) return tempView
        }
        return null
    }

    /**
     * 从父布局删除View
     * @param view View
     * @return View
     */
    @JvmStatic
    fun removeViewFromParent(view: View): View {
        val viewParent: ViewParent = view.parent
        if (viewParent is ViewGroup && !UtilKActivity.isActivityDestroyed(view.context)) viewParent.removeView(viewParent)
        return view
    }

    /**
     * 根据View的高度和宽高比, 设置高度
     * @param view View
     * @param ratio Float
     */
    @JvmStatic
    fun setViewRatio(
        view: View,
        ratio: Float
    ) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
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
     * @param view View
     * @param paddingHorizontal Int
     * @param paddingVertical Int
     */
    @JvmStatic
    fun setPadding(
        view: View,
        paddingHorizontal: Int,
        paddingVertical: Int
    ) {
        view.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

    /**
     * 左右内边距
     * @param view View
     * @param padding Int
     */
    @JvmStatic
    fun setPaddingHorizontal(
        view: View,
        padding: Int
    ) {
        view.setPadding(padding, 0, padding, 0)
    }

    /**
     * 上下内边距
     * @param view View
     * @param padding Int
     */
    @JvmStatic
    fun setPaddingVertical(
        view: View,
        padding: Int
    ) {
        view.setPadding(0, padding, 0, padding)
    }

    /**
     * 重置大小
     * @param view View
     * @param width Int
     * @param height Int
     */
    @JvmStatic
    fun resizeSize(
        view: View,
        width: Int,
        height: Int
    ) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        view.layoutParams = layoutParams
    }

    /**
     * 重置大小
     * @param view View
     * @param size Int
     */
    @JvmStatic
    fun resizeSize(
        view: View,
        size: Int
    ) {
        resizeSize(view, size, size)
    }
}