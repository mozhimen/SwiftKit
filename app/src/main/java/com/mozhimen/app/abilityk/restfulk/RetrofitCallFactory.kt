package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.annors.methods.METHOD
import com.mozhimen.abilityk.restfulk.commons.Call
import com.mozhimen.abilityk.restfulk.mos.RequestK
import com.mozhimen.abilityk.restfulk.mos.ResponseK
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
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
class RetrofitCallFactory(baseUrl: String) : Call.Factory {
    private var gsonConverter: GsonConverter
    private var apiService: ApiService


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        apiService = retrofit.create(ApiService::class.java)
        gsonConverter = GsonConverter()
    }

    override fun newCall(requestK: RequestK): Call<Any> {
        return RetrofitCall(requestK)
    }

    internal inner class RetrofitCall<T>(private val requestK: RequestK) : Call<T> {
        override fun execute(): ResponseK<T> {
            val realCall = createRealCall(requestK)
            val responseK = realCall.execute()
            return parseResponse(responseK)
        }

        private fun parseResponse(responseK: Response<ResponseBody>): ResponseK<T> {
            var rawData: String? = null
            if (responseK.isSuccessful) {
                val body = responseK.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = responseK.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return gsonConverter.convert(rawData!!, requestK.returnType!!)
        }

        override fun enqueue(callback: com.mozhimen.abilityk.restfulk.commons.Callback<T>) {
            val realCall = createRealCall(requestK)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val responseK = parseResponse(response)
                    callback.onSuccess(responseK)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFail(t)
                }

            })
        }

        private fun createRealCall(requestK: RequestK): Call<ResponseBody> {
            when (requestK.httpMethod) {
                METHOD.GETK -> {
                    return apiService.get(
                        requestK.headers,
                        requestK.endPointUrl(),
                        requestK.parameters
                    )
                }
                METHOD.POSTK -> {
                    val requestBody: RequestBody = buildRequestBody(requestK)
                    return apiService.post(requestK.headers, requestK.endPointUrl(), requestBody)
                }
                METHOD.PUTK -> {
                    val requestBody: RequestBody = buildRequestBody(requestK)
                    return apiService.put(requestK.headers, requestK.endPointUrl(), requestBody)
                }
                METHOD.DELETEK -> {
                    return apiService.delete(requestK.headers, requestK.endPointUrl())
                }
                else -> {
                    throw IllegalStateException("restfulk only support GET POST for now, url = " + requestK.endPointUrl())
                }
            }
        }

        private fun buildRequestBody(requestK: RequestK): RequestBody {
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