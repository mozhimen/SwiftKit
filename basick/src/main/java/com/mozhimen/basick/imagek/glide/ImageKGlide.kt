package com.mozhimen.basick.imagek.glide

import android.app.Activity
import android.content.Context
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
import com.bumptech.glide.request.transition.Transition
import com.mozhimen.basick.elemk.commons.I_AListener
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.imagek.glide.commons.ICustomTarget
import com.mozhimen.basick.imagek.glide.impls.BlurTransformation
import com.mozhimen.basick.imagek.glide.impls.RoundedBorderTransformation
import com.mozhimen.basick.utilk.android.app.isFinishingOrDestroyed
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.kotlinx.coroutines.safeResume
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @ClassName ImageKGlide
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 16:53
 * @Version 1.0
 */
suspend fun String.isImageHorizontal(context: Context?): Boolean =
    ImageKGlide.isImageHorizontal(this, context)

suspend fun String.isImageVertical(context: Context?): Boolean =
    ImageKGlide.isImageVertical(this, context)

//////////////////////////////////////////////////////////////////

fun ImageView.loadImageGlide(res: Any) {
    ImageKGlide.loadImageGlide(this, res)
}

fun ImageView.loadImageRoundedCornerGlide(res: Any, radius: Int) {
    ImageKGlide.loadImageRoundedCornerGlide(this, res, radius)
}

fun ImageView.loadImageComplexGlide(
    res: Any, placeholder: Int, error: Int
) {
    ImageKGlide.loadImageComplexGlide(this, res, placeholder, error)
}

@Deprecated(
    message = "replace to coil 用coil替代glide",
    replaceWith = ReplaceWith(
        expression = "ImageKCoil",
        imports = ["com.mozhimen.imagek.coil.ImageKCoil"]
    )
)
object ImageKGlide : IUtilK {

    @JvmStatic
    suspend fun getImageWidthAndHeight(res: Any?, context: Context?): Pair<Int, Int> = suspendCancellableCoroutine { coroutine ->
        contractImageGlide(context, {
            Glide.with(context!!).asBitmap().load(res).into(object : ICustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.d(TAG, "onResourceReady: res $res resource width ${resource.width} height ${resource.height}")
                    coroutine.safeResume(resource.width to resource.height)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    Log.d(TAG, "onLoadFailed: resource width 0 height 0")
                    coroutine.safeResume(0 to 0)
                }
            })
        }, {
            Log.d(TAG, "onLoadFailed: onError of glide")
            coroutine.safeResume(0 to 0)
        })
    }

    //////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否是横图
     */
    @JvmStatic
    suspend fun isImageHorizontal(res: Any?, context: Context?): Boolean {
        val imageSize = getImageWidthAndHeight(res, context)
        return imageSize.first > imageSize.second
    }

    /**
     * 是否是竖图
     */
    @JvmStatic
    suspend fun isImageVertical(res: Any?, context: Context?): Boolean {
        val imageSize = getImageWidthAndHeight(res, context)
        return imageSize.first < imageSize.second
    }

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @WorkerThread
    fun obj2Bitmap(obj: Any, context: Context?, placeholder: Int, width: Int, height: Int): Bitmap? {
        return contractImageGlideRes(context) {
            Glide.with(context!!).asBitmap().load(obj)
                .centerCrop()
                .placeholder(placeholder)
                .error(placeholder)
                .into(width, height)
                .get()
        }
    }

    @JvmStatic
    @WorkerThread
    fun obj2Bitmap(obj: Any, context: Context?, placeholder: Int, width: Int, height: Int, cornerRadius: Int): Bitmap? {
        return contractImageGlideRes(context) {
            Glide.with(context!!).asBitmap().load(obj)
                .centerCrop()
                .transform(RoundedCorners(cornerRadius))
                .placeholder(placeholder)
                .error(placeholder)
                .into(width, height)
                .get()
        }
    }

    //////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun loadImageGlide(
        imageView: ImageView, res: Any?
    ) {
        contractImageGlide(imageView.context, {
            Glide.with(imageView).load(res)
                .into(imageView)
        })
    }

    @JvmStatic
    fun loadImageComplexGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        error: Int
    ) {
        contractImageGlide(imageView.context, {
            Glide.with(imageView).load(res)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(error)
                .placeholder(placeholder)
                .into(imageView)
        })
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
        contractImageGlide(imageView.context, {
            Glide.with(imageView).load(res)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CircleCrop())
                .placeholder(placeholder)
                .error(error)
                .into(imageView)
        })
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
        contractImageGlide(imageView.context,{
            Glide.with(imageView).load(res)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(RoundedBorderTransformation(borderWidth, borderColor))
                .placeholder(placeholder)
                .error(error)
                .into(imageView)
        })
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
        contractImageGlide(imageView.context,{
            Glide.with(imageView).load(res)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .placeholder(placeholder)
                .error(error)
                .into(imageView)
        })
    }

    @JvmStatic
    fun loadImageRoundedCornerGlide(
        imageView: ImageView,
        res: Any?,
        cornerRadius: Int
    ) {
        contractImageGlide(imageView.context,{
            Glide.with(imageView).load(res)
//            .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop(), RoundedCorners(cornerRadius))
                .into(imageView)
        })
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
        contractImageGlide(imageView.context,{
            Glide.with(imageView)
                .load(res)
                .placeholder(placeholder)
                .error(error)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(BlurTransformation(radius, sampling))
                .into(imageView)
        })
    }

    @JvmStatic
    fun loadImageBlurGlide(
        imageView: ImageView,
        res: Any?,
        placeholder: Int,
        radius: Int = BlurTransformation.BLUR_MAX_RADIUS,
        sampling: Int = BlurTransformation.BLUR_DEFAULT_DOWN_SAMPLING
    ) {
        contractImageGlide(imageView.context,{
            Glide.with(imageView)
                .load(res)
                .placeholder(placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(BlurTransformation(radius, sampling))
                .into(imageView)
        })
    }

    @JvmStatic
    fun contractImageGlide(context: Context?, onContinue: I_Listener, onError: I_Listener? = null) {
        if (context != null /*&& context is Activity && !context.isFinishingOrDestroyed()*/) {
            try {
                onContinue.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                onError?.invoke()
            }
        } else {
            onError?.invoke()
        }
    }

    @JvmStatic
    fun <T> contractImageGlideRes(context: Context?, onContinue: I_AListener<T>): T? {
        if (context != null /*&& context is Activity && !context.isFinishingOrDestroyed()*/) {
            try {
                return onContinue.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}