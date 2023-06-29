package com.mozhimen.uicorek.imagek.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.android.util.dp2px
import com.mozhimen.basick.imagek.coil.ImageKLoader

/**
 * @ClassName ImageKBindingAdapter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 14:20
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET)
object ImageKBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["loadImageWhen", "loadImageWhen_statusTrue", "loadImageWhen_statusFalse"], requireAll = true)
    fun loadImageWhen(imageView: ImageView, loadImageWhen: Boolean, loadImageWhen_statusTrue: Any, loadImageWhen_statusFalse: Any) {
        if (loadImageWhen) {
            ImageKLoader.loadImage(imageView, loadImageWhen_statusTrue)
        } else {
            ImageKLoader.loadImage(imageView, loadImageWhen_statusFalse)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageWhen2", "loadImageWhen2_condition2", "loadImageWhen2_status1", "loadImageWhen2_status2", "loadImageWhen2_status3", "loadImageWhen2_status4"], requireAll = true)
    fun loadImageWhen2(
        imageView: ImageView,
        loadImageWhen2: Boolean,
        loadImageWhen2_condition2: Boolean,
        loadImageWhen2_status1: Any,
        loadImageWhen2_status2: Any,
        loadImageWhen2_status3: Any,
        loadImageWhen2_status4: Any
    ) {
        when {
            loadImageWhen2 && loadImageWhen2_condition2 -> ImageKLoader.loadImage(imageView, loadImageWhen2_status1)
            loadImageWhen2 && !loadImageWhen2_condition2 -> ImageKLoader.loadImage(imageView, loadImageWhen2_status2)
            !loadImageWhen2 && loadImageWhen2_condition2 -> ImageKLoader.loadImage(imageView, loadImageWhen2_status3)
            !loadImageWhen2 && !loadImageWhen2_condition2 -> ImageKLoader.loadImage(imageView, loadImageWhen2_status4)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(imageView: ImageView, res: Any) {
        ImageKLoader.loadImage(imageView, res)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageBlur", "placeholder"], requireAll = true)
    fun loadImageBlur(imageView: ImageView, res: Any, placeholder: Int) {
        ImageKLoader.loadImageBlur(imageView, res, placeholder)
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageRoundedCorner", "roundedCornerRadius"], requireAll = true)
    fun loadImageRoundedCorner(imageView: ImageView, res: Any, roundedCornerRadius: Int) {
        ImageKLoader.loadImageRoundedCorner(imageView, res, roundedCornerRadius.dp2px())
    }

    @JvmStatic
    @BindingAdapter(value = ["loadImageRoundedCorner", "roundedCornerRadius"], requireAll = true)
    fun loadImageRoundedCorner(imageView: ImageView, res: Any, roundedCornerRadius: Float) {
        ImageKLoader.loadImageRoundedCorner(imageView, res, roundedCornerRadius.dp2px())
    }

    //    /**
//     * 加载圆角图片
//     * @param imageView ImageView
//     * @param loadCorner Any
//     * @param cornerRadius Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    @BindingAdapter(value = ["loadCorner", "cornerRadius", "placeholder", "error"], requireAll = false)
//    fun loadImageCorner(
//        imageView: ImageView,
//        loadCorner: Any,
//        cornerRadius: Int = 2,
//        placeholder: Int = R.mipmap.refresh_loading,
//        error: Int = R.mipmap.layoutk_empty
//    ) {
//        UtilKImageLoader.loadImageCorner(imageView, loadCorner, cornerRadius, placeholder, error)
//    }
//    /**
//     * 加载图片
//     * @param imageView ImageView
//     * @param res Any
//     */
//    @JvmStatic
//    @BindingAdapter("load")
//    fun loadImage(imageView: ImageView, res: Any) {
//        imageView.load(res)
//    }
//
//    /**
//     * 加载图片(复杂场景)
//     * @param view ImageView
//     * @param loadImageComplex Any
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    @BindingAdapter(value = ["loadComplex", "placeholder", "error"], requireAll = false)
//    fun loadImageComplex(
//        imageView: ImageView,
//        loadImageComplex: Any,
//        placeholder: Int = R.mipmap.refresh_loading,
//        error: Int = R.mipmap.layoutk_empty
//    ) {
//        UtilKImageLoader.loadImageComplex(imageView, loadImageComplex, placeholder, error)
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
//    @BindingAdapter(value = ["loadCircle", "placeholder", "error"], requireAll = false)
//    fun loadImageCircle(
//        imageView: ImageView,
//        loadCircle: Any,
//        placeholder: Int = R.mipmap.refresh_loading,
//        error: Int = R.mipmap.layoutk_empty
//    ) {
//        UtilKImageLoader.loadImageCircle(imageView, loadCircle, placeholder, error)
//    }
//
//    /**
//     * 加载带边框的圆角图片
//     * @param imageView ImageView
//     * @param loadCircleBorder Any
//     * @param borderWidth Int
//     * @param borderColor Int
//     * @param placeholder Int
//     * @param error Int
//     */
//    @JvmStatic
//    @BindingAdapter(value = ["loadCircleBorder", "borderWidth", "borderColor", "placeholder", "error"], requireAll = false)
//    fun loadImageCircleBorder(
//        imageView: ImageView,
//        loadCircleBorder: Any,
//        borderWidth: Int = 2,
//        borderColor: Int = android.R.color.white,
//        placeholder: Int = R.mipmap.refresh_loading,
//        error: Int = R.mipmap.layoutk_empty
//    ) {
//        UtilKImageLoader.loadImageCircleBorder(imageView, loadCircleBorder, borderWidth.toFloat(), borderColor, placeholder, error)
//    }
//

}