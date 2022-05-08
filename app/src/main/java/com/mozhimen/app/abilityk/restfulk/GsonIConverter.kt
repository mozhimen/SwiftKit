package com.mozhimen.app.abilityk.restfulk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mozhimen.abilityk.restfulk.commons._IConverter
import com.mozhimen.abilityk.restfulk.mos._Response
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
class GsonIConverter : _IConverter {
    private var gson: Gson = Gson()

    override fun <T> convert(rawData: String, dataType: Type): _Response<T> {
        var response = _Response<T>()
        try {
            var jsonObject = JSONObject(rawData)
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")
            val data = jsonObject.opt("data")

            if ((data is JSONObject) or (data is JSONArray)) {
                if (response.code == _Response.SUCCESS) {
                    response.data = gson.fromJson(data.toString(), dataType)
                } else {
                    response.errorData = gson.fromJson<MutableMap<String, String>>(
                        data.toString(),
                        object : TypeToken<MutableMap<String, String>>() {}.type
                    )
                }
            } else {
                response.data = data as T?
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            response.code = -1
            response.msg = e.message
        }

        return response
    }
}