package com.mozhimen.abilityk.hotupdatek.commons

/**
 * @ClassName IHotUpdateKListener
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/13 15:35
 * @Version 1.0
 */
interface ISuspendHotupdateKListener {
    suspend fun onComplete()
    suspend fun onFail(msg: String)
}