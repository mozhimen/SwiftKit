package com.mozhimen.basick.imagek.glide.helpers

import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.util.ContentLengthInputStream
import com.bumptech.glide.util.Preconditions
import com.mozhimen.basick.elemk.commons.IA_AListener
import com.mozhimen.basick.imagek.glide.mos.ImageKGlideFile
import com.mozhimen.basick.utilk.bases.IUtilK
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

/**
 * @ClassName OkHttpStreamFetcher
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 17:19
 * @Version 1.0
 */

class OkHttpInputStreamDataFetcher(client: Call.Factory, imageKGlideFile: ImageKGlideFile, private val _onExecuteFileId: IA_AListener<String?>) : DataFetcher<InputStream>, Callback, IUtilK {
    private val _client: Call.Factory
    private val _ImageKGlideFile: ImageKGlideFile
    private var _inputStream: InputStream? = null
    private var _responseBody: ResponseBody? = null
    private var _dataCallback: DataFetcher.DataCallback<in InputStream>? = null

    // call may be accessed on the main thread while the object is in use on other threads. All other
    // accesses to variables may occur on different threads, but only one at a time.
    @Volatile
    private var _call: Call? = null

    // Public API.
    init {
        _client = client
        _ImageKGlideFile = imageKGlideFile
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val imageKGlideFile: ImageKGlideFile = _ImageKGlideFile
        var imageFileIdUrl: String? = imageKGlideFile.url
        if (TextUtils.isEmpty(imageFileIdUrl)) {
            imageFileIdUrl = _onExecuteFileId.invoke(imageKGlideFile.fileId)//Router.serviceRouter.executeCmsNodeGetByIdAndPath(glideImageFileId.getFileId())
        }
        if (imageFileIdUrl.isNullOrEmpty()) {
            callback.onLoadFailed(RuntimeException("查询失败，未找到该节点"))
            return
        }
        val requestBuilder: Request.Builder = Request.Builder().url(imageFileIdUrl)
        val request: Request = requestBuilder.build()
        _dataCallback = callback
        _call = _client.newCall(request)
        _call!!.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "OkHttp failed to obtain result", e)
        }
        _dataCallback?.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        _responseBody = response.body
        if (response.isSuccessful) {
            val contentLength = Preconditions.checkNotNull(_responseBody).contentLength()
            _inputStream = ContentLengthInputStream.obtain(_responseBody!!.byteStream(), contentLength)
            _dataCallback?.onDataReady(_inputStream)
        } else {
            _dataCallback?.onLoadFailed(HttpException(response.message, response.code))
        }
    }

    override fun cleanup() {
        try {
            _inputStream?.close()
        } catch (e: IOException) {
            // Ignored
        }
        _responseBody?.close()
        _dataCallback = null
    }

    override fun cancel() {
        val local = _call
        local?.cancel()
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}

