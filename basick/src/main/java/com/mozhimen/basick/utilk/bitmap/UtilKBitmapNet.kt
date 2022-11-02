package com.mozhimen.basick.utilk.bitmap

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mozhimen.basick.utilk.UtilKGlobal
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * @ClassName UtilKBitmapNet
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/2 23:49
 * @Version 1.0
 */
object UtilKBitmapNet {
    private val _context = UtilKGlobal.instance.getApp()!!

    /**
     * 协程方式 获取Bitmap
     * @param url String
     * @param placeholder Int
     * @param error Int
     * @return Bitmap
     */
    @JvmStatic
    suspend fun url2Bitmap(
        url: String,
        placeholder: Int = android.R.color.black,
        error: Int = android.R.color.black
    ): Bitmap? = suspendCancellableCoroutine { coroutine ->
        Glide.with(_context).asBitmap().load(url).transition(withCrossFade()).placeholder(placeholder).error(error).into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                coroutine.resume(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                coroutine.resume(null)
            }
        })
    }
}