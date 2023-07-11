package com.mozhimen.basick.elemk.androidx.lifecycle

import androidx.lifecycle.Observer
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IStickyLiveData

/**
 * @ClassName StickyObserver
 * @Description 还有一种方法: 通过反射, 获取liveData当中的mVersion字段, 来控制黏性数据的分发与否, 但是我们认为这种反射不够优雅
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/7/12 0:25
 * @Version 1.0
 */
class StickyObserver<T>(
    private val _stickyLiveData: IStickyLiveData<T>,
    private val _isSticky: Boolean,
    private val _observer: Observer<in T>
) : Observer<T> {

    /**
     * lastVersion 和liveData的version对齐的原因, 就是为控制黏性事件的分发
     * sticky不等于true, 只能接收到注册之后发送的消息, 如果要接收黏性事件, 则sticky需要传递true
     */
    private var _lastVersion = _stickyLiveData.getStickyVersion()

    override fun onChanged(t: T) {
        if (_lastVersion >= _stickyLiveData.getStickyVersion()) {
            //就说明stickyLiveData 没有更新的数据需要发送
            if (_isSticky && _stickyLiveData.getStickyValue() != null) {
                _observer.onChanged(_stickyLiveData.getStickyValue())
            }
            return
        }

        _lastVersion = _stickyLiveData.getStickyVersion()
        _observer.onChanged(t)
    }
}