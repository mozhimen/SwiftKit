package com.mozhimen.app.abilityk.restfulk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mozhimen.abilityk.restfulk.commons.Converter
import com.mozhimen.abilityk.restfulk.mos.ResponseK
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * @ClassName GsonConverter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 20:51
 * @Version 1.0
 */
class GsonConverter : Converter {
    private var gson: Gson = Gson()

    override fun <T> convert(rawData: String, dataType: Type): ResponseK<T> {
        var responseK = ResponseK<T>()
        try {
            var jsonObject = JSONObject(rawData)
            responseK.code = jsonObject.optInt("code")
            responseK.msg = jsonObject.optString("msg")
            val data = jsonObject.opt("data")

            if ((data is JSONObject) or (data is JSONArray)) {
                if (responseK.code == ResponseK.SUCCESS) {
                    responseK.data = gson.fromJson(data.toString(), dataType)
                } else {
                    responseK.errorData = gson.fromJson<MutableMap<String, String>>(
                        data.toString(),
                        object : TypeToken<MutableMap<String, String>>() {}.type
                    )
                }
            } else {
                responseK.data = data as T?
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            responseK.code = -1
            responseK.msg = e.message
        }

        return responseK
    }
}