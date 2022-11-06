package com.mozhimen.basick.utilk.bitmap

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import com.mozhimen.basick.utilk.UtilKGlobal

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
     * @return Bitmap
     */
    @JvmStatic
    suspend fun url2Bitmap(
        url: String
    ): Bitmap? {
        return (_context.imageLoader.execute(ImageRequest.Builder(_context).data(url).build()).drawable as? BitmapDrawable)?.bitmap

//        Glide.with(_context).asBitmap().load(url).transition(withCrossFade()).placeholder(placeholder).error(error).into(object : CustomTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                coroutine.resume(resource)
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) {
//                coroutine.resume(null)
//            }
//        })
    }
}