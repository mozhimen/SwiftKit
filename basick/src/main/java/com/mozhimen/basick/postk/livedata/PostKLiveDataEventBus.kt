package com.mozhimen.basick.postk.livedata

import androidx.lifecycle.*
import com.mozhimen.basick.elemk.androidx.lifecycle.StickyLiveData
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName SenseKLiveDataEventBus
 * @Description
 * 基于事件名称 订阅,分发消息
 * 由于一个liveData只能发送 一种数据类型
 * 所以 不同的event事件, 需要使用不同的liveData实例去分发
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/6 16:53
 * @Version 1.0
 */
object PostKLiveDataEventBus {
    private val _eventLiveDataMap = ConcurrentHashMap<String, EventBusStickyLiveData<*>>()

    ///////////////////////////////////////////////////////////////////////////////////////////

    fun <T> with(eventName: String): StickyLiveData<T> {
        var liveData = _eventLiveDataMap[eventName]
        if (liveData == null) {
            liveData = EventBusStickyLiveData<T>(eventName)
            _eventLiveDataMap[eventName] = liveData
        }
        return liveData as EventBusStickyLiveData<T>
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    class EventBusStickyLiveData<T>(private val _name: String) : StickyLiveData<T>() {
        override fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
            owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) _eventLiveDataMap.remove(_name)//监听宿主 发生销毁事件, 主动把liveData移除掉
            })
            super.observeSticky(owner, observer)
        }
    }
}