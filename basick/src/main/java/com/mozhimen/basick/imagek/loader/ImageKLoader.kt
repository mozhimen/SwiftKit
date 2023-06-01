package com.mozhimen.basick.imagek.loader

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.res.dp2px
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.imagek.loader.mos.BlurTransformation
import com.mozhimen.basick.imagek.loader.mos.ColorFilterTransformation
import com.mozhimen.basick.imagek.loader.mos.CropTransformation
import com.mozhimen.basick.imagek.loader.mos.GrayscaleTransformation

/**
 * @ClassName UtilKImageLoader
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:25
 * @Version 1.0
 */
fun ImageView.loadImage(
    res: Any
) {
    ImageKLoader.loadImage(this, res)
}

fun ImageView.loadImageComplex(
    res: Any,
    placeholder: Int,
    error: Int,
    crossFadeEnable: Boolean = true,
    crossFadeTime: Int = 1500
) {
    ImageKLoader.loadImageComplex(this, res, placeholder, error, crossFadeEnable, crossFadeTime)
}

fun ImageView.loadImageBlur(
    res: Any,
    placeholder: Int,
    crossFadeEnable: Boolean = true,
    crossFadeTime: Int = 1500,
    @FloatRange(from = 0.0, to = 25.0) radius: Float = BlurTransformation.DEFAULT_RADIUS,
    @FloatRange(from = 0.0, to = Double.MAX_VALUE) sampling: Float = BlurTransformation.DEFAULT_SAMPLING
) {
    ImageKLoader.loadImageBlur(this, res, placeholder, crossFadeEnable, crossFadeTime, radius, sampling)
}

fun ImageView.loadImageGray(
    res: Any
) {
    ImageKLoader.loadImageGray(this, res)
}

fun ImageView.loadImageColorFilter(
    res: Any,
    @ColorInt color: Int
) {
    ImageKLoader.loadImageColorFilter(this, res, color)
}

fun ImageView.loadImageCircle(
    res: Any
) {
    ImageKLoader.loadImageCircle(this, res)
}

fun ImageView.loadImageCircleComplex(
    res: Any,
    placeholder: Int,
    error: Int,
    crossFadeEnable: Boolean = true,
    crossFadeTime: Int = 1000
) {
    ImageKLoader.loadImageCircleComplex(this, res, placeholder, error, crossFadeEnable, crossFadeTime)
}

fun ImageView.loadImageRoundedCorner(
    res: Any,
    @Px cornerRadius: Float = 6f.dp2px()
) {
    ImageKLoader.loadImageRoundedCorner(this, res, cornerRadius)
}

fun ImageView.loadImageCrop(
    res: Any,
    cropType: CropTransformation.ECropType = CropTransformation.ECropType.CENTER
) {
    ImageKLoader.loadImageCrop(this, res, cropType)
}

@AManifestKRequire(CPermission.INTERNET)
object ImageKLoader {
    /**
     * 普通加载
     * @param imageView ImageView
     * @param res Any
     */
    @JvmStatic
    fun loadImage(imageView: ImageView, res: Any) {
        imageView.load(res)
    }

    /**
     * 加载复杂设置图片
     * @param imageView ImageView
     * @param res Any
     * @param placeholder Int
     * @param error Int
     * @param crossFadeEnable Boolean
     * @param crossFadeTime Int
     */
    @JvmStatic
    fun loadImageComplex(
        imageView: ImageView,
        res: Any,
        placeholder: Int,
        error: Int,
        crossFadeEnable: Boolean = true,
        crossFadeTime: Int = 1000
    ) {
        imageView.load(res) {
            crossfade(crossFadeEnable)
            crossfade(crossFadeTime)
            placeholder(placeholder)
            error(error)
        }
    }

    /**
     * 加载高斯模糊图
     * @param imageView ImageView
     * @param res Any
     * @param radius Float
     * @param sampling Float
     */
    @JvmStatic
    fun loadImageBlur(
        imageView: ImageView,
        res: Any,
        placeholder: Int,
        crossFadeEnable: Boolean = true,
        crossFadeTime: Int = 1500,
        @FloatRange(from = 0.0, to = 25.0) radius: Float = BlurTransformation.DEFAULT_RADIUS,
        @FloatRange(from = 0.0, to = Double.MAX_VALUE) sampling: Float = BlurTransformation.DEFAULT_SAMPLING
    ) {
        imageView.load(res) {
            crossfade(crossFadeEnable)
            crossfade(crossFadeTime)
            placeholder(placeholder)
            transformations(BlurTransformation(imageView.context, radius, sampling))
        }
    }

    /**
     * 加载灰度图
     * @param imageView ImageView
     * @param res Any
     */
    @JvmStatic
    fun loadImageGray(
        imageView: ImageView,
        res: Any
    ) {
        imageView.load(res) {
            transformations(GrayscaleTransformation())
        }
    }

