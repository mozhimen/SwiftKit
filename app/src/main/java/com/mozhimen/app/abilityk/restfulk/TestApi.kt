package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.annors._Field
import com.mozhimen.abilityk.restfulk.annors.methods._GET
import com.mozhimen.abilityk.restfulk.commons._ICall
import org.json.JSONObject

/**
 * @ClassName TestApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:19
 * @Version 1.0
 */
interface TestApi {
    @_GET("xxx")
    fun listCities(@_Field("name") name: String): _ICall<JSONObject>
}