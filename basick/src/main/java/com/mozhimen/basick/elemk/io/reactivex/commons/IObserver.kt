package com.mozhimen.basick.elemk.io.reactivex.commons

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * @ClassName ObserverCallback
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface IObserver<T : Any> : Observer<T> {
    override fun onSubscribe(d: Disposable) {}
    override fun onNext(t: T) {}
    override fun onError(e: Throwable) {}
    override fun onComplete() {}
}