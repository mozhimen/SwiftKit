package com.mozhimen.basick.utilk.view

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.mozhimen.basick.utilk.UtilKDataType
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
     * @param cls Class<T>
     * @return T?
     */
    @JvmStatic
    fun <T> findTypeChildView(
        group: ViewGroup?,
        cls: Class<T>
    ): T? {
        if (group == null) {
            return null
        }
        val deque: Deque<View> = ArrayDeque()
        deque.add(group)
        while (!deque.isEmpty()) {
            val node = deque.removeFirst()
            if (cls.isInstance(node)) {
                return cls.cast(node)
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
     * @param parentView View
     * @return View?
     */
    @JvmStatic
    fun findViewFromParentById(viewId: Int, parentView: View): View? {
        if (viewId == 0) return null
        var viewTree: View = parentView
        while (viewTree.parent is View) {
            //逐层在父View中查找，是为了查找离自己最近的目标对象，因为ID可能重复
            viewTree = viewTree.parent as View
            val targetView = viewTree.findViewById<View>(viewId)
            if (targetView != null) {
                return targetView
            }
        }
        return null
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
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val height: Int = view.height
                if (height > 0) {
                    view.layoutParams.width = (height * ratio).toInt()
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