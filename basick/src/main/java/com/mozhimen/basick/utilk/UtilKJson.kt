package com.mozhimen.basick.utilk

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.jvm.Throws

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:23
 * @Version 1.0
 */
object UtilKJson {

    val moshiBuilder = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    /**
     * 转json
     * @param t T
     * @param indent String 自定义字符例如
     * @return String
     * @throws Exception
     */
    @Throws(Exception::class)
    inline fun <reified T> t2Json(t: T, indent: String = ""): String =
        moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!).indent(indent).toJson(t)

    /**
     * 转T
     * @param json String
     * @return T?
     * @throws Exception
     */
    @Throws(Exception::class)
    inline fun <reified T> json2T(json: String): T? =
        moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!).fromJson(json)

//    /**
//     * 转Json
//     * @param t Any
//     * @return String
//     */
//    @JvmStatic
//    fun <T> t2Json(t: T): String =
//        Gson().toJson(t)
//
//    /**
//     * obj转Json
//     * @param any Any
//     * @return String
//     */
//    @JvmStatic
//    fun any2Json(any: Any): String =
//        Gson().toJson(any)
//
//    /**
//     * 从Json转
//     * @param json String
//     * @param token TypeToken<T>
//     * @return T
//     */
//    @JvmStatic
//    fun <T> json2T(json: String, token: TypeToken<T>): T =
//        Gson().fromJson(json, token.type)
//
//    /**
//     * 从Json转
//     * @param json String
//     * @param cls Class<T>
//     * @return T
//     */
//    @JvmStatic
//    fun <T> json2T(json: String, cls: Class<T>): T =
//        Gson().fromJson(json, cls)
//
//    /**
//     * Any转JsonObj
//     * @param any Any
//     * @return JSONObject?
//     */
//    @JvmStatic
//    fun any2JsonObj(any: Any): JSONObject =
//        if (any is String) {
//            JSONObject(any)
//        } else {
//            JSONObject(Gson().toJson(any))
//        }
//
//    /**
//     * 从JsonString中摘取string
//     * @param json String
//     * @param name String
//     * @return String?
//     */
//    @JvmStatic
//    fun getStrFromJson(json: String, name: String): String =
//        JSONObject(json.trim { it <= ' ' })[name].toString()
//
//    /**
//     * obj转jsonStr
//     * @param obj Any
//     * @return String
//     */
//    @JvmStatic
//    fun obj2JsonOnField(obj: Any): String =
//        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create().toJson(obj)
//
//    /**
//     * json转Array
//     * @param json String
//     * @return Array<String>?
//     */
//    @JvmStatic
//    fun json2StrArray(json: String): Array<String?> {
//        val jsonArray = JSONArray(json.trim { it <= ' ' })
//        val length = jsonArray.length()
//        val strArr = Array<String?>(length) { "" }
//        for (i in 0 until length) {
//            strArr[i] = jsonArray[i] as? String?
//        }
//        return strArr
//    }
//
//    /**
//     * 从JsonObj找到JsonArray
//     * @param jsonObj JSONObject
//     * @param name String
//     * @return JSONArray?
//     */
//    @JvmStatic
//    fun getJsonArrayFromJsonObj(jsonObj: JSONObject, name: String): JSONArray? {
//        return try {
//            jsonObj.getJSONArray(name)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    /**
//     * 从JsonObj找到JsonObj
//     * @param jsonObj JSONObject
//     * @param name String
//     * @return JSONObject?
//     */
//    @JvmStatic
//    fun getJsonObjFromJsonObj(jsonObj: JSONObject, name: String): JSONObject? {
//        return try {
//            jsonObj.getJSONObject(name)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    /**
//     * jsonArray转StrList
//     * @param jsonArray JSONArray
//     * @return ArrayList<String?>?
//     */
//    @JvmStatic
//    fun jsonArray2StrList(jsonArray: JSONArray): ArrayList<String?>? {
//        val arrayList = ArrayList<String?>()
//        for (i in 0 until jsonArray.length()) {
//            try {
//                val obj = jsonArray[i] as? String?
//                arrayList.add(obj)
//            } catch (e: JSONException) {
//                e.printStackTrace()
//                return null
//            }
//        }
//        return arrayList
//    }
//
//    /**
//     * jsonArray转TList
//     * @param jsonArray JSONArray
//     * @param cls Class<T>
//     * @return ArrayList<T?>?
//     */
//    @JvmStatic
//    fun <T> jsonArray2TList(jsonArray: JSONArray, cls: Class<T>): ArrayList<T?>? {
//        val arrayList = ArrayList<T?>()
//        try {
//            val length = jsonArray.length()
//            for (i in 0 until length) {
//                val jsonObj = jsonArray[i] as? JSONObject?
//                if (jsonObj != null) {
//                    arrayList.add(json2T(jsonObj.toString(), cls))
//                } else {
//                    arrayList.add(null)
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return null
//        }
//        return arrayList
//    }
//
//    /**
//     * json转TList
//     * @param json String
//     * @param cls Class<T>
//     * @return ArrayList<T?>?
//     */
//    @JvmStatic
//    fun <T> json2TList(json: String, cls: Class<T>): ArrayList<T?>? {
//        return jsonArray2TList(JSONArray(json.trim { json <= " " }), cls)
//    }
//
//    /**
//     * 组合Json
//     * @param jsonObj JSONObject
//     * @param jsonObj2 JSONObject
//     * @return String?
//     */
//    @JvmStatic
//    fun combineJson(jsonObj: JSONObject, jsonObj2: JSONObject): String? {
//        val jsonArray = JSONArray()
//        jsonArray.put(jsonObj)
//        val jsonArray2 = JSONArray()
//        jsonArray2.put(jsonObj2)
//
//        val stringBuffer = StringBuffer()
//        val stringBuilder = StringBuilder()
//        stringBuilder.append(jsonArray.toString())
//        stringBuilder.append("\t")
//        stringBuilder.append(jsonArray2.toString())
//        stringBuffer.append(stringBuilder.toString())
//        return stringBuffer.toString()
//    }
//
//    /**
//     * 分割json
//     * @param json String
//     * @return Array<String?>?
//     */
//    @JvmStatic
//    fun splitJson(json: String): Array<String?>? {
//        val splitArray: Array<String?> = json.split("\t").toTypedArray()
//        return if (splitArray.size != 2) null else splitArray
//    }
//
//    /**
//     * json转JsonEle
//     * @param json String
//     * @return JsonElement?
//     */
//    @JvmStatic
//    fun json2JsonElement(json: String): JsonElement? = try {
//        Gson().fromJson(json.trim { it <= ' ' }, JsonElement::class.java) as JsonElement
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//
//    /**
//     * json转JsonObj
//     * @param json String
//     * @return JSONObject
//     */
//    @JvmStatic
//    fun json2JsonObj(json: String): JSONObject =
//        JSONObject(json)
//
//    /**
//     * json转JsonArray
//     * @param json String
//     * @return JSONArray
//     */
//    @JvmStatic
//    fun json2JsonArray(json: String): JSONArray =
//        JSONArray(json)
//
//    /**
//     * 将obj填入
//     * @param jsonObj JSONObject
//     * @param any Any
//     * @param strObj Array<out String>
//     */
//    @JvmStatic
//    fun putAny2JsonObj(jsonObj: JSONObject, any: Any, vararg strObj: String) {
//        var name = strObj[0]
//        if (strObj.size > 1) {
//            name = strObj[1]
//        }
//        try {
//            jsonObj.put(name, any)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /**
//     * 从jsonObj获取T
//     * @param jsonObj JSONObject
//     * @param name String
//     * @return T?
//     */
//    @JvmStatic
//    fun <T> getTFromJsonObj(jsonObj: JSONObject, name: String): T? =
//        try {
//            jsonObj[name] as? T?
//        } catch (e: JSONException) {
//            e.printStackTrace()
//            null
//        }
//
//    /**
//     * 从JsonObj获取JsonArray
//     * @param jsonArray JSONArray
//     * @param i Int
//     * @return JSONObject?
//     */
//    @JvmStatic
//    fun getJsonObjFromJsonArray(jsonArray: JSONArray, i: Int): JSONObject? =
//        try {
//            jsonArray.getJSONObject(i)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
}