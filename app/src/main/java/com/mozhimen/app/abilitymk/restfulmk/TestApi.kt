package com.mozhimen.app.abilitymk.restfulmk

import com.mozhimen.abilitymk.restfulmk.annors.FieldMK
import com.mozhimen.abilitymk.restfulmk.annors.methods.GETMK
import com.mozhimen.abilitymk.restfulmk.commons.CallMK
import org.json.JSONObject

/**
 * @ClassName TestApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:19
 * @Version 1.0
 */
interface TestApi {
    @GETMK("xxx")
    fun listCities(@FieldMK("name") name: String): CallMK<JSONObject>
}