package com.mozhimen.app.abilityk.netk.customs

import com.mozhimen.abilityk.netk.annors._Path
import com.mozhimen.abilityk.netk.annors.methods._GET
import com.mozhimen.abilityk.netk.commons.INetKCall
import com.mozhimen.app.abilityk.netk.mos.Weather
import io.reactivex.Observable
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
    @_GET("{loc}/realtime.json")
    fun getRealTimeWeatherAsync(@_Path("loc") loc: String): INetKCall<Weather>

    //121.321504,31.194874
    @GET("{loc}/realtime.json")
    suspend fun getRealTimeWeatherCoroutine(@Path("loc") loc: String): Weather

    //121.321504,31.194874
    @GET("{loc}/realtime.json")
    fun getRealTimeWeatherRxJava(@Path("loc") loc: String): Observable<Weather>
}