package com.mozhimen.basick.imagek.loader.exts

import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.imagek.loader.ImageKLoader
import com.mozhimen.basick.imagek.loader.mos.BlurTransformation
import com.mozhimen.basick.imagek.loader.mos.CropTransformation
import com.mozhimen.basick.utilk.exts.dp2px

/**
 * @ClassName ExtsKViewImageLoader
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:54
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET)
class ExtsImageK

/**
 * 普通加载
 * @receiver ImageView
 * @param res Any
 */
fun ImageView.loadImage(
    res: Any
) {
    ImageKLoader.loadImage(this, res)
}

/**
 * 加载复杂设置图片
 * @receiver ImageView
 * @param res Any
 * @param placeholder Int
 * @param error Int
 * @param crossFadeEnable Boolean
 * @param crossFadeTime Int
 */
fun ImageView.loadImageComplex(
    res: Any,
    placeholder: Int,
    error: Int,
    crossFadeEnable: Boolean = true,
    crossFadeTime: Int = 1500
) {
    ImageKLoader.loadImageComplex(this, res, placeholder, error, crossFadeEnable, crossFadeTime)
}

/**
 * 加载高斯模糊图
 * @receiver ImageView
 * @param res Any
 * @param radius Float
 * @param sampling Float
 */
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

/**
 * 加载灰度图
 * @receiver ImageView
 * @param res Any
 */
fun ImageView.loadImageGray(
    res: Any
) {
    ImageKLoader.loadImageGray(this, res)
}

/**
 * 加载颜色过滤图片
 * @receiver ImageView
 * @param res Any
 * @param color Int
 */
fun ImageView.loadImageColorFilter(
    res: Any,
    @ColorInt color: Int
) {
    ImageKLoader.loadImageColorFilter(this, res, color)
}

/**
 * 加载圆形图片
 * @receiver ImageView
 * @param res Any
 */
fun ImageView.loadImageCircle(
    res: Any
) {
    ImageKLoader.loadImageCircle(this, res)
}

/**
 * 加载圆角图片
 * @receiver ImageView
 * @param res Any
 * @param cornerRadius Int
 */
fun ImageView.loadImageRoundedCorner(
    res: Any,
    @Px cornerRadius: Float = 6f.dp2px()
) {
    ImageKLoader.loadImageRoundedCorner(this, res, cornerRadius)
}

/**
 * 加载裁剪图片
 * @receiver ImageView
 * @param res Any
 * @param cropType CropType
 */
fun ImageView.loadImageCrop(
    res: Any,
    cropType: CropTransformation.ECropType = CropTransformation.ECropType.CENTER
) {
    ImageKLoader.loadImageCrop(this, res, cropType)
}