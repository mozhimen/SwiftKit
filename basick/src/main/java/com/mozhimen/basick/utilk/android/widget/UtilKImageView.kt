package com.mozhimen.basick.utilk.android.widget

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.mozhimen.basick.utilk.android.graphics.applyAdjustAlpha
import com.mozhimen.basick.utilk.android.graphics.getContrastColor

/**
 * @ClassName UtilKViewImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 2:35
 * @Version 1.0
 */
fun ImageView.applyImageResourceWhen(bool: Boolean, @DrawableRes statusTrue: Int, @DrawableRes statusFalse: Int) {
    UtilKImageView.applyImageResourceWhen(this, bool, statusTrue, statusFalse)
}

fun ImageView.applyFitDrawable(drawable: Drawable) {
    UtilKImageView.applyFitDrawable(this, drawable)
}

fun ImageView.applyColorFilter(colorRes: Int) {
    UtilKImageView.applyColorFilter(this, colorRes)
}

fun ImageView.applyFillWithStroke(@ColorInt fillColorInt: Int, @ColorInt backgroundColorInt: Int, isDrawRectangle: Boolean = false) {
    UtilKImageView.applyFillWithStroke(this, fillColorInt, backgroundColorInt, isDrawRectangle)
}

fun ImageView.showToastContentDescriptionOnLongClick() {
    UtilKImageView.showToastContentDescriptionOnLongClick(this)
}

object UtilKImageView {

    @JvmStatic
    fun applyImageResourceWhen(imageView: ImageView, bool: Boolean, @DrawableRes statusTrue: Int, @DrawableRes statusFalse: Int) {
        imageView.setImageResource(if (bool) statusTrue else statusFalse)
    }

    /**
     * 适应图片
     * @param imageView ImageView
     * @param drawable Drawable
     */
    @JvmStatic
    fun applyFitDrawable(imageView: ImageView, drawable: Drawable) {
        val layoutParams = imageView.layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = (drawable.intrinsicHeight / (drawable.intrinsicWidth * 1.0f / layoutParams.width)).toInt()
        imageView.layoutParams = layoutParams
    }

    @JvmStatic
    fun applyColorFilter(imageView: ImageView, @ColorInt colorInt: Int) {
        imageView.setColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
    }

    @JvmStatic
    fun applyFillWithStroke(imageView: ImageView, @ColorInt fillColorInt: Int, @ColorInt backgroundColorInt: Int, isDrawRectangle: Boolean = false) {
        GradientDrawable().apply {
            shape = if (isDrawRectangle) GradientDrawable.RECTANGLE else GradientDrawable.OVAL
            setColor(fillColorInt)
            imageView.background = this

            if (backgroundColorInt == fillColorInt || fillColorInt == -2 && backgroundColorInt == -1) {
                setStroke(2, backgroundColorInt.getContrastColor().applyAdjustAlpha(0.5f))
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showToastContentDescriptionOnLongClick(imageView: ImageView) {
        imageView.setOnLongClickListener {
            showToastContentDescription(imageView);true
        }
    }

    @JvmStatic
    fun showToastContentDescription(imageView: ImageView) {
        imageView.contentDescription?.toString()?.showToastOnMain()
    }
}