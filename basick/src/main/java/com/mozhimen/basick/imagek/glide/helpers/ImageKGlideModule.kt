package com.mozhimen.basick.imagek.glide.helpers

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.mozhimen.basick.imagek.glide.mos.GlideImageFileId
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @ClassName ImageKGlideModule
 * @Description
 *
 * //定义你自己的GlideModule
 *
 * 依赖:
 * kapt 'com.github.bumptech.glide:compiler:4.12.0'
 *
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 17:16
 * @Version 1.0
 */


/*
@Excludes(com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule::class)
@GlideModule
class ImageKGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        //OkHttp请求使用同一个OKHttp对象
        val okHttpClient = OkHttpClient.Builder().build()
        //使用全局的okHttp对象
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
        //注册我们自己的通过FileId加载图片的ModelLoader
        registry.append(
            GlideImageFileId::class.java,
            InputStream::class.java,
            ImageKFileIdLoader.Factory(okHttpClient) { a -> "" }
        )
    }
}*/
