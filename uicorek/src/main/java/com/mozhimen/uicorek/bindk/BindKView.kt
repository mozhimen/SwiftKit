package com.mozhimen.uicorek.bindk

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mozhimen.basick.utilk.UtilKView
import com.mozhimen.uicorek.R
import java.io.File

/**
 * @ClassName BindKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/15 4:40
 * @Version 1.0
 */
object BindKView {
    /**
     * 根据View的高度和宽高比, 设置高度
     * @param view View
     * @param ratio Float
     */
    @JvmStatic
    @BindingAdapter("viewRatio")
    fun setViewRatio(view: View, viewRatio: Float) {
        UtilKView.setViewRatio(view, viewRatio)
    }

    /**
     * 加载图片
     * @param view ImageView
     * @param res Any
     */
    @JvmStatic
    @BindingAdapter("load")
    fun loadImage(view: ImageView, res: Any) {
        UtilKView.loadImage(view, res)
    }

    /**
     * 加载图片(复杂场景)
     * @param view ImageView
     * @param loadImageComplex Any
     * @param placeholder Int
     * @param error Int
     */
    @JvmStatic
    @BindingAdapter(value = ["loadComplex", "placeholder", "error"], requireAll = false)
    fun loadImageComplex(
        imageView: ImageView,
        loadImageComplex: Any,
        placeholder: Int = R.mipmap.refreshk_loading,
        error: Int = R.mipmap.layoutk_empty
    ) {
        UtilKView.loadImageComplex(imageView, loadImageComplex, placeholder, error)
    }

    /**
     * 加载圆形图片
     * @param imageView ImageView
     * @param res Any
     * @param placeholder Int
     * @param error Int
     */
    @JvmStatic
    @BindingAdapter(value = ["loadCircle", "placeholder", "error"], requireAll = false)
    fun loadImageCircle(
        imageView: ImageView,
        loadCircle: Any,
        placeholder: Int = R.mipmap.refreshk_loading,
        error: Int = R.mipmap.layoutk_empty
    ) {
        UtilKView.loadImageCircle(imageView, loadCircle, placeholder, error)
    }

    /**
     * 加载带边框的圆角图片
     * @param imageView ImageView
     * @param loadCircleBorder Any
     * @param borderWidth Int
     * @param borderColor Int
     * @param placeholder Int
     * @param error Int
     */
    @JvmStatic
    @BindingAdapter(value = ["loadCircleBorder", "borderWidth", "borderColor", "placeholder", "error"], requireAll = false)
    fun loadImageCircleBorder(
        imageView: ImageView,
        loadCircleBorder: Any,
        borderWidth: Int = 2,
        borderColor: Int = android.R.color.white,
        placeholder: Int = R.mipmap.refreshk_loading,
        error: Int = R.mipmap.layoutk_empty
    ) {
        UtilKView.loadImageCircleBorder(imageView, loadCircleBorder, borderWidth.toFloat(), borderColor, placeholder, error)
    }

    /**
     * 加载圆角图片
     * @param imageView ImageView
     * @param loadCorner Any
     * @param cornerRadius Int
     * @param placeholder Int
     * @param error Int
     */
    @JvmStatic
    @BindingAdapter(value = ["loadCorner", "cornerRadius", "placeholder", "error"], requireAll = false)
    fun loadImageCorner(
        imageView: ImageView,
        loadCorner: Any,
        cornerRadius: Int = 2,
        placeholder: Int = R.mipmap.refreshk_loading,
        error: Int = R.mipmap.layoutk_empty
    ) {
        UtilKView.loadImageCorner(imageView, loadCorner, cornerRadius, placeholder, error)
    }
}