    /**
     * 加载颜色过滤图片
     * @param imageView ImageView
     * @param res Any
     * @param color Int
     */
    @JvmStatic
    fun loadImageColorFilter(
        imageView: ImageView,
        res: Any,
        @ColorInt color: Int
    ) {
        imageView.load(res) {
            transformations(ColorFilterTransformation(color))
        }
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView
     * @param res Any
     */
    @JvmStatic
    fun loadImageCircle(
        imageView: ImageView,
        res: Any
    ) {
        imageView.load(res) {
            transformations(CircleCropTransformation())
        }
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView
     * @param res Any
     * @param placeholder Int
     * @param error Int
     * @param crossFadeEnable Boolean
     * @param crossFadeTime Int
     */
    @JvmStatic
    fun loadImageCircleComplex(
        imageView: ImageView,
        res: Any,
        placeholder: Int,
        error: Int,
        crossFadeEnable: Boolean = true,
        crossFadeTime: Int = 1000
    ) {
        imageView.load(res) {
            transformations(CircleCropTransformation())
            crossfade(crossFadeEnable)
            crossfade(crossFadeTime)
            placeholder(placeholder)
            error(error)
        }
    }


    /**
     * 加载圆角图片
     * @param imageView ImageView
     * @param res Any
     * @param roundedCornerRadius Int
     */
    @JvmStatic
    fun loadImageRoundedCorner(
        imageView: ImageView,
        res: Any,
        @Px roundedCornerRadius: Float = 6f.dp2px()
    ) {
        imageView.load(res) {
            transformations(RoundedCornersTransformation(roundedCornerRadius))
        }
    }

    /**
     * 加载裁剪图片
     * @param imageView ImageView
     * @param res Any
     * @param cropType CropType
     */
    @JvmStatic
    fun loadImageCrop(
        imageView: ImageView,
        res: Any,
        cropType: CropTransformation.ECropType = CropTransformation.ECropType.CENTER
    ) {
        imageView.load(res) {
            transformations(CropTransformation(cropType))
        }
    }

    //用coil替代glide
//    /**
//     * 适应图片
//     * @param imageView ImageView
//     * @param drawable Drawable
//     */
//    @JvmStatic
//    fun fitImage(
//        imageView: ImageView,
//        drawable: Drawable
//    ) {
//        val drawableWidth = drawable.intrinsicWidth
//        val drawableHeight = drawable.intrinsicHeight
//        val layoutParams = imageView.layoutParams ?: ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        val width = layoutParams.width
//        layoutParams.height = (drawableHeight / (drawableWidth * 1.0f / width)).toInt()
//        imageView.layoutParams = layoutParams
//    }
//
//    /**
//     * 加载普通图片
//     * @param imageView ImageView
//     * @param res Any
//     */
//    @JvmStatic
//    fun loadImage(
//        imageView: ImageView,
//        res: Any
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).into(imageView)
//    }
//
//    /**
//     * 加载普通图片(复杂)
//     * @param imageView ImageView
//     * @param res Any
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageComplex(
//        imageView: ImageView,
//        res: Any,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade()).error(error).placeholder(placeholder)
//            .into(imageView)
//    }
//
//    /**
//     * 加载圆形图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCircle(
//        imageView: ImageView,
//        res: Any,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CircleCrop()).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * 加载带边框的圆角图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param borderWidth Float
//     * @param borderColor Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCircleBorder(
//        imageView: ImageView,
//        res: Any,
//        borderWidth: Float,
//        borderColor: Int,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CircleBorderTransform(borderWidth, borderColor)).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * 加载圆角图片
//     * @param imageView ImageView
//     * @param res Any
//     * @param cornerRadius Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    fun loadImageCorner(
//        imageView: ImageView,
//        res: Any,
//        cornerRadius: Int,
//        placeholder: Int,
//        error: Int
//    ) {
//        if (!isGlideTypeMatch(res)) return
//        Glide.with(imageView).load(res).transition(DrawableTransitionOptions.withCrossFade())
//            .transform(CenterCrop(), RoundedCorners(cornerRadius)).placeholder(placeholder).error(error)
//            .into(imageView)
//    }
//
//    /**
//     * glide类型匹配
//     * @param arg Any
//     * @return Boolean
//     */
//    @JvmStatic
//    fun isGlideTypeMatch(arg: Any): Boolean =
//        UtilKDataType.isTypeMatch(
//            arg,
//            String::class.java, Bitmap::class.java, Uri::class.java, File::class.java, Number::class.java, ByteArray::class.java,
//        )
//
//    private class CircleBorderTransform(private val _borderWidth: Float, borderColor: Int) : CircleCrop() {
//        private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//        init {
//            borderPaint.color = borderColor
//            borderPaint.style = Paint.Style.STROKE
//            borderPaint.strokeWidth = _borderWidth
//        }
//
//        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
//            val transform = super.transform(pool, toTransform, outWidth, outHeight)
//            val canvas = Canvas(transform)
//            val radiusWidth = outWidth / 2f
//            val radiusHeight = outHeight / 2f
//            canvas.drawCircle(
//                radiusWidth,
//                radiusHeight,
//                radiusWidth.coerceAtMost(radiusHeight) - _borderWidth / 2f,
//                borderPaint
//            )
//            canvas.setBitmap(null)
//            return transform
//        }
//    }
}