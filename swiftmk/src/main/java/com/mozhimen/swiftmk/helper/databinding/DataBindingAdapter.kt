package com.mozhimen.swiftmk.helper.databinding

import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mozhimen.swiftmk.R

/**
 * @ClassName DataBindingAdapter
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/6/21 15:35
 * @Version 1.0
 */
object DataBindingAdapter {
    // 根据View的高度和宽高比，设置高度
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

    //Databinding加载图片
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView?, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view?.context!!).load(url).dontAnimate().placeholder(R.mipmap.none).into(view)
        }
    }
}