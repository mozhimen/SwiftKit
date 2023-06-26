package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.liveData
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @ClassName UtilKLiveData
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/7 15:09
 * @Version 1.0
 */
object UtilKLiveData {
    /**
     * 作用: 消息机制
     * 依赖: implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2'
     *   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2'
     *
     * 声明: val oldLiveData = MutableLiveData<Location>()
     *   val weatherLiveData=Transformations.switchMap(locationLiveData){ location->
     *     Repository.refreshWeather(location.lng,location.lat)}
     *
     * 用法1: fun searchPlaces(query:String) = fire(Dispatchers.IO){
     * val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
     *   if(placeResponse.status=="ok"){
     *     val places=placeResponse.places
     *     Result.success(places)}
     *   else{
     *     Result.failure(RuntimeException("response status is ${placeResponse.status}"))}}
     *
     * 用法2: fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
     * coroutineScope {
     *   val deferredRealTime = async {
     *     SunnyWeatherNetwork.getRealtimeWeather(lng,lat)}
     *   val realtimeResponse=deferredRealTime.await()
     *   if(realtimeResponse.status=="ok"){
     *     val weather=Weather(realtimeResponse.result.realtime)
     *     Result.success(weather)
     *   }else{
     *     Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"))}}}
     *
     * 调用: viewModel.weatherLiveData.observe(this, Observer { result ->
     * val weather = result.getOrNull()
     * if (weather != null) {...} else {...
     *   result.exceptionOrNull()?.printStackTrace()}})
     */
    @JvmStatic
    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}