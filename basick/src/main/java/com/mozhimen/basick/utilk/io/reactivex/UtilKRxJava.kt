package com.mozhimen.basick.utilk.io.reactivex

import com.mozhimen.basick.elemk.commons.IA_Listener
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @ClassName UtilKRxJava
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/12 0:48
 * @Version 1.0
 */
object UtilKRxJava {
    /**
     * 毫秒后执行next操作
     * @param millisecond Long
     * @param schedulers Scheduler
     * @param listener IRxJavaListener
     * @return Disposable
     */
    @JvmStatic
    fun timer(
        millisecond: Long,
        schedulers: Scheduler = AndroidSchedulers.mainThread(),
        listener: IA_Listener<Long>
    ): Disposable =
        Observable.timer(millisecond, TimeUnit.MILLISECONDS)
            .observeOn(schedulers)
            .subscribe { c -> listener.invoke(c) }

    /**
     * 每隔毫秒后执行next操作
     * @param millisecond Long
     * @param schedulers Scheduler
     * @param listener IRxJavaListener
     * @return Disposable
     */
    @JvmStatic
    fun interval(
        millisecond: Long,
        schedulers: Scheduler = AndroidSchedulers.mainThread(),
        listener: IA_Listener<Long>
    ): Disposable =
        Observable.interval(100, millisecond, TimeUnit.MILLISECONDS)
            .observeOn(schedulers)
            .subscribe { c -> listener.invoke(c) }

    /**
     * 每隔毫秒后执行next操作
     * @param millisecond Long
     * @param repeatCount Int
     * @param schedulers Scheduler
     * @param listener IRxJavaListener
     * @return Disposable
     */
    @JvmStatic
    fun take(
        millisecond: Long,
        repeatCount: Int,
        schedulers: Scheduler = AndroidSchedulers.mainThread(),
        listener: IA_Listener<Long>
    ): Disposable =
        Observable.interval(millisecond, TimeUnit.MILLISECONDS)
            .take(repeatCount.toLong())
            .observeOn(schedulers)
            .subscribe { c -> listener.invoke(c) }
}

//    interface UtilKIRxJavaListener {
//        fun onNext(count: Long)
//    }