package com.mozhimen.basick.utilk.exts

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import com.mozhimen.basick.utilk.view.UtilKImageView

/**
 * @ClassName ExtsKViewImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:47
 * @Version 1.0
 */
/**
 * 自适应图片
 * @receiver ImageView
 * @param drawable Drawable
 */
fun ImageView.fitImage(drawable: Drawable) {
    UtilKImageView.fitImage(this, drawable)
}

fun ImageView.applyColorFilter(colorRes: Int) {
    UtilKImageView.applyColorFilter(this, colorRes)
}

fun ImageView.toastContentDescriptionOnLongClick() {
    UtilKImageView.toastContentDescriptionOnLongClick(this)
}

fun ImageView.setFillWithStroke(@ColorInt fillColorInt: Int, @ColorInt backgroundColorInt: Int, isDrawRectangle: Boolean = false) {
    UtilKImageView.setFillWithStroke(this, fillColorInt, backgroundColorInt, isDrawRectangle)
}