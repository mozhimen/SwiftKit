package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.annors.methods._METHOD
import com.mozhimen.abilityk.restfulk.commons._ICall
import com.mozhimen.abilityk.restfulk.commons._ICallback
import com.mozhimen.abilityk.restfulk.mos._Request
import com.mozhimen.abilityk.restfulk.mos._Response
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
class RetrofitCallIFactory(baseUrl: String) : _ICall.IFactory {
    private var _gsonConverter: GsonIConverter
    private var _apiService: ApiService


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        _apiService = retrofit.create(ApiService::class.java)
        _gsonConverter = GsonIConverter()
    }

    override fun newCall(request: _Request): _ICall<Any> {
        return RetrofitICall(request)
    }

    internal inner class RetrofitICall<T>(private val request: _Request) : _ICall<T> {
        override fun execute(): _Response<T> {
            val realCall: Call<ResponseBody> = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)
        }

        private fun parseResponse(response: Response<ResponseBody>): _Response<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return _gsonConverter.convert(rawData!!, request.returnType!!)
        }

        override fun enqueue(ICallback: _ICallback<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response = parseResponse(response)
                    ICallback.onSuccess(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    ICallback.onFail(t)
                }
            })
        }

        private fun createRealCall(request: _Request): Call<ResponseBody> {
            when (request.httpMethod) {
                _METHOD._GETK -> {
                    return _apiService.get(
                        request.headers,
                        request.endPointUrl(),
                        request.parameters
                    )
                }
                _METHOD._POSTK -> {
                    val requestBody: RequestBody = buildRequestBody(request)
                    return _apiService.post(request.headers, request.endPointUrl(), requestBody)
                }
                _METHOD._PUTK -> {
                    val requestBody: RequestBody = buildRequestBody(request)
                    return _apiService.put(request.headers, request.endPointUrl(), requestBody)
                }
                _METHOD._DELETEK -> {
                    return _apiService.delete(request.headers, request.endPointUrl())
                }
                else -> {
                    throw IllegalStateException("restfulk only support GET POST for now, url = " + request.endPointUrl())
                }
            }
        }

        private fun buildRequestBody(requestK: _Request): RequestBody {
            val params = requestK.parameters
            val builder = FormBody.Builder()
            val requestBody: RequestBody?
            val jsonObject = JSONObject()
            for ((key, value) in params!!) {
                if (requestK.formPost) {
                    builder.add(key, value)
                } else {
                    jsonObject.put(key, value)
                }
            }
            requestBody = if (requestK.formPost) {
                builder.build()
            } else {
                jsonObject.toString()
                    .toRequestBody("application/json;utf-8".toMediaTypeOrNull())
            }
            return requestBody
        }
    }

    interface ApiService {
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