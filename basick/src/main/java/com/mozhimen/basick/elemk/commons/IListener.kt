package com.mozhimen.basick.elemk.commons


/**
 * @ClassName IListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
typealias I_Listener = () -> Unit
typealias I_AListener<A> = () -> A
typealias IA_Listener<A> = (a: A) -> Unit
typealias IA_AListener<A> = (a: A) -> A
typealias IA_BListener<A, B> = (a: A) -> B
typealias IAA_Listener<A> = (a1: A, a2: A) -> Unit
typealias IAA_BListener<A, B> = (a1: A, a2: A) -> B
typealias IAB_Listener<A, B> = (a: A, b: B) -> Unit
typealias IABC_Listener<A, B, C> = (a: A, b: B, c: C) -> Unit

typealias IExt_Listener<E> = E.() -> Unit
typealias IExt_AListener<E, A> = E.() -> A
