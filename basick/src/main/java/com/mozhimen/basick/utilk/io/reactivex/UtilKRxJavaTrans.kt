package com.mozhimen.basick.utilk.io.reactivex

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @ClassName RxJavaScheduler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/14 14:08
 * @Version 1.0
 */
object UtilKRxJavaTrans {
    @JvmStatic
    fun <T> io2mainObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun <T> new2mainObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun <T> io2mainFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream: Flowable<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun <T> io2mainMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer { upstream: Maybe<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun <T> io2mainSingle(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun <T> new2mainSingle(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
    @JvmStatic
    fun io2mainCompletable(): CompletableTransformer {
        return CompletableTransformer { upstream: Completable ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}