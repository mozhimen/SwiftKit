package com.mozhimen.swiftmk.base;

import java.lang.System;

/**
 * @ClassName BaseViewModel
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/5/26 17:42
 * @Version 1.0
 */
@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JO\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\b\"\u0004\b\u0000\u0010\n2\u0006\u0010\u000b\u001a\u00020\f2\"\u0010\r\u001a\u001e\b\u0001\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\t0\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000e\u00f8\u0001\u0000\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2 = {"Lcom/mozhimen/swiftmk/base/BaseViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "mTag", "", "getMTag", "()Ljava/lang/String;", "fire", "Landroidx/lifecycle/LiveData;", "Lkotlin/Result;", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function1;)Landroidx/lifecycle/LiveData;", "swiftmk_debug"})
public class BaseViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mTag = null;
    
    public BaseViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMTag() {
        return null;
    }
    
    /**
     * 作用: 消息机制
     * 依赖: implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2'
     *  implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2'
     *
     * 声明: val oldLiveData = MutableLiveData<Location>()
     *  val weatherLiveData=Transformations.switchMap(locationLiveData){ location->
     *    Repository.refreshWeather(location.lng,location.lat)}
     *
     * 用法1: fun searchPlaces(query:String) = fire(Dispatchers.IO){
     * val placeResponse=SunnyWeatherNetwork.searchPlaces(query)
     *  if(placeResponse.status=="ok"){
     *    val places=placeResponse.places
     *    Result.success(places)}
     *  else{
     *    Result.failure(RuntimeException("response status is ${placeResponse.status}"))}}
     *
     * 用法2: fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO) {
     * coroutineScope {
     *  val deferredRealTime = async {
     *    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)}
     *  val realtimeResponse=deferredRealTime.await()
     *  if(realtimeResponse.status=="ok"){
     *    val weather=Weather(realtimeResponse.result.realtime)
     *    Result.success(weather)
     *  }else{
     *    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}"))}}}
     *
     * 调用: viewModel.weatherLiveData.observe(this, Observer { result ->
     * val weather = result.getOrNull()
     * if (weather != null) {...} else {...
     *  result.exceptionOrNull()?.printStackTrace()}})
     */
    @org.jetbrains.annotations.NotNull()
    public final <T extends java.lang.Object>androidx.lifecycle.LiveData<kotlin.Result<T>> fire(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.CoroutineContext context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super kotlin.Result<? extends T>>, ? extends java.lang.Object> block) {
        return null;
    }
}