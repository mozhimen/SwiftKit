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
object UtilKJSON : BaseUtilK() {

    /**
     * 从Json中摘取string
     */
    @JvmStatic
    fun getStrForStrJson(strJson: String, name: String): String =
        JSONObject(strJson.trim { it <= ' ' })[name].toString()

    /**
     * 从JsonObj找到JsonArray
     */
    @JvmStatic
    fun getJSONArrayForJSONObject(jSONObject: JSONObject, name: String): JSONArray? =
        try {
            jSONObject.getJSONArray(name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从JsonObj找到JsonObj
     */
    @JvmStatic
    fun getJSONObjectForJSONObject(jSONObject: JSONObject, name: String): JSONObject? =
        try {
            jSONObject.getJSONObject(name)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从jsonObj获取T
     */
    @Suppress(CSuppress.UNCHECKED_CAST)
    @JvmStatic
    fun <T> getTForJSONObject(jSONObject: JSONObject, name: String): T? =
        try {
            jSONObject[name] as? T?
        } catch (e: JSONException) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    /**
     * 从JsonObj获取JsonArray
     */
    @JvmStatic
    fun getJSONObjectForJSONArray(jSONArray: JSONArray, index: Int): JSONObject? =
        try {
            jSONArray.getJSONObject(index)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            null
        }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun wrapStrJson(strJson: String): String {
        var message: String
        if (TextUtils.isEmpty(strJson)) return ""
        try {
            if (strJson.startsWith("{")) {
                val jSONObject = JSONObject(strJson)
                message = jSONObject.toString(2)
                message = """
                
                <<<<<=====JSONObject=====>>>>>
                $message
                <<<<<=====JSONObject=====>>>>>
                
                """.trimIndent()
            } else if (strJson.startsWith("[")) {
                val jSONArray = JSONArray(strJson)
                message = jSONArray.toString(4)
                message = """
                
                <<<<<=====JSONArray=====>>>>>
                $message
                <<<<<=====JSONArray=====>>>>>
                
                """.trimIndent()
            } else message = strJson
        } catch (e: JSONException) {
            e.printStackTrace()
            e.message?.et(TAG)
            message = strJson
        }
        return message
    }

    /**
     * 分割json
     */
    @JvmStatic
    fun splitStrJson(strJson: String): Array<String?>? {
        val splitArray: Array<String?> = strJson.split("\t").toTypedArray()
        return if (splitArray.size != 2) null else splitArray
    }

    /**
     * 组合Json
     */
    @JvmStatic
    fun joinJSONObject2strJson(jSONObject: JSONObject, jsonObj2: JSONObject): String {
        val jSONArray = JSONArray()
        jSONArray.put(jSONObject)
        val jsonArray2 = JSONArray()
        jsonArray2.put(jsonObj2)

        val stringBuffer = StringBuffer()
        val stringBuilder = StringBuilder()
        stringBuilder.append(jSONArray.toString())
        stringBuilder.append("\t")
        stringBuilder.append(jsonArray2.toString())
        stringBuffer.append(stringBuilder.toString())
        return stringBuffer.toString()
    }

    /**
     * 将obj填入
     */
    @JvmStatic
    fun putObj2jSONObject(jSONObject: JSONObject, obj: Any, vararg str: String) {
        var name = str[0]
        if (str.size > 1)
            name = str[1]
        try {
            jSONObject.put(name, obj)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }
}