package com.mozhimen.componentktest.netk.http.customs

import com.mozhimen.componentktest.netk.http.mos.Weather
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @ClassName TestApi
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:19
 * @Version 1.0
 */
interface Apis {

    //121.321504,31.194874
    @GET("{loc}/realtime.json")
    suspend fun getRealTimeWeatherCoroutine(@Path("loc") loc: String): Weather?

}