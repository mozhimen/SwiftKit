package com.mozhimen.basick.utilk.io.reactivex

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Maybe
import io.reactivex.MaybeTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @ClassName RxJavaScheduler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/14 14:08
 * @Version 1.0
 */
object UtilKTransformerWrapper {
    @JvmStatic
    fun <T> getObservableTransformer_ofIo2main(): ObservableTransformer<T, T> =
        ObservableTransformer { observable: Observable<T> ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun <T> getObservableTransformer_ofNew2main(): ObservableTransformer<T, T> =
        ObservableTransformer { observable: Observable<T> ->
            observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun <T> getFlowableTransformer_ofIo2main(): FlowableTransformer<T, T> =
        FlowableTransformer { observable: Flowable<T> ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun <T> getMaybeTransformer_ofIo2main(): MaybeTransformer<T, T> =
        MaybeTransformer { maybe: Maybe<T> ->
            maybe.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun <T> getSingleTransformer_ofIo2main(): SingleTransformer<T, T> =
        SingleTransformer { single: Single<T> ->
            single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun <T> getSingleTransformer_ofNew2main(): SingleTransformer<T, T> =
        SingleTransformer { single: Single<T> ->
            single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }

    @JvmStatic
    fun getCompletableTransformer_io2main(): CompletableTransformer =
        CompletableTransformer { completable: Completable ->
            completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
}