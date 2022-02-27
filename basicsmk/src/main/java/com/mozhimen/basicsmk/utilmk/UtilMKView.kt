package com.mozhimen.basicsmk.utilmk

import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.R

/**
 * @ClassName UtilMKView
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:50
 * @Version 1.0
 */
object UtilMKView {
    /**
     * 根据View的高度和宽高比，设置高度
     */
    @JvmStatic
    @BindingAdapter("widthHeightRatio")
    fun setWidthHeightRatio(view: View, ratio: Float) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val height: Int = view.height
                if (height > 0) {
                    view.layoutParams.width = (height * ratio).toInt()
                    view.invalidate()
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })
    }

    /**
     * Databinding加载图片
     */
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView?, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view?.context!!).load(url).dontAnimate().placeholder(R.color.white)
                .into(view)
        }
    }
}