package com.mozhimen.basick.imagek.glide.helpers

import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import com.mozhimen.basick.elemk.commons.IA_AListener
import com.mozhimen.basick.imagek.glide.mos.GlideImageFileId
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @ClassName ImageKFileIdLoader
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 17:17
 * @Version 1.0
 */
class ImageKFileIdLoader(private val _client: Call.Factory, private val _onExecuteFileId: IA_AListener<String?>) : ModelLoader<GlideImageFileId, InputStream> {

    override fun handles(model: GlideImageFileId): Boolean {
        if (model.url?.isNotEmpty() == true) {
            return true
        }
        if (model.fileId.isNotEmpty()) {
            return true
        }
        return false
    }

    override fun buildLoadData(model: GlideImageFileId, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(ObjectKey(model), OkHttpStreamFetcher(_client, model, _onExecuteFileId))
    }

    /** The default factory for [OkHttpUrlLoader]s.  */ // Public API.
    class Factory
    /** Constructor for a new Factory that runs requests using a static singleton client.  */ @JvmOverloads constructor(
        private val _client: Call.Factory = internalClient!!,
        private val _onExecuteFileId: IA_AListener<String?>
    ) :
        ModelLoaderFactory<GlideImageFileId, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideImageFileId, InputStream> {
            return ImageKFileIdLoader(_client, _onExecuteFileId)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }

        companion object {
            @Volatile
            private var internalClient: Call.Factory? = null
                get() {
                    if (field == null) {
                        synchronized(Factory::class.java) {
                            if (field == null) {
                                field = OkHttpClient()
                            }
                        }
                    }
                    return field
                }
        }
        /**
         * Constructor for a new Factory that runs requests using given client.
         *
         * @param _client this is typically an instance of `OkHttpClient`.
         */
    }
}