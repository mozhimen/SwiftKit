package com.mozhimen.uicorek.imagek

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mozhimen.basick.imagek.glide.ImageKGlide
import com.mozhimen.basick.utilk.android.util.dp2px

/**
 * @ClassName ImageKBindingAdapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/16 22:06
 * @Version 1.0
 */
object ImageKBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["loadImageWhenGlide", "loadImageWhenGlide_statusTrue", "loadImageWhenGlide_statusFalse"], requireAll = true)
    fun loadImageWhenGlide(imageView: ImageView, loadImageWhenGlide: Boolean, loadImageWhenGlide_statusTrue: Any, loadImageWhenGlide_statusFalse: Any) {
        if (loadImageWhenGlide) {
            ImageKGlide.loadImageGlide(imageView, loadImageWhenGlide_statusTrue)
        } else {
            ImageKGlide.loadImageGlide(imageView, loadImageWhenGlide_statusFalse)
        }
    }
    @JvmStatic
    @BindingAdapter(value = ["loadImageBlurGlide", "placeholder"], requireAll = true)
    fun loadImageBlurGlide(imageView: ImageView, loadImageBlurGlide: Any, placeholder: Int) {
        ImageKGlide.loadImageBlurGlide(imageView, loadImageBlurGlide, placeholder)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageRoundedCornerGlide", "roundedCornerRadius"], requireAll = true)
    fun loadImageRoundedCornerGlide(imageView: ImageView, loadImageRoundedCornerGlide: Any, roundedCornerRadius: Int) {
        ImageKGlide.loadImageRoundedCornerGlide(imageView, loadImageRoundedCornerGlide, roundedCornerRadius.dp2px().toInt())
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageRoundedCornerGlide", "roundedCornerRadius"], requireAll = true)
    fun loadImageRoundedCornerGlide(imageView: ImageView, loadImageRoundedCornerGlide: Any, roundedCornerRadius: Float) {
        ImageKGlide.loadImageRoundedCornerGlide(imageView, loadImageRoundedCornerGlide, roundedCornerRadius.dp2px().toInt())
    }
}