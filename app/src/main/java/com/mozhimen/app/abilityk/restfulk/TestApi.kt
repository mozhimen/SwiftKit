package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.annors.Field
import com.mozhimen.abilityk.restfulk.annors.methods.GET
import com.mozhimen.abilityk.restfulk.commons.CallK
import org.json.JSONObject

/**
 * @ClassName TestApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:19
 * @Version 1.0
 */
interface TestApi {
    @GET("xxx")
    fun listCities(@Field("name") name: String): CallK<JSONObject>
}