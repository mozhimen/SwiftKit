package com.mozhimen.basick.utilk.exts

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
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
    UtilKImageView.setColorFilter(this, colorRes)
}

fun ImageView.toastContentDescriptionOnLongClick() {
    UtilKImageView.toastContentDescriptionOnLongClick(this)
}

fun ImageView.setFillWithStroke(fillColor: Int, backgroundColor: Int, drawRectangle: Boolean = false) {
    GradientDrawable().apply {
        shape = if (drawRectangle) GradientDrawable.RECTANGLE else GradientDrawable.OVAL
        setColor(fillColor)
        background = this

        if (backgroundColor == fillColor || fillColor == -2 && backgroundColor == -1) {
            val strokeColor = backgroundColor.getContrastColor().adjustAlpha(0.5f)
            setStroke(2, strokeColor)
        }
    }
}