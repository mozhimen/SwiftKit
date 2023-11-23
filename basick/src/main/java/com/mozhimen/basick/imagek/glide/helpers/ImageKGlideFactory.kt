package com.mozhimen.basick.imagek.glide.helpers

import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.mozhimen.basick.elemk.commons.IA_AListener
import com.mozhimen.basick.imagek.glide.mos.ImageKGlideFile
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @ClassName ImageKGlideFactory
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/11/23 10:29
 * @Version 1.0
 */
/** The default factory for [OkHttpUrlLoader]s.  */ // Public API.
class ImageKGlideFactory
/** Constructor for a new Factory that runs requests using a static singleton client.  */ @JvmOverloads constructor(
    private val _client: Call.Factory = internalClient!!,
    private val _listener: IA_AListener<String?>
) : ModelLoaderFactory<ImageKGlideFile, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<ImageKGlideFile, InputStream> =
        ImageKGlideModelLoader(_client, _listener)

    override fun teardown() {
        // Do nothing, this instance doesn't own the client.
    }

    companion object {
        @Volatile
        private var internalClient: Call.Factory? = null
            get() {
                if (field == null) {
                    synchronized(ImageKGlideFactory::class.java) {
                        if (field == null)
                            field = OkHttpClient()
                    }
                }
                return field
            }
    }
    /**
     * Constructor for a new Factory that runs requests using given client.
     * @param _client this is typically an instance of `OkHttpClient`.
     */
}