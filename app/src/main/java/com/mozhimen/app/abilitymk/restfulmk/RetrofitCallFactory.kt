package com.mozhimen.app.abilitymk.restfulmk

import com.mozhimen.abilitymk.restfulmk.annors.methods.METHODMK
import com.mozhimen.abilitymk.restfulmk.commons.CallMK
import com.mozhimen.abilitymk.restfulmk.commons.CallbackMK
import com.mozhimen.abilitymk.restfulmk.mos.RequestMK
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
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
class RetrofitCallFactory( baseUrl: String) : CallMK.Factory {
    private var gsonConverter: GsonConverter
    private var apiService: ApiService


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        apiService = retrofit.create(ApiService::class.java)
        gsonConverter = GsonConverter()
    }

    override fun newCall(requestMK: RequestMK): CallMK<Any> {
        return RetrofitCall(requestMK)
    }

    internal inner class RetrofitCall<T>(private val requestMK: RequestMK) : CallMK<T> {
        override fun execute(): ResponseMK<T> {
            val realCallMK = createRealCall(requestMK)
            val responseMK = realCallMK.execute()
            return parseResponse(responseMK)
        }

        private fun parseResponse(responseMK: Response<ResponseBody>): ResponseMK<T> {
            var rawData: String? = null
            if (responseMK.isSuccessful) {
                val body = responseMK.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = responseMK.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return gsonConverter.convert(rawData!!, requestMK.returnType!!)
        }

        override fun enqueue(callbackMK: CallbackMK<T>) {
            val realCallMK = createRealCall(requestMK)
            realCallMK.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val responseMK = parseResponse(response)
                    callbackMK.onSuccess(responseMK)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callbackMK.onFail(t)
                }

            })
        }

        private fun createRealCall(requestMK: RequestMK): Call<ResponseBody> {
            when (requestMK.httpMethod) {
                METHODMK.GETMK -> {
                    return apiService.get(
                        requestMK.headers,
                        requestMK.endPointUrl(),
                        requestMK.parameters
                    )
                }
                METHODMK.POSTMK -> {
                    val params = requestMK.parameters
                    val builder = FormBody.Builder()
                    val requestBody: RequestBody?
                    val jsonObject = JSONObject()
                    for ((key, value) in params!!) {
                        if (requestMK.formPost) {
                            builder.add(key, value)
                        } else {
                            jsonObject.put(key, value)
                        }
                    }
                    requestBody = if (requestMK.formPost) {
                        builder.build()
                    } else {
                        jsonObject.toString()
                            .toRequestBody("application/json;utf-8".toMediaTypeOrNull())
                    }
                    return apiService.post(requestMK.headers, requestMK.endPointUrl(), requestBody)
                }
                else -> {
                    throw IllegalStateException("restfulmk only support GET POST for now, url = " + requestMK.endPointUrl())
                }
            }
        }
    }

    interface ApiService {
        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?, @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>
    }
}