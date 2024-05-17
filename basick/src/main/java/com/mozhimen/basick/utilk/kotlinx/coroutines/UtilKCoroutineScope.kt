package com.mozhimen.basick.utilk.kotlinx.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import com.mozhimen.basick.elemk.commons.ISuspendA_BListener
import com.mozhimen.basick.elemk.commons.ISuspendExtA_BListener
import com.mozhimen.basick.elemk.commons.ISuspendExt_Listener
import com.mozhimen.basick.elemk.commons.ISuspend_AListener
import com.mozhimen.basick.elemk.commons.ISuspend_Listener
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @ClassName UtilKCoroutineScope
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 9:41
 * @Version 1.0
 */
fun CoroutineScope.launchSafe(coroutineContext: CoroutineContext= EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
    UtilKCoroutineScope.launchSafe(this, coroutineContext, block)
}

//////////////////////////////////////////////////////////////////

object UtilKCoroutineScope {
    @JvmStatic
    fun launchOnMainScope(coroutineScope: CoroutineScope, block: ISuspendExt_Listener<CoroutineScope>) {
        coroutineScope.launch(Dispatchers.Main) {
            this.block()
        }
    }

    @JvmStatic
    fun launchOnBackScope(coroutineScope: CoroutineScope, block: ISuspendExt_Listener<CoroutineScope>) {
        coroutineScope.launch(Dispatchers.IO) {
            this.block()
        }
    }

    @JvmStatic
    fun launchSafe(coroutineScope: CoroutineScope, coroutineContext: CoroutineContext= EmptyCoroutineContext, block: ISuspendExt_Listener<CoroutineScope>) {
        coroutineScope.launch(coroutineContext) {
            try {
                this.block()
            } catch (e: Throwable) {
                UtilKLogWrapper.e(e)
            }
        }
    }

    /**
     * 作用: 消息机制
     * 依赖: implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2'
     *   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2'
     *
     * 声明: val oldLiveData = MutableLiveData<Location>()
     *   val weatherLiveData=Transformations.switchMap(locationLiveData){ location->
     *     Repository.refreshWeather(location.lng,location.lat)}
     *
     * 用法1: fun searchPlaces(query:String) = asLiveData(Dispatchers.IO){
     * val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
     *   if(placeResponse.status=="ok"){
     *     val places=placeResponse.places
     *     Result.success(places)}
     *   else{
     *     Result.failure(RuntimeException("response status is ${placeResponse.status}"))}}
     *
     * 用法2: fun refreshWeather(lng:String,lat:String) = asLiveData(Dispatchers.IO) {
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
    fun <T> asLiveData(coroutineContext: CoroutineContext, block: ISuspend_AListener<Result<T>>/* suspend () -> Result<T>*/): LiveData<Result<T>> =
        liveData(coroutineContext) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}