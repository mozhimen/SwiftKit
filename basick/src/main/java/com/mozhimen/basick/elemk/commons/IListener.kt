package com.mozhimen.basick.elemk.commons


/**
 * @ClassName IListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/13 14:50
 * @Version 1.0
 */
typealias I_Listener = () -> Unit
typealias I_AListener<A> = () -> A
typealias IA_Listener<A> = (a: A) -> Unit
typealias IA_BListener<A, B> = (a: A) -> B
typealias IAA_Listener<A> = (a1: A, a2: A) -> Unit
typealias IAA_BListener<A,B> = (a1: A, a2: A) -> B
typealias IAB_Listener<A, B> = (a: A, b: B) -> Unit
