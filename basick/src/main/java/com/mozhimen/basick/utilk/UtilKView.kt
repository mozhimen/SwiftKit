package com.mozhimen.basick.utilk

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import java.io.File
import java.lang.IllegalArgumentException


/**
 * @ClassName UtilKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:50
 * @Version 1.0
 */
object UtilKView {

    /**
     * 获取指定类型的子View
     * @param group ViewGroup?
     * @param cls Class<T>
     * @return T?
     */
    fun <T> findTypeView(group: ViewGroup?, cls: Class<T>): T? {
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

    //region # view
    /**
     * 根据View的高度和宽高比, 设置高度
     * @param view View
     * @param ratio Float
     */
    fun setViewRatio(view: View, ratio: Float) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val height: Int = view.height
                if (height > 0) {
                    view.layoutParams.width = (height * ratio).toInt()
                    view.invalidate()
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
    fun setPadding(view: View, paddingHorizontal: Int, paddingVertical: Int) {
        view.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
    }

    /**
     * 左右内边距
     * @param view View
     * @param padding Int
     */
    fun setPaddingHorizontal(view: View, padding: Int) {
        view.setPadding(padding, 0, padding, 0)
    }

    /**
     * 上下内边距
     * @param view View
     * @param padding Int
     */
    fun setPaddingVertical(view: View, padding: Int) {
        view.setPadding(0, padding, 0, padding)
    }

    /**
     * 重置大小
     * @param view View
     * @param width Int
     * @param height Int
     */
    fun resizeSize(view: View, width: Int, height: Int) {
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
    fun resizeSize(view: View, size: Int) {
        resizeSize(view, size, size)
    }
    //endregion

    //region # textView
    /**
     * 设置字体的细或粗
     * @param textView TextView
     * @param style Int
     */
    fun fontStyle(textView: TextView, @IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
        textView.typeface = Typeface.defaultFromStyle(style)
    }

    /**
     * 设置字体
     * @param textView TextView
     * @param iconFont String
     */
    fun font(textView: TextView, fontPath: String = "fonts/iconfont.ttf") {
        val typeface = Typeface.createFromAsset(textView.context.assets, fontPath)
        textView.typeface = typeface
    }
    //endregion

    //region editView
    /**
     * 最多可输入的字符数
     * @param editText EditText
     * @param inputMaxLength Int
     */
    fun setInputMaxLength(editText: EditText, inputMaxLength: Int) {
        if (inputMaxLength > 0) {
            editText.filters = arrayOf(InputFilter.LengthFilter(inputMaxLength))
        }
    }
    //endregion

    //region # imageView
    /**
     * 适应图片
     * @param imageView ImageView
     * @param drawable Drawable
     */
    fun fitImage(imageView: ImageView, drawable: Drawable) {
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        val layoutParams = imageView.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val width = layoutParams.width
        layoutParams.height = (drawableHeight / (drawableWidth * 1.0f / width)).toInt()
        imageView.layoutParams = layoutParams
    }

    /**
     * 加载位图
     * @param imageView ImageView
     * @param bitmap Bitmap
     */
    fun loadImage(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    /**
     * 加载普通图片
     * @param imageView ImageView
     * @param url String
     */
    fun loadImage(
        imageView: ImageView,
        res: Any
    ) {
        if (!isTypeLegal(res)) return
        Glide.with(imageView).load(res).into(imageView)
    }

    /**
     * 加载普通图片(复杂)
     * @param imageView ImageView
     * @param res Any
     * @param placeholder Int
     * @param error Int
     */
    fun loadImageComplex(
        imageView: ImageView,
        res: Any,
        placeholder: Int = android.R.color.transparent,
        error: Int = android.R.color.black
    ) {
        if (!isTypeLegal(res)) return
        Glide.with(imageView).load(res).transition(withCrossFade()).error(error).placeholder(placeholder)
            .into(imageView)
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView
     * @param res Any
     * @param placeholder Int
     * @param error Int
     */
    fun loadImageCircle(
        imageView: ImageView,
        res: Any,
        placeholder: Int = android.R.color.transparent,
        error: Int = android.R.color.black
    ) {
        if (!isTypeLegal(res)) return
        Glide.with(imageView).load(res).transition(withCrossFade())
            .transform(CircleCrop()).placeholder(placeholder).error(error)
            .into(imageView)
    }

    /**
     * 加载带边框的圆角图片
     * @param imageView ImageView
     * @param res Any
     * @param borderWidth Float
     * @param borderColor Int
     * @param placeholder Int
     * @param error Int
     */
    fun loadImageCircleBorder(
        imageView: ImageView,
        res: Any,
        borderWidth: Float = 1f,
        borderColor: Int = UtilKRes.getColor(android.R.color.white),
        placeholder: Int = android.R.color.transparent,
        error: Int = android.R.color.black
    ) {
        if (!isTypeLegal(res)) return
        Glide.with(imageView).load(res).transition(withCrossFade())
            .transform(CircleBorderTransform(borderWidth, borderColor)).placeholder(placeholder).error(error)
            .into(imageView)
    }

    /**
     * 加载圆角图片
     * @param imageView ImageView
     * @param res Any
     * @param cornerRadius Int
     * @param placeholder Int
     * @param error Int
     */
    fun loadImageCorner(
        imageView: ImageView,
        res: Any,
        cornerRadius: Int = 1,
        placeholder: Int = android.R.color.transparent,
        error: Int = android.R.color.black
    ) {
        if (!isTypeLegal(res)) return
        Glide.with(imageView).load(res).transition(withCrossFade())
            .transform(CenterCrop(), RoundedCorners(cornerRadius)).placeholder(placeholder).error(error)
            .into(imageView)
    }

    private class CircleBorderTransform(private val _borderWidth: Float, borderColor: Int) : CircleCrop() {
        private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        init {
            borderPaint.color = borderColor
            borderPaint.style = Paint.Style.STROKE
            borderPaint.strokeWidth = _borderWidth
        }

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            val transform = super.transform(pool, toTransform, outWidth, outHeight)
            val canvas = Canvas(transform)
            val radiusWidth = outWidth / 2f
            val radiusHeight = outHeight / 2f
            canvas.drawCircle(
                radiusWidth,
                radiusHeight,
                radiusWidth.coerceAtMost(radiusHeight) - _borderWidth / 2f,
                borderPaint
            )
            canvas.setBitmap(null)
            return transform
        }
    }

    private fun isTypeLegal(arg: Any): Boolean {
        return if (arg is String || arg is Bitmap || arg is Drawable || arg is Uri || arg is File || arg is Int || arg is ByteArray) {
            true
        } else {
            throw IllegalArgumentException("arg must be String, Bitmap, Drawable, Uri, File, Int, ByteArray!")
        }
    }

    //endregion

    //region # recyclerView
    /**
     * 是否滑动到底部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2Top(recyclerView: RecyclerView): Boolean = !recyclerView.canScrollVertically(-1)

    /**
     * 是否滑动到底部
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2End(recyclerView: RecyclerView): Boolean = !recyclerView.canScrollVertically(1)

    /**
     * 滑动到顶部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2Top2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItemPos = linearLayoutManager.findFirstVisibleItemPosition()
            val lastItemPos = linearLayoutManager.findLastVisibleItemPosition()
            val itemCount = linearLayoutManager.itemCount
            val lastChild: View = recyclerView.getChildAt(lastItemPos - firstItemPos)
            if (lastItemPos == itemCount - 1 && lastChild.bottom <= recyclerView.measuredHeight) {
                return true
            }
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
            val firstVisibleItems =
                (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            // 真实Position就是position - firstVisibleItems[0]
            val itemCount = layoutManager.itemCount
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(lastPositions)
            val lastPosition: Int = lastPositions.maxOf { it }
            val lastChild: View? = recyclerView.getChildAt(lastPosition - firstVisibleItems[0])
            if (lastPosition == itemCount - 1 && lastChild != null && lastChild.bottom <= recyclerView.getMeasuredHeight()) {
                return true
            }
        }
        return false
    }

    /**
     * 滑动到底部2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2End2(recyclerView: RecyclerView): Boolean {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItem = linearLayoutManager.findFirstVisibleItemPosition()
            return recyclerView.getChildAt(0).y == 0f && firstItem == 0
        } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
            val aa = (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstVisibleItemPositions(null)
            return recyclerView.getChildAt(0).y == 0f && aa[0] == 0
        }
        return false
    }

    /**
     * 是否滑动到边缘
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2VerticalEdge(recyclerView: RecyclerView): Boolean =
        isScroll2End(recyclerView) || isScroll2Top(recyclerView)

    /**
     * 是否滑动到边缘2
     * @param recyclerView RecyclerView
     * @return Boolean
     */
    fun isScroll2VerticalEdge2(recyclerView: RecyclerView): Boolean =
        isScroll2End2(recyclerView) || isScroll2Top2(recyclerView)

    /**
     * 是否向上滚动
     * @param dy Int
     * @return Boolean
     */
    fun isScrollUp(dy: Int): Boolean = dy < 0

    /**
     * 是否向下滚动
     * @param dx Int
     * @return Boolean
     */
    fun isScrollDown(dx: Int): Boolean = dx > 0

    /**
     * 找到最后一个可视的Item
     * @param recyclerView RecyclerView
     * @return Int
     */
    fun findLastVisibleItem(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> {
                return layoutManager.findLastVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                return layoutManager.findLastVisibleItemPositions(null)[0]
            }
        }
        return -1
    }

    /**
     * 找到第一个可视的View
     * @param recyclerView RecyclerView
     * @return Int
     */
    fun findFirstVisibleItem(recyclerView: RecyclerView): Int {
        when (val layoutManager = recyclerView.layoutManager) {
            //layoutManager is GridLayoutManager
            is LinearLayoutManager -> {
                return layoutManager.findFirstVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                return layoutManager.findFirstVisibleItemPositions(null)[0]
            }
        }
        return -1
    }
    //endregion
}