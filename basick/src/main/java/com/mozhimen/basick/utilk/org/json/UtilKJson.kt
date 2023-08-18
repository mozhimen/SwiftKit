package com.mozhimen.basick.utilk.org.json

import android.text.TextUtils
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.google.gson.UtilKGson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:30
 * @Version 1.0
 */
object UtilKJson : BaseUtilK() {

    /**
     * 从Json中摘取string
     * @param json String
     * @param name String
     * @return String?
     */
    @JvmStatic
    fun getStrForJson(json: String, name: String): String =
        JSONObject(json.trim { it <= ' ' })[name].toString()

    /**
     * 从JsonObj找到JsonArray
     * @param jsonObj JSONObject
     * @param name String
     * @return JSONArray?
     */
    @JvmStatic
    fun getJsonArrayForJsonObj(jsonObj: JSONObject, name: String): JSONArray? =
        try {
            jsonObj.getJSONArray(name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从JsonObj找到JsonObj
     * @param jsonObj JSONObject
     * @param name String
     * @return JSONObject?
     */
    @JvmStatic
    fun getJsonObjForJsonObj(jsonObj: JSONObject, name: String): JSONObject? =
        try {
            jsonObj.getJSONObject(name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从jsonObj获取T
     * @param jsonObj JSONObject
     * @param name String
     * @return T?
     */
    @Suppress(CSuppress.UNCHECKED_CAST)
    @JvmStatic
    fun <T> getTForJsonObj(jsonObj: JSONObject, name: String): T? =
        try {
            jsonObj[name] as? T?
        } catch (e: JSONException) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从JsonObj获取JsonArray
     * @param jsonArray JSONArray
     * @param i Int
     * @return JSONObject?
     */
    @JvmStatic
    fun getJsonObjForJsonArray(jsonArray: JSONArray, i: Int): JSONObject? =
        try {
            jsonArray.getJSONObject(i)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    ////////////////////////////////////////////////////////////////////

    /**
     * json转JsonArray
     * @param json String
     * @return JSONArray
     */
    @JvmStatic
    fun json2jsonArray(json: String): JSONArray =
        JSONArray(json)

    /**
     * json转Array
     * @param json String
     * @return Array<String>?
     */
    @JvmStatic
    fun json2strArray(json: String): Array<String?> {
        val jsonArray = JSONArray(json.trim { it <= ' ' })
        val length = jsonArray.length()
        val strArray = Array<String?>(length) { "" }
        for (i in 0 until length)
            strArray[i] = jsonArray[i] as? String?
        return strArray
    }

    /**
     * json转JsonObj
     * @param json String
     * @return JSONObject
     */
    @JvmStatic
    fun json2jsonObj(json: String): JSONObject =
        JSONObject(json)

    /**
     * json转TList
     * @param json String
     * @param clazz Class<T>
     * @return ArrayList<T?>?
     */
    @JvmStatic
    fun <T> json2tList(json: String, clazz: Class<T>): ArrayList<T?>? =
        jsonArray2tList(JSONArray(json.trim { json <= " " }), clazz)

    /**
     * Any转JsonObj
     * @param obj Any
     * @return JSONObject?
     */
    @JvmStatic
    fun obj2jsonObj(obj: Any): JSONObject =
        if (obj is String)
            JSONObject(obj)
        else JSONObject(UtilKGson.obj2jsonGson(obj))

    /**
     * jsonArray转StrList
     * @param jsonArray JSONArray
     * @return ArrayList<String?>?
     */
    @JvmStatic
    fun jsonArray2strList(jsonArray: JSONArray): ArrayList<String?>? {
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

    /**
     * jsonArray转TList
     * @param jsonArray JSONArray
     * @param clazz Class<T>
     * @return ArrayList<T?>?
     */
    @JvmStatic
    fun <T> jsonArray2tList(jsonArray: JSONArray, clazz: Class<T>): ArrayList<T?>? {
        val arrayList = ArrayList<T?>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObj = jsonArray[i] as? JSONObject?
                if (jsonObj != null)
                    arrayList.add(UtilKGson.jsonGson2t(jsonObj.toString(), clazz))
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

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun wrapJson(json: String): String {
        var message: String
        if (TextUtils.isEmpty(json)) return ""
        try {
            if (json.startsWith("{")) {
                val jsonObject = JSONObject(json)
                message = jsonObject.toString(2)
                message = """
                
                <<<<<=====JSONObject=====>>>>>
                $message
                <<<<<=====JSONObject=====>>>>>
                
                """.trimIndent()
            } else if (json.startsWith("[")) {
                val jsonArray = JSONArray(json)
                message = jsonArray.toString(4)
                message = """
                
                <<<<<=====JSONArray=====>>>>>
                $message
                <<<<<=====JSONArray=====>>>>>
                
                """.trimIndent()
            } else message = json
        } catch (e: JSONException) {
            e.printStackTrace()
            e.message?.et(TAG)
            message = json
        }
        return message
    }

    /**
     * 分割json
     * @param json String
     * @return Array<String?>?
     */
    @JvmStatic
    fun splitJson(json: String): Array<String?>? {
        val splitArray: Array<String?> = json.split("\t").toTypedArray()
        return if (splitArray.size != 2) null else splitArray
    }

    /**
     * 组合Json
     * @param jsonObj JSONObject
     * @param jsonObj2 JSONObject
     * @return String?
     */
    @JvmStatic
    fun joinJsonObj2json(jsonObj: JSONObject, jsonObj2: JSONObject): String {
        val jsonArray = JSONArray()
        jsonArray.put(jsonObj)
        val jsonArray2 = JSONArray()
        jsonArray2.put(jsonObj2)

        val stringBuffer = StringBuffer()
        val stringBuilder = StringBuilder()
        stringBuilder.append(jsonArray.toString())
        stringBuilder.append("\t")
        stringBuilder.append(jsonArray2.toString())
        stringBuffer.append(stringBuilder.toString())
        return stringBuffer.toString()
    }

    /**
     * 将obj填入
     * @param jsonObj JSONObject
     * @param obj Any
     * @param str Array<out String>
     */
    @JvmStatic
    fun putObj2jsonObj(jsonObj: JSONObject, obj: Any, vararg str: String) {
        var name = str[0]
        if (str.size > 1)
            name = str[1]
        try {
            jsonObj.put(name, obj)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}