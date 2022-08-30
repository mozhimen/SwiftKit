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
    fun setViewRatio(view: View, ratio: Float) {
        UtilKView.setViewRatio(view, ratio)
    }

    /**
     * 加载图片
     * @param view ImageView
     * @param res Any
     */
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, res: Any) {
        UtilKView.loadImage(view, res)
    }

    /**
     * 加载图片(复杂场景)
     * @param view ImageView
     * @param res Any
     */
    /*@JvmStatic
    @BindingAdapter("loadImageComplex")
    fun loadImageComplex(
        view: ImageView,
        res: Any,
        placeholder: Int = R.mipmap.,
        error: Int = android.R.color.black
    ) {
        UtilKView.loadImageComplex(view, res)
    }*/
}