package com.mozhimen.basick.postk.event

import androidx.lifecycle.*
import com.mozhimen.basick.elemk.androidx.lifecycle.sticky.MutableLiveDataSticky
import com.mozhimen.basick.elemk.kotlin.cons.CSuppress
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
class PostKEventLiveData {
    companion object {
        @JvmStatic
        val instance = INSTANCE.holder
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private val _eventLiveDataMap = ConcurrentHashMap<String, MutableEventLiveDataSticky<*>>()

    private inner class InnerLifecycleEventObserver(private val _name: String) :
        LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY)
                _eventLiveDataMap.remove(_name)//监听宿主 发生销毁事件, 主动把liveData移除掉
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

    @Suppress(CSuppress.UNCHECKED_CAST)
    fun <T> with(eventName: String): MutableLiveDataSticky<T> {
        var liveData = _eventLiveDataMap[eventName]
        if (liveData == null) {
            liveData = MutableEventLiveDataSticky<T>(eventName)
            _eventLiveDataMap[eventName] = liveData
        }
        return liveData as MutableEventLiveDataSticky<T>
    }

    /////////////////////////////////////////////////////////////////////////////////////

    inner class MutableEventLiveDataSticky<T>(private val _name: String) : MutableLiveDataSticky<T>() {
        override fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
            owner.lifecycle.addObserver(InnerLifecycleEventObserver(_name))
            super.observeSticky(owner, observer)
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////

    private object INSTANCE {
        val holder = PostKEventLiveData()
    }
}