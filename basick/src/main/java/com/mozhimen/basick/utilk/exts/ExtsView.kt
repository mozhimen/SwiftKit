package com.mozhimen.basick.utilk.exts

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.utilk.view.*

/**
 * @ClassName ExtsKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:42
 * @Version 1.0
 */
/**
 * 四周内边距
 * @receiver View
 * @param paddingHorizontal Int
 * @param paddingVertical Int
 */
fun View.setPadding(paddingHorizontal: Int, paddingVertical: Int) {
    UtilKView.setPadding(this, paddingHorizontal, paddingVertical)
}

/**
 * 左右内边距
 * @receiver View
 * @param padding Int
 */
fun View.setPaddingHorizontal(padding: Int) {
    UtilKView.setPaddingHorizontal(this, padding)
}

/**
 * 上下内边距
 * @receiver View
 * @param padding Int
 */
fun View.setPaddingVertical(padding: Int) {
    UtilKView.setPaddingVertical(this, padding)
}

/**
 * 重置大小
 * @receiver ImageView
 * @param size Int
 */
fun View.resizeSize(size: Int) {
    UtilKView.resizeSize(this, size)
}

/**
 * 重置大小
 * @receiver ImageView
 * @param width Int
 * @param height Int
 */
fun View.resizeSize(width: Int, height: Int) {
    UtilKView.resizeSize(this, width, height)
}

fun View.toGone() {
    UtilKView.toGone(this)
}

fun View.toVisible() {
    UtilKView.toVisible(this)
}

fun View.toGoneIf(boolean: Boolean) {
    UtilKView.toGoneIf(this, boolean)
}

fun View.toVisibleIf(boolean: Boolean) {
    UtilKView.toVisibleIf(this, boolean)
}

fun View.isVisible(): Boolean =
    UtilKView.isVisible(this)

fun View.isInvisible(): Boolean =
    UtilKView.isInvisible(this)

fun View.isGone(): Boolean =
    UtilKView.isGone(this)