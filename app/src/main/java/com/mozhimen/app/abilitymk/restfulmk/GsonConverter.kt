package com.mozhimen.app.abilitymk.restfulmk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mozhimen.abilitymk.restfulmk.commons.Converter
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
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

    override fun <T> convert(rawData: String, dataType: Type): ResponseMK<T> {
        var responseMK = ResponseMK<T>()
        try {
            var jsonObject = JSONObject(rawData)
            responseMK.code = jsonObject.optInt("code")
            responseMK.msg = jsonObject.optString("msg")
            val data = jsonObject.opt("data")

            if ((data is JSONObject) or (data is JSONArray)) {
                if (responseMK.code == ResponseMK.SUCCESS) {
                    responseMK.data = gson.fromJson(data.toString(), dataType)
                } else {
                    responseMK.errorData = gson.fromJson<MutableMap<String, String>>(
                        data.toString(),
                        object : TypeToken<MutableMap<String, String>>() {}.type
                    )
                }
            } else {
                responseMK.data = data as T?
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            responseMK.code = -1
            responseMK.msg = e.message
        }

        return responseMK
    }
}