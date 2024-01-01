package com.mozhimen.basick.imagek.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.WorkerThread
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mozhimen.basick.imagek.glide.commons.ICustomTarget
import com.mozhimen.basick.imagek.glide.impls.BlurTransformation
import com.mozhimen.basick.imagek.glide.impls.CircleBorderTransform
import com.mozhimen.basick.utilk.bases.BaseUtilK
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * @ClassName ImageKGlide
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 16:53
 * @Version 1.0
 */
suspend fun String.isImageHorizontal(): Boolean =
    ImageKGlide.isImageHorizontal(this)

suspend fun String.isImageVertical(): Boolean =
    ImageKGlide.isImageVertical(this)

@Deprecated(
    message = "replace to coil 用coil替代glide",
    replaceWith = ReplaceWith(
        expression = "ImageKCoil",
        imports = ["com.mozhimen.basick.imagek.coil.ImageKCoil"]
    )
)
object ImageKGlide : BaseUtilK() {

    @JvmStatic
    suspend fun getImageWidthAndHeight(res: Any?): Pair<Int, Int> = suspendCancellableCoroutine { coroutine ->
        Glide.with(_context).asBitmap().load(res).into(object : ICustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Log.d(TAG, "onResourceReady: res $res resource width ${resource.width} height ${resource.height}")
                coroutine.resume(resource.width to resource.height)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                Log.d(TAG, "onLoadFailed: resource width 0 height 0")
                coroutine.resume(0 to 0)
            }
        })
    }

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否是横图
     */
    @JvmStatic
    suspend fun isImageHorizontal(res: Any?): Boolean {
        val imageSize = getImageWidthAndHeight(res)
        return imageSize.first > imageSize.second
    }

    /**
     * 是否是竖图
     */
    @JvmStatic
    suspend fun isImageVertical(res: Any?): Boolean =
        !isImageHorizontal(res)

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @WorkerThread
    fun obj2Bitmap(obj: Any, placeholder: Int, width: Int, height: Int): Bitmap {
        return Glide.with(_context).asBitmap().load(obj)
            .centerCrop()
            .placeholder(placeholder)
            .error(placeholder)
            .into(width, height)
            .get()
    }

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun loadImageGlide(
        imageView: ImageView,
        res: Any?
    ) {
        Glide.with(imageView).load(res)
            .into(imageView)
    }

    @JvmStatic
    fun loadImageComplexGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(error)
            .placeholder(placeholder)
            .into(imageView)
    }

    /**
     * 加载圆形图片
     */
    @JvmStatic
    fun loadImageCircleGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CircleCrop())
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

    /**
     * 加载带边框的圆角图片
     */
    @JvmStatic
    fun loadImageBorderRoundedCornerGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int,
        borderWidth: Float,
        borderColor: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CircleBorderTransform(borderWidth, borderColor))
            .placeholder(placeholder)
            .error(error)
            .into(imageView)
    }

    /**
     * 加载圆角图片
     */
    @JvmStatic
    fun loadImageRoundedCornerGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int,
        cornerRadius: Int
    ) {
        Glide.with(imageView).load(res)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .placeholder(placeholder).error(error)
            .into(imageView)
    }

    @JvmStatic
    fun loadImageBlurGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int,
        radius: Int = BlurTransformation.BLUR_MAX_RADIUS,
        sampling: Int = BlurTransformation.BLUR_DEFAULT_DOWN_SAMPLING
    ) {
        Glide.with(imageView)
            .load(res)
            .placeholder(placeholder)
            .error(error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transform(BlurTransformation(radius, sampling))
            .into(imageView)
    }
}