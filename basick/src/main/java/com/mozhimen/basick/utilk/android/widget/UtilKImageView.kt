package com.mozhimen.basick.utilk.android.widget

import android.graphics.Outline
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.graphics.cons.CGradientDrawable
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.utilk.android.graphics.UtilKGradientDrawable
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.get_ofAlpha
import com.mozhimen.basick.utilk.kotlin.get_ofContrast

/**
 * @ClassName UtilKViewImage
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/6 2:35
 * @Version 1.0
 */
fun ImageView.applyImageResource_of(bool: Boolean, @DrawableRes statusTrue: Int, @DrawableRes statusFalse: Int) {
    UtilKImageView.applyImageResource_of(this, bool, statusTrue, statusFalse)
}

fun ImageView.applyFitDrawable_ofFit(drawable: Drawable) {
    UtilKImageView.applyFitDrawable_ofFit(this, drawable)
}

fun ImageView.applyColorFilter(colorRes: Int) {
    UtilKImageView.applyColorFilter(this, colorRes)
}

fun ImageView.applyBackground_ofFillWithStroke(@ColorInt fillColorInt: Int, @ColorInt backgroundColorInt: Int, isDrawRectangle: Boolean = false) {
    UtilKImageView.applyBackground_ofFillWithStroke(this, fillColorInt, backgroundColorInt, isDrawRectangle)
}

fun ImageView.showToastContentDescriptionOnLongClick() {
    UtilKImageView.showToastContentDescriptionOnLongClick(this)
}

fun ImageView.showToastContentDescription() {
    UtilKImageView.showToastContentDescription(this)
}

object UtilKImageView {

    @JvmStatic
    fun getContentDescription(imageView: ImageView): CharSequence? =
        imageView.contentDescription

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun applyImageResource_of(imageView: ImageView, bool: Boolean, @DrawableRes statusTrue: Int, @DrawableRes statusFalse: Int) {
        imageView.setImageResource(if (bool) statusTrue else statusFalse)
    }

    //适应图片
    @JvmStatic
    fun applyFitDrawable_ofFit(imageView: ImageView, drawable: Drawable) {
        val layoutParams = imageView.layoutParams ?: ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.height = (drawable.intrinsicHeight / (drawable.intrinsicWidth * 1.0f / layoutParams.width)).toInt()
        imageView.layoutParams = layoutParams
    }

    @JvmStatic
    fun applyColorFilter(imageView: ImageView, @ColorInt intColor: Int) {
        imageView.setColorFilter(intColor, PorterDuff.Mode.SRC_IN)
    }

    @JvmStatic
    fun applyBackground_ofFillWithStroke(imageView: ImageView, @ColorInt intColor: Int, @ColorInt intColorBorder: Int, isDrawRectangle: Boolean = false) {
        imageView.background = UtilKGradientDrawable.get(
            if (isDrawRectangle) CGradientDrawable.RECTANGLE else CGradientDrawable.OVAL,
            intColor,
            intColorBorder,
            2
        )
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun applyRoundRect_ofOutline(imageView: ImageView, @FloatRange(from = 0.0, to = 1.0) alpha: Float, radius: Float) {
        imageView.apply {
            clipToOutline = true
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    view ?: return
                    outline?.apply {
                        this.alpha = alpha
                        setRoundRect(0, 0, view.width, view.height, radius)
                    }
                }
            }
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun applyShadow(imageView: ImageView, elevation: Float, @ColorInt outlineAmbientShadowColor: Int, @ColorInt outlineSpotShadowColor: Int) {
        imageView.apply {
            setElevation(elevation)
            if (UtilKBuildVersion.isAfterV_28_9_P()) {
                this.outlineAmbientShadowColor = outlineAmbientShadowColor
                this.outlineSpotShadowColor = outlineSpotShadowColor
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun showToastContentDescriptionOnLongClick(imageView: ImageView) {
        imageView.setOnLongClickListener {
            showToastContentDescription(imageView)
            true
        }
    }

    @JvmStatic
    fun showToastContentDescription(imageView: ImageView) {
        getContentDescription(imageView)?.toString()?.showToastOnMain()
    }
}