package com.mozhimen.basick.elemk.commons


/**
 * @ClassName IListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
typealias ISuspend_Listener = suspend () -> Unit
typealias ISuspend_AListener<A> = suspend () -> A
typealias ISuspendA_Listener<A> = suspend (a: A) -> Unit
typealias ISuspendAA_Listener<A> = suspend (a1: A, a2: A) -> Unit
typealias ISuspendAB_Listener<A, B> = suspend (a: A, b: B) -> Unit
typealias ISuspendA_BListener<A, B> = suspend (a: A) -> B

typealias ISuspendExt_Listener<E> = suspend E.() -> Unit
typealias ISuspendExt_AListener<E, A> = suspend E.() -> A
typealias ISuspendExtA_BListener<E, A, B> = suspend E.(a: A) -> B
