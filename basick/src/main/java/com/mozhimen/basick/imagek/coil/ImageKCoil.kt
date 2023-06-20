package com.mozhimen.basick.imagek.coil

import android.content.Context
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import coil.Coil
import coil.imageLoader
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import com.mozhimen.basick.imagek.coil.cons.CCoilBlur
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.res.dp2px
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.imagek.coil.mos.BlurTransformation
import com.mozhimen.basick.imagek.coil.mos.ColorFilterTransformation
import com.mozhimen.basick.imagek.coil.mos.CropTransformation
import com.mozhimen.basick.imagek.coil.mos.GrayscaleTransformation

/**
 * @ClassName UtilKImageLoader
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 0:25
 * @Version 1.0
 */
fun ImageView.loadImage(res: Any) {
    ImageKLoader.loadImage(this, res)
}

fun ImageView.loadImageComplex(
    res: Any, placeholder: Int, error: Int,
    crossFadeEnable: Boolean = true, crossFadeTime: Int = 1500
) {
    ImageKLoader.loadImageComplex(this, res, placeholder, error, crossFadeEnable, crossFadeTime)
}

fun ImageView.loadImageBlur(
    res: Any, placeholder: Int,
    crossFadeEnable: Boolean = true, crossFadeTime: Int = 1500,
    @FloatRange(from = 0.0, to = 25.0) radius: Float = CCoilBlur.RADIUS,
    @FloatRange(from = 0.0, to = Double.MAX_VALUE) sampling: Float = CCoilBlur.SAMPLING
) {
    ImageKLoader.loadImageBlur(this, res, placeholder, crossFadeEnable, crossFadeTime, radius, sampling)
}

fun ImageView.loadImageGray(res: Any) {
    ImageKLoader.loadImageGray(this, res)
}

fun ImageView.loadImageColorFilter(res: Any, @ColorInt color: Int) {
    ImageKLoader.loadImageColorFilter(this, res, color)
}

fun ImageView.loadImageCircle(res: Any) {
    ImageKLoader.loadImageCircle(this, res)
}

fun ImageView.loadImageCircleComplex(
    res: Any, placeholder: Int, error: Int,
    crossFadeEnable: Boolean = true, crossFadeTime: Int = 1000
) {
    ImageKLoader.loadImageCircleComplex(this, res, placeholder, error, crossFadeEnable, crossFadeTime)
}

fun ImageView.loadImageRoundedCorner(
    res: Any, @Px cornerRadius: Float = 6f.dp2px()
) {
    ImageKLoader.loadImageRoundedCorner(this, res, cornerRadius)
}

fun ImageView.loadImageCrop(
    res: Any, cropType: CropTransformation.ECropType = CropTransformation.ECropType.CENTER
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
        imageView: ImageView, res: Any, placeholder: Int, error: Int,
        crossFadeEnable: Boolean = true, crossFadeTime: Int = 1000
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
        imageView: ImageView, res: Any, placeholder: Int,
        crossFadeEnable: Boolean = true, crossFadeTime: Int = 1500,
        @FloatRange(from = 0.0, to = 25.0) radius: Float = CCoilBlur.RADIUS,
        @FloatRange(from = 0.0, to = Double.MAX_VALUE) sampling: Float = CCoilBlur.SAMPLING
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
    fun loadImageGray(imageView: ImageView, res: Any) {
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
    fun loadImageColorFilter(imageView: ImageView, res: Any, @ColorInt color: Int) {
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
    fun loadImageCircle(imageView: ImageView, res: Any) {
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
        imageView: ImageView, res: Any, placeholder: Int, error: Int,
        crossFadeEnable: Boolean = true, crossFadeTime: Int = 1000
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
        imageView: ImageView, res: Any,
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
        imageView: ImageView, res: Any,
        cropType: CropTransformation.ECropType = CropTransformation.ECropType.CENTER
    ) {
        imageView.load(res) {
            transformations(CropTransformation(cropType))
        }
    }
}