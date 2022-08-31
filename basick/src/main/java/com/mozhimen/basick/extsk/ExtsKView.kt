package com.mozhimen.basick.extsk

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntRange
import com.mozhimen.basick.utilk.UtilKView

/**
 * @ClassName ExtsKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:42
 * @Version 1.0
 */
//region # view
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
//endregion

//region # imageView
/**
 * 自适应图片
 * @receiver ImageView
 * @param drawable Drawable
 */
fun ImageView.fitImage(
    drawable: Drawable
) {
    UtilKView.fitImage(this, drawable)
}

/**
 * 加载普通图片
 * @receiver ImageView
 * @param res Any
 */
fun ImageView.load(
    res: Any
) {
    UtilKView.loadImage(this, res)
}

/**
 * 加载普通图片
 * @receiver ImageView
 * @param res Any
 * @param placeholder Int
 * @param error Int
 */
fun ImageView.loadComplex(
    res: Any,
    placeholder: Int,
    error: Int
) {
    UtilKView.loadImageComplex(this, res, placeholder, error)
}

/**
 * 加载圆形图片
 * @receiver ImageView
 * @param res Any
 * @param placeholder Int
 * @param error Int
 */
fun ImageView.loadCircle(
    res: Any,
    placeholder: Int ,
    error: Int
) {
    UtilKView.loadImageCircle(this, res, placeholder, error)
}

/**
 * 加载带边框的圆角图片
 * @receiver ImageView
 * @param res Any
 * @param borderWidth Float
 * @param borderColor Int
 * @param placeholder Int
 * @param error Int
 */
fun ImageView.loadCircleBorder(
    res: Any,
    borderWidth: Float,
    borderColor: Int,
    placeholder: Int,
    error: Int
) {
    UtilKView.loadImageCircleBorder(this, res, borderWidth, borderColor, placeholder, error)
}

/**
 * 加载圆角图片
 * @receiver ImageView
 * @param res Any
 * @param cornerRadius Int
 * @param placeholder Int
 * @param error Int
 */
fun ImageView.loadCorner(
    res: Any,
    cornerRadius: Int,
    placeholder: Int,
    error: Int
) {
    UtilKView.loadImageCorner(this, res, cornerRadius, placeholder, error)
}
//endregion

//region # textView
/**
 * 设置字体的细或粗
 * @receiver TextView
 * @param style Int
 */
fun TextView.fontStyle(@IntRange(from = 0, to = 3) style: Int = Typeface.NORMAL) {
    UtilKView.fontStyle(this, style)
}

/**
 * 设置字体
 * @receiver TextView
 * @param iconFont String
 */
fun TextView.font(iconFont: String = "icons/iconfont.ttf") {
    UtilKView.font(this, iconFont)
}
//endregion

//region # editText
fun EditText.setInputMaxLength(inputMaxLength: Int) {
    UtilKView.setInputMaxLength(this, inputMaxLength)
}
//endregion