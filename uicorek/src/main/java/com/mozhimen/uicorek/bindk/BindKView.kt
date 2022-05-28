package com.mozhimen.uicorek.bindk

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mozhimen.basick.utilk.UtilKView

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
     * @param view ImageView?
     * @param url String?
     */
    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadImage(view: ImageView, url: String) {
        UtilKView.loadImageComplex(view, url)
    }

    /**
     * 加载图片
     * @param view ImageView?
     * @param url String?
     */
    @JvmStatic
    @BindingAdapter("loadBitmap")
    fun loadImage(view: ImageView, bitmap: Bitmap) {
        UtilKView.loadImage(view, bitmap)
    }
}