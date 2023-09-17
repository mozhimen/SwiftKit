package com.mozhimen.basick.imagek.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mozhimen.basick.imagek.glide.temps.CircleBorderTransform

/**
 * @ClassName ImageKGlide
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 16:53
 * @Version 1.0
 */
@Deprecated(
    message = "replace to coil 用coil替代glide",
    replaceWith = ReplaceWith(
        expression = "ImageKCoil",
        imports = ["com.mozhimen.basick.imagek.coil.ImageKCoil"]
    )
)
object ImageKGlide {


    @JvmStatic
    fun loadImageGlide(imageView: ImageView, res: Any) {
        Glide.with(imageView).load(res)
            .into(imageView)
    }

    @JvmStatic
    fun loadImageComplexGlide(imageView: ImageView, res: Any, placeholder: Int, error: Int) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(error)
            .placeholder(placeholder)
            .into(imageView)
    }

    /**
     * 加载圆形图片
     */
    @JvmStatic
    fun loadImageCircleGlide(imageView: ImageView, res: Any, placeholder: Int, error: Int) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CircleCrop())
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

    /**
     * 加载带边框的圆角图片
     */
    @JvmStatic
    fun loadImageBorderRoundedCornerGlide(
        imageView: ImageView, res: Any, placeholder: Int, error: Int,
        borderWidth: Float,
        borderColor: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CircleBorderTransform(borderWidth, borderColor))
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

    /**
     * 加载圆角图片
     */
    @JvmStatic
    fun loadImageRoundedCornerGlide(
        imageView: ImageView, res: Any, placeholder: Int, error: Int,
        cornerRadius: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .placeholder(placeholder).error(error)
            .into(imageView)
    }
}