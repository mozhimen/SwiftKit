package com.mozhimen.basick.utilk.view

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.mozhimen.basick.utilk.exts.showToastOnMain

/**
 * @ClassName UtilKViewImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 2:35
 * @Version 1.0
 */
object UtilKImageView {
    /**
     * 适应图片
     * @param imageView ImageView
     * @param drawable Drawable
     */
    @JvmStatic
    fun fitImage(
        imageView: ImageView,
        drawable: Drawable
    ) {
        val layoutParams = imageView.layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = (drawable.intrinsicHeight / (drawable.intrinsicWidth * 1.0f / layoutParams.width)).toInt()
        imageView.layoutParams = layoutParams
    }

    @JvmStatic
    fun setColorFilter(
        imageView: ImageView,
        colorRes: Int
    ) {
        imageView.setColorFilter(colorRes, PorterDuff.Mode.SRC_IN)
    }

    @JvmStatic
    fun toastContentDescriptionOnLongClick(imageView: ImageView) {
        imageView.setOnLongClickListener { toastContentDescription(imageView);true }
    }

    @JvmStatic
    fun toastContentDescription(imageView: ImageView) {
        imageView.contentDescription?.toString()?.showToastOnMain()
    }
}