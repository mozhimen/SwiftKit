package com.mozhimen.componentk.netk.async.customs

import com.mozhimen.componentk.netk.async.annors.methods._METHOD
import com.mozhimen.componentk.netk.async.commons.INetKCall
import com.mozhimen.componentk.netk.async.commons.INetKConverter
import com.mozhimen.componentk.netk.async.commons.INetKFactory
import com.mozhimen.componentk.netk.async.commons.INetKListener
import com.mozhimen.componentk.netk.async.mos.NetKRequest
import com.mozhimen.componentk.netk.async.mos.NetKResponse
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

/**
 * @ClassName RetrofitCallFactory
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/10/8 22:37
 * @Version 1.0
 */
class AsyncFactory(baseUrl: String, converter: INetKConverter? = null) : INetKFactory {
    private var _gsonConverter: INetKConverter
    private var _apiService: AsyncApiSet


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        _apiService = retrofit.create(AsyncApiSet::class.java)
        _gsonConverter = converter ?: AsyncConverter()
    }

    override fun newCall(request: NetKRequest): INetKCall<Any> {
        return AsyncCall(request)
    }

    internal inner class AsyncCall<T>(private val request: NetKRequest) : INetKCall<T> {
        override fun execute(): NetKResponse<T> {
            val realCall: Call<ResponseBody> = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)
        }

        override fun enqueue(callback: INetKListener<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response1 = parseResponse(response)
                    callback.onSuccess(response1)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFail(t)
                }
            })
        }

        private fun parseResponse(response: Response<ResponseBody>): NetKResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body: ResponseBody? = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body: ResponseBody? = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return _gsonConverter.convert(rawData!!, request.returnType!!)
        }

        private fun createRealCall(request: NetKRequest): Call<ResponseBody> {
            when (request.httpMethod) {
                _METHOD._GET -> {
                    return _apiService.get(
                        request.headers,
                        request.endPointUrl(),
                        request.parameters
                    )
                }
                _METHOD._POST -> {
                    val requestBody: RequestBody = buildRequestBody(request)
                    return _apiService.post(request.headers, request.endPointUrl(), requestBody)
                }
                _METHOD._PUT -> {
                    val requestBody: RequestBody = buildRequestBody(request)
                    return _apiService.put(request.headers, request.endPointUrl(), requestBody)
                }
                _METHOD._DELETE -> {
                    return _apiService.delete(request.headers, request.endPointUrl())
                }
                else -> {
                    throw IllegalStateException("netk only support _GET, _POST, _PUT, _DELETE for now, url = " + request.endPointUrl())
                }
            }
        }

        private fun buildRequestBody(request: NetKRequest): RequestBody {
            val params = request.parameters
            val builder = FormBody.Builder()
            val requestBody: RequestBody?
            val jsonObject = JSONObject()
            if (params != null) {
                for ((key, value) in params) {
                    if (request.formPost) {
                        builder.add(key, value)
                    } else {
                        jsonObject.put(key, value)
                    }
                }
            }
            requestBody = if (request.formPost) {
                builder.build()
            } else {
                jsonObject.toString()
                    .toRequestBody("application/json;utf-8".toMediaTypeOrNull())
            }
            return requestBody
        }
    }

    interface AsyncApiSet {
        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>

        @PUT
        fun put(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>

        @DELETE//不可以携带requestBody
        fun delete(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String
        ): Call<ResponseBody>
    }
}