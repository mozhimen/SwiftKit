package com.mozhimen.abilityk.netk.customs

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mozhimen.abilityk.netk.commons.INetKConverter
import com.mozhimen.abilityk.netk.helpers.StatusParser
import com.mozhimen.abilityk.netk.mos.NetKResponse
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
class CustomConverter : INetKConverter {
    private var _gson: Gson = Gson()

    override fun <T> convert(rawData: String, dataType: Type): NetKResponse<T> {
        val response = NetKResponse<T>()
        try {
            val jsonObject = JSONObject(rawData)
            val data = jsonObject.opt("data") ?: jsonObject
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")

            if ((data is JSONObject) || (data is JSONArray)) {
                if (response.code == StatusParser.SUCCESS) {
                    response.data = _gson.fromJson(data.toString(), dataType)
                } else {
                    response.errorData = _gson.fromJson<MutableMap<String, String>>(
                        data.toString(),
                        object : TypeToken<MutableMap<String, String>>() {}.type
                    )
                }
            } else {
                response.data = data as? T?
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            response.code = -1
            response.msg = e.message
        }

        response.rawData = rawData
        return response
    }
}