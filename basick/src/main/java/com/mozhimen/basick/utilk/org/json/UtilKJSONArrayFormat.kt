package com.mozhimen.basick.utilk.org.json

import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.google.gson.UtilKGson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @ClassName UtilKJSONFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/19 1:46
 * @Version 1.0
 */
fun JSONArray.jSONArray2strList(): ArrayList<String?>? =
    UtilKJSONArrayFormat.jSONArray2strList(this)

fun <T> JSONArray.jSONArray2tList(clazz: Class<T>): ArrayList<T?>? =
    UtilKJSONArrayFormat.jSONArray2tList(this, clazz)

object UtilKJSONArrayFormat: IUtilK {

    @JvmStatic
    fun jSONArray2strList(jsonArray: JSONArray): ArrayList<String?>? {
        val arrayList = ArrayList<String?>()
        for (i in 0 until jsonArray.length()) {
            try {
                val obj = jsonArray[i] as? String?
                arrayList.add(obj)
            } catch (e: JSONException) {
                e.printStackTrace()
                e.message?.et(TAG)
                return null
            }
        }
        return arrayList
    }

    @JvmStatic
    fun <T> jSONArray2tList(jsonArray: JSONArray, clazz: Class<T>): ArrayList<T?>? {
        val arrayList = ArrayList<T?>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObj = jsonArray[i] as? JSONObject?
                if (jsonObj != null)
                    arrayList.add(UtilKGson.strJson2tGson(jsonObj.toString(), clazz))
                else
                    arrayList.add(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            return null
        }
        return arrayList
    }
